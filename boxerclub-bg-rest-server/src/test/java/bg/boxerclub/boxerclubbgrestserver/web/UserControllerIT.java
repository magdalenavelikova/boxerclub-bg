package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.dto.user.AuthRequest;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.EditUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.RegisterUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserRoleDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

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


    @Test
    public void testLogin() throws Exception {
        AuthRequest authRequest = new AuthRequest() {{
            setUsername("member@member.com");
            setPassword("123456");
        }};

        String jsonRequest = objectMapper.writeValueAsString(authRequest);


        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("member@member.com"))
                .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_MEMBER"));

    }

    @Test
    void testLoginUnauthorizedInvalidPassword() throws Exception {
        AuthRequest authRequest = new AuthRequest() {{
            setUsername("member@member.com");
            setPassword("invalid");
        }};

        String jsonRequest = objectMapper.writeValueAsString(authRequest);

        mockMvc.perform(post("/users/login")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginUnauthorizedInvalidUsername() throws Exception {
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

        mockMvc.perform(post("/users/register")
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

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testDeleteUser_Success() throws Exception {
        UserEntity testMember = testDataUtils.createTestMember("test@mail.bg");


        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", testMember.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testDeleteUserWhenIdIsNotValid() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", "1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value("1111"))
                .andExpect(jsonPath("$.description").value("User not found!"));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"MODERATOR"})
    public void testDeleteUser_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testEditUser_Success() throws Exception {
        UserEntity testMember = testDataUtils.createTestMember("edit@mail.bg");
        UserRoleDto testUserRoleDto = new UserRoleDto() {{
            setRole("MODERATOR");
        }};
        EditUserDto testEditUser = new EditUserDto() {{
            setId(testMember.getId());
            setEmail("edit@mail.bg");
            setFirstName("NewName");
            setLastName("Velikova");
            setRoles(List.of(testUserRoleDto));

        }};
        Long id = testEditUser.getId();

        String jsonRequest = objectMapper.writeValueAsString(testEditUser);
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(testEditUser.getEmail()))
                .andExpect(jsonPath("$.firstName").value(testEditUser.getFirstName()));
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testEditUserWhenSomeFieldIsInvalid() throws Exception {
        UserRoleDto testUserRoleDto = new UserRoleDto() {{
            setRole("MODERATOR");
        }};
        EditUserDto testEditUser = new EditUserDto() {{
            setId(2L);
            setEmail("");
            setFirstName("NewName");
            setLastName("Velikova");
            setRoles(new ArrayList<>());

        }};
        testEditUser.getRoles().add(testUserRoleDto);

        String jsonRequest = objectMapper.writeValueAsString(testEditUser);
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/{id}", testEditUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("CONFLICT"))
                .andExpect(jsonPath("$.message")
                        .value(containsString("Validation failed")));
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testEditUserWhenIdIsNotValid() throws Exception {
        UserRoleDto testUserRoleDto = new UserRoleDto() {{
            setRole("MODERATOR");
        }};
        EditUserDto testEditUser = new EditUserDto() {{
            setId(2L);
            setEmail("member@member.com");
            setFirstName("NewName");
            setLastName("Velikova");
            setRoles(List.of(testUserRoleDto));

        }};

        String jsonRequest = objectMapper.writeValueAsString(testEditUser);
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/{id}", "11111")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value("11111"))
                .andExpect(jsonPath("$.description").value("User not found!"));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"MEMBER"})
    public void testEditUser_Forbidden() throws Exception {
        UserRoleDto testUserRoleDto = new UserRoleDto() {{
            setRole("MODERATOR");
        }};
        EditUserDto testEditUser = new EditUserDto() {{
            setId(2L);
            setEmail("member@member.com");
            setFirstName("NewName");
            setLastName("Velikova");
            setRoles(List.of(testUserRoleDto));

        }};

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/{id}", testEditUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}
