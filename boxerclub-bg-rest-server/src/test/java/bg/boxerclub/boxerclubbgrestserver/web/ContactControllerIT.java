package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactDto;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Sex;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private ContactDto testContactDto;

    @BeforeEach
    void setUp() {
        testContactDto = new ContactDto() {

            {

                setName("Contact");
                setNameBG("Contact");
                setSex(Sex.Female);
                setPosition("Position");
                setPositionBG("Position");
                setPicture("link");
                setCountry("Bulgaria");
                setCountryBG("Bulgaria");
                setCity("Sofia");
                setCityBG("Sofia");
                setZip("1225");
                setAddress("Address");
                setAddressBG("Address");
                setEmail("test@test.bg");
                setPhone("+359887407508");
            }
        };


    }

    @Test

    public void testGetAllContacts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    void testAdd() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(testContactDto);
        mockMvc.perform(post("/contacts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(testContactDto.getName()))
                .andExpect(jsonPath("$.position").value(testContactDto.getPosition()));

    }


    @Test
    @WithMockUser(username = "user@example.com", roles = {"MEMBER"})
    public void testAdd_Forbidden() throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(testContactDto);
        mockMvc.perform(post("/contacts/add")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"MODERATOR"})
    public void testAdd_NotValid() throws Exception {
        testContactDto.setName("");
        String jsonRequest = objectMapper.writeValueAsString(testContactDto);
        mockMvc.perform(post("/contacts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("CONFLICT"))
                .andExpect(jsonPath("$.message")
                        .value(containsString("Validation failed")));

    }


    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    public void testDeleteContact_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/contacts/{id}", "4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    public void testDeleteContactWhenIdIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/contacts/{id}", "1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1111"))
                .andExpect(jsonPath("$.description").value("Contact not found!"));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"MEMBER"})
    public void testDeleteContact_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/contacts/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    public void testEditContact_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(testContactDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/contacts/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(testContactDto.getName()))
                .andExpect(jsonPath("$.position").value(testContactDto.getPosition()));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    public void testEditContactWhenIsNotValid() throws Exception {
        testContactDto.setId(1L);
        testContactDto.setName("");
        String jsonRequest = objectMapper.writeValueAsString(testContactDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/links/{id}", testContactDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("CONFLICT"))
                .andExpect(jsonPath("$.message")
                        .value(containsString("Validation failed")));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"MEMBER"})
    public void testEditContact_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/links/{id}", "12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}
