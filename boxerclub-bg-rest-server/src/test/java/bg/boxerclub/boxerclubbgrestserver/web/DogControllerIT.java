package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Color;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Sex;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.util.TestDataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DogControllerIT {
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
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    private DogRepository testDogRepository;
    private GreenMail greenMail;
    private RegisterDogDto testRegisterDogDto;
    private ParentDto testParentDto;
    private EditDogDto testEditDogDto;
    private DogEntity testDogEntity;

    MockMultipartFile pedigree = new MockMultipartFile("pedigree", "test-pedigree.pdf", "application/pdf", "pedigree content".getBytes());

    @BeforeEach
    void setUp() {
        testDogEntity = testDataUtils.createDog("123456");
        greenMail = new GreenMail(new ServerSetup(port, host, "smtp"));
        greenMail.start();
        greenMail.setUser(username, password);
        testRegisterDogDto = new RegisterDogDto() {
            {
                setName("New");
                setRegistrationNum("112");
                setMicroChip("111");
                setSex(Sex.Male.toString());
                setColor(Color.Brindle.toString());
                setBirthday(LocalDate.of(2023, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");
                setOwnerId("1");

            }
        };
        testParentDto = new ParentDto() {
            {
                setName("Parent");
                setRegistrationNum("111112");
                setMicroChip("111");
                setSex(Sex.Male.toString());
                setColor(Color.Brindle.toString());
                setBirthday("2021-10-10");
                setHealthStatus("ok");
                setKennel("NA");
                setChildId(testDogEntity.getId().toString());

            }
        };
        testEditDogDto = new EditDogDto() {{
            setId(1L);
            setName("Edit");
            setRegistrationNum("111");
            setMicroChip("111");
            setSex(Sex.Male.toString());
            setColor(Color.Brindle.toString());
            setBirthday(LocalDate.of(2023, 10, 10));
            setHealthStatus("ok");
            setKennel("NA");

            setOwnerEmail("bozhidar.velikov@gmail.com");
        }};

    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
        testDataUtils.cleanUpDogs();
    }


    @Test
    @WithUserDetails("member@member.com")
    public void testRegisterDog() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(testRegisterDogDto);
        MockMultipartFile jsonfile = new MockMultipartFile("dto", "", "application/json", (jsonRequest.getBytes()));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/dogs/register")

                .file(pedigree).file(jsonfile).contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("New"));

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

        assertEquals(1, receivedMessages.length);
        MimeMessage registrationMessage = receivedMessages[0];
        assertEquals(1, registrationMessage.getAllRecipients().length);
        assertEquals("New Dog Registration", registrationMessage.getSubject());
        assertEquals("Bulgarian Boxer Club <office@boxerclub-bg.org>", registrationMessage.getFrom()[0].toString());

    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testRegisterDog_WhenUserIsAdmin() throws Exception {
        testRegisterDogDto.setRegistrationNum("1234");
        String jsonRequest = objectMapper.writeValueAsString(testRegisterDogDto);
        MockMultipartFile jsonfile = new MockMultipartFile("dto", "", "application/json", (jsonRequest.getBytes()));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/dogs/register").file(pedigree).file(jsonfile).contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("New"));
        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(0, receivedMessages.length);
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testRegisterDogWhenIsNotValid() throws Exception {
        testRegisterDogDto.setColor("");
        String jsonRequest = objectMapper.writeValueAsString(testRegisterDogDto);
        MockMultipartFile jsonfile = new MockMultipartFile("dto", "", "application/json", (jsonRequest.getBytes()));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/dogs/register").file(pedigree).file(jsonfile).contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.status").value("CONFLICT")).andExpect(jsonPath("$.message").value(containsString("Validation failed")));
        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(0, receivedMessages.length);
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testRegisterDogWhenIsNotUnique() throws Exception {
        testRegisterDogDto.setRegistrationNum("123456");
        String jsonRequest = objectMapper.writeValueAsString(testRegisterDogDto);
        MockMultipartFile jsonfile = new MockMultipartFile("dto", "", "application/json", (jsonRequest.getBytes()));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/dogs/register").file(pedigree).file(jsonfile).contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.status").value("CONFLICT")).andExpect(jsonPath("$.message").value(containsString("Validation failed")));

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(0, receivedMessages.length);
    }

    @Test
    public void testGetAllApprovedDogs() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/dogs/approved")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(94)));
    }

    @Test
    @WithMockUser(username = "mail@example.com", roles = {"ADMIN"})
    public void testGetAllDogs() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/dogs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "mail@example.com", roles = {"MEMBER"})
    public void testGetAllDogs_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogs").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("member@member.com")
    public void testRegisterParentDog() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(testParentDto);
        MockMultipartFile jsonfile = new MockMultipartFile("dto", "", "application/json", (jsonRequest.getBytes()));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/dogs/register/parent").file(jsonfile).contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Parent"));
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testRegisterParentWhenIsNotValid() throws Exception {
        testParentDto.setBirthday("2023-11-01");
        String jsonRequest = objectMapper.writeValueAsString(testParentDto);
        MockMultipartFile jsonfile = new MockMultipartFile("dto", "", "application/json", (jsonRequest.getBytes()));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/dogs/register/parent")

                .file(jsonfile).contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().isConflict()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.description").value("Parent with registration number " + testParentDto.getRegistrationNum() + " is younger than child!"));
    }


    @Test
    @WithUserDetails("member@member.com")
    public void testRequestChangeOwnerShip() throws Exception {
        DogDtoWithNewOwner testDogDtoWithNewOwner = new DogDtoWithNewOwner() {{
            setRegistrationNum(testDogEntity.getRegistrationNum());
            setNewOwnerId("2");
        }};
        String jsonRequest = objectMapper.writeValueAsString(testDogDtoWithNewOwner);

        mockMvc.perform(post("/dogs/ownership").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().isOk()).andExpect(jsonPath("$.message").value("An email has been sent to the current owner. Once he confirms, the ownership will be changed."));
        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

        assertEquals(1, receivedMessages.length);
        MimeMessage registrationMessage = receivedMessages[0];
        assertEquals(1, registrationMessage.getAllRecipients().length);
        assertEquals("Request for change of ownership", registrationMessage.getSubject());
        assertEquals("Bulgarian Boxer Club <office@boxerclub-bg.org>", registrationMessage.getFrom()[0].toString());
        assertEquals("bozhidar.velikov@gmail.com", registrationMessage.getAllRecipients()[0].toString());
    }

    @Test
    @WithMockUser(username = "mail@example.com")
    public void testRequestChangeOwnerShip_Forbidden() throws Exception {
        DogDtoWithNewOwner testDogDtoWithNewOwner = new DogDtoWithNewOwner() {{
            setRegistrationNum(testDogEntity.getRegistrationNum());
            setNewOwnerId("2");
        }};
        String jsonRequest = objectMapper.writeValueAsString(testDogDtoWithNewOwner);

        mockMvc.perform(post("/dogs/ownership")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testApproveDog_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/dogs/approve/{id}", testDogEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());

    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testApproveDogWhenIdIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/dogs/approve/{id}", "1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dogId").value("1111"))
                .andExpect(jsonPath("$.description").value("Dog not found!"));
    }

    @Test
    @WithUserDetails("member@member.com")
    public void testApproveDog_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/dogs/approve/{id}", "1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testGetDog_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogs/{id}", testDogEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").value(testDogEntity.getId()));

    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testGetDogWhenIdIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogs/{id}", "1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dogId").value("1111"))
                .andExpect(jsonPath("$.description").value("Dog not found!"));
    }


    @Test
    public void testGetDogChart_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogs/chart/{id}", testDogEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDogChartWhenIdIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogs/chart/{id}", "1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dogId").value("1111"))
                .andExpect(jsonPath("$.description").value("Dog not found!"));
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testDeleteDog_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/dogs/{id}", testDogEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testDeleteDogWhenIdIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/dogs/{id}", "1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dogId").value("1111"))
                .andExpect(jsonPath("$.description").value("Dog not found!"));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"MEMBER"})
    public void testDeleteDog_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/dogs/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("member@member.com")
    public void testEditDog_Unauthorized() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(testEditDogDto);
        MockMultipartFile jsonfile =
                new MockMultipartFile("dto", "", "application/json",
                        (jsonRequest.getBytes()));
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/dogs/edit/{id}",
                        testDogEntity.getId());
        builder.with(new RequestPostProcessor() {
            @Override
            public @NotNull MockHttpServletRequest postProcessRequest(@NotNull MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });
        mockMvc.perform(builder
                        .file(pedigree).file(jsonfile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testEditDog() throws Exception {
        testEditDogDto.setId(testDogEntity.getId());
        String jsonRequest = objectMapper.writeValueAsString(testEditDogDto);
        MockMultipartFile jsonfile = new MockMultipartFile("dto", "", "application/json", (jsonRequest.getBytes()));
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/dogs/edit/{id}",
                        testDogEntity.getId());
        builder.with(new RequestPostProcessor() {
            @Override
            public @NotNull MockHttpServletRequest postProcessRequest(@NotNull MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });
        mockMvc.perform(builder.file(jsonfile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testEditDogDto.getName()));
    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testEditDogWhenIsNotValid() throws Exception {
        testEditDogDto.setName("");
        String jsonRequest = objectMapper.writeValueAsString(testEditDogDto);
        MockMultipartFile jsonfile = new MockMultipartFile("dto", "", "application/json", (jsonRequest.getBytes()));
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/dogs/edit/{id}",
                        testDogEntity.getId());
        builder.with(new RequestPostProcessor() {
            @Override
            public @NotNull MockHttpServletRequest postProcessRequest(@NotNull MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });
        mockMvc.perform(builder
                        .file(pedigree).file(jsonfile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value(containsString("Validation failed")));

    }

    @Test
    @WithUserDetails("bozhidar.velikov@gmail.com")
    public void testEditDogWhenIsNotUnique() throws Exception {
        DogEntity newDog = testDataUtils.createDog("444");


        testEditDogDto.setId(2L);
        testEditDogDto.setRegistrationNum(testDogEntity.getRegistrationNum());
        String jsonRequest = objectMapper.writeValueAsString(testEditDogDto);
        MockMultipartFile jsonfile = new MockMultipartFile("dto", "", "application/json", (jsonRequest.getBytes()));
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/dogs/edit/{id}",
                        newDog.getId());
        builder.with(new RequestPostProcessor() {
            @Override
            public @NotNull MockHttpServletRequest postProcessRequest(@NotNull MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });
        mockMvc.perform(builder
                        .file(pedigree).file(jsonfile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.description").value("There is already a registered dog with number " + testEditDogDto.getRegistrationNum() + "!"));

    }

    @Test
    public void testGetDogDetails_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogs/details/{id}", testDogEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.dog.id").value(testDogEntity.getId()));

    }

    @Test
    public void testGetDogDetailsWhenIdIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogs/details/{id}", testDogEntity.getId() + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dogId").value(testDogEntity.getId() + 1))
                .andExpect(jsonPath("$.description").value("Dog not found!"));
    }

    @Test
    public void testConfirmChangeOwnerShip_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dogs/ownershipConfirm")
                        .param("registrationNum", testDogEntity.getRegistrationNum())
                        .param("newOwner", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Ownership change successful"));

    }

    @Test
    @WithUserDetails("member@member.com")
    public void testAddParentDog() throws Exception {
        DogEntity parent = testDataUtils.createDog("010101");
        parent.setBirthday(LocalDate.of(2019, 1, 1));
        testDogRepository.save(parent);
        AddParentDto testAddParentDto = new AddParentDto() {{
            setId(parent.getId());
            setName(parent.getName());
            setSex(parent.getSex().toString());
            setChildId(testDogEntity.getId().toString());

        }};

        String jsonRequest = objectMapper.writeValueAsString(testAddParentDto);

        mockMvc.perform(post("/dogs/add/parent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationNum").value(parent.getRegistrationNum()));

    }

    @Test
    @WithUserDetails("member@member.com")
    public void testAddParentDog_WhenIsNotValid() throws Exception {
        DogEntity parent = testDataUtils.createDog("010101");
        AddParentDto testAddParentDto = new AddParentDto() {{
            setId(parent.getId());
            setName(parent.getName());
            setSex(parent.getSex().toString());
            setChildId(testDogEntity.getId().toString());

        }};

        String jsonRequest = objectMapper.writeValueAsString(testAddParentDto);

        mockMvc.perform(post("/dogs/add/parent").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Parent with registration number " + parent.getRegistrationNum() + " is younger than child!"));

    }
}
