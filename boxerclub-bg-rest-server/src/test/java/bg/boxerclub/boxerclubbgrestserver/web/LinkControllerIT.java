package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkDto;
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

public class LinkControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    private LinkDto testLinkDto;

    @BeforeEach
    void setUp() {
        testLinkDto = new LinkDto() {
            {
                setTitle("Title");
                setUrlLink("url_link");
                setDescription("Description");
                setType("Type");
            }
        };
    }

    @Test

    public void testGetAllLinks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/links")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(27)));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    void testAdd() throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(testLinkDto);
        mockMvc.perform(post("/links/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(testLinkDto.getTitle()))
                .andExpect(jsonPath("$.type").value(testLinkDto.getType()));

    }


    @Test
    @WithMockUser(username = "user@example.com", roles = {"MEMBER"})
    public void testAdd_Forbidden() throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(testLinkDto);
        mockMvc.perform(post("/links/add").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"MODERATOR"})
    public void testAdd_NotValid() throws Exception {
        testLinkDto.setUrlLink("");
        String jsonRequest = objectMapper.writeValueAsString(testLinkDto);
        mockMvc.perform(post("/links/add")
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
    public void testDeleteLink_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/links/{id}", "11")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    public void testDeleteLinkWhenIdIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/links/{id}", "1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.linkId").value("1111"))
                .andExpect(jsonPath("$.description").value("Link not found!"));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"MEMBER"})
    public void testDeleteLink_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/links/{id}", "11")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    public void testEditLink_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(testLinkDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/links/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.type").value("Type"));

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    public void testEditLinkWhenIdIsNotValid() throws Exception {
        testLinkDto.setUrlLink("");
        String jsonRequest = objectMapper.writeValueAsString(testLinkDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/links/{id}", "1111")
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
    public void testEditLink_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/links/{id}", "12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}
