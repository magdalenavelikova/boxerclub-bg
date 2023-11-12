package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.dto.user.RegisterUserDto;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import bg.boxerclub.boxerclubbgrestserver.util.TestDataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestDataUtils testDataUtils;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    private GreenMail greenMail;


    @BeforeEach
    void setUp() {

        greenMail = new GreenMail(new ServerSetup(port, host, "smtp"));
        greenMail.start();
        greenMail.setUser(username, password);


    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }


//    @Test
//    public void testLogin() throws Exception {
//
//        UserEntity testUser = testDataUtils.createTestUser("user@mail.bg");
//        AuthRequest authRequest = new AuthRequest() {{
//            setUsername(testUser.getEmail());
//            setPassword(testUser.getPassword());
//        }};
//       // System.out.println(SecurityContextHolder.getContext().getAuthentication());
//        String jsonRequest = objectMapper.writeValueAsString(authRequest);
//
//        ResultActions result = mockMvc.perform(post("/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.email").value("user@mail.bg"))
//                .andExpect(jsonPath("$.roles[0].role").value("USER"));
//
//    }

    @Test
    void testLoginUnauthorized() throws Exception {

        String invalidAuthRequest = "{\"username\": \"invalidUser\", \"password\": \"invalidPassword\"}";

        mockMvc.perform(post("/users/login")
                        .content(invalidAuthRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRegisterUser() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto() {
            {
                setEmail("newUser@example.com");
                setFirstName("Maggie");
                setLastName("Velikova");
                setPassword("456123");
                setConfirmPassword("456123");
            }
        };

        String jsonRequest = objectMapper.writeValueAsString(registerUserDto);

        ResultActions result = mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("newUser@example.com"))
                .andExpect(jsonPath("$.roles[0].role").value("USER"));


        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

        assertEquals(1, receivedMessages.length);
        MimeMessage registrationMessage = receivedMessages[0];
        assertEquals(1, registrationMessage.getAllRecipients().length);
        assertEquals("Successful Registration!", registrationMessage.getSubject());
        assertEquals("Bulgarian Boxer Club <office@boxerclub-bg.org>", registrationMessage.getFrom()[0].toString());
        assertEquals("newUser@example.com", registrationMessage.getAllRecipients()[0].toString());
    }

    @Test
    public void testRegisterUserWhenIsNotValid() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto() {
            {
                setEmail("newUser");
                setFirstName("Maggie");
                setLastName("Velikova");
                setPassword("456");
                setConfirmPassword("456123");
            }
        };

        String jsonRequest = objectMapper.writeValueAsString(registerUserDto);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value(containsString("Validation failed")));
        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(0, receivedMessages.length);
    }

    @Test
    public void testRegisterUserWhenIsNotUnique() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto() {
            {
                setEmail("member@member.com");
                setFirstName("Maggie");
                setLastName("Velikova");
                setPassword("4564564");
                setConfirmPassword("4564564");
            }
        };

        String jsonRequest = objectMapper.writeValueAsString(registerUserDto);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("CONFLICT"))
                .andExpect(jsonPath("$.message")
                        .value(containsString("Validation failed")));

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(0, receivedMessages.length);
    }

    @Test
    @WithMockUser(username = "newUser@example.com", roles = {"ADMIN"})
    public void testGetAllUsers() throws Exception {
        testDataUtils.createTestAdmin("admin@mail.bg");
        testDataUtils.createTestMember("member@mail.bg");

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    @WithMockUser(username = "newUser@example.com", roles = {"MEMBER"})
    public void testGetAllUsers_Forbidden() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }
}
