package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.EventEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Location;
import bg.boxerclub.boxerclubbgrestserver.util.TestDataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestDataUtils testDataUtils;

    private EventDto testEventDto;
    private EventEntity passedEvent;
    private EventEntity upcomingEvent;

    @BeforeEach
    void setUp() {
        passedEvent = testDataUtils.createEvent(LocalDate.of(2023, 10, 15));
        upcomingEvent = testDataUtils.createEvent(LocalDate.now());
        testEventDto = new EventDto() {
            {
                setTitle("Title");
                setUrlLink("url_event");
                setDescription("Description");
                setLocation(Location.Bulgarian);
                setStartDate(LocalDate.of(2023, 10, 10));
                setExpiryDate(LocalDate.now());
            }
        };
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpEvents();
    }

    @Test
    public void testGetAllEvents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.upcomingBg", hasSize(1)))
                .andExpect(jsonPath("$.upcomingInt", hasSize(0)))
                .andExpect(jsonPath("$.passedBg", hasSize(1)))
                .andExpect(jsonPath("$.passedInt", hasSize(0)));


    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    void testAdd() throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(testEventDto);
        mockMvc.perform(post("/events/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(testEventDto.getTitle()))
                .andExpect(jsonPath("$.location").value(testEventDto.getLocation().toString()));

    }


    @Test
    @WithMockUser(username = "user@example.com", roles = {"MEMBER"})
    public void testAddEvent_Forbidden() throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(testEventDto);
        mockMvc.perform(post("/events/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)).andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"MODERATOR"})
    public void testAdd_NotValid() throws Exception {
        testEventDto.setTitle("");
        String jsonRequest = objectMapper.writeValueAsString(testEventDto);
        mockMvc.perform(post("/events/add")
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
    public void testDeleteEvent_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    public void testDeleteEventWhenIdIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/{id}", "1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1111"))
                .andExpect(jsonPath("$.description").value("Event not found!"));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"MEMBER"})
    public void testDeleteEvent_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/events/{id}", "11")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    public void testEditEvent_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(testEventDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/events/{id}", passedEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(testEventDto.getTitle()))
                .andExpect(jsonPath("$.location").value(testEventDto.getLocation().toString()));

    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"ADMIN"})
    public void testEditEventWhenIsNotValid() throws Exception {
        testEventDto.setId(1L);
        testEventDto.setTitle("");
        String jsonRequest = objectMapper.writeValueAsString(testEventDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/events/{id}", testEventDto.getId())
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
    public void testEditEvent_Forbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/events/{id}", upcomingEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}
