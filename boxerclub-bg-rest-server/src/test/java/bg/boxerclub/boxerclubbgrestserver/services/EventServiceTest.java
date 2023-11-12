package bg.boxerclub.boxerclubbgrestserver.services;

import bg.boxerclub.boxerclubbgrestserver.exception.EventNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventsViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.EventEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Location;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.EventMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.EventRepository;
import bg.boxerclub.boxerclubbgrestserver.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EventServiceTest {
    @Mock
    private EventRepository mockEventRepository;
    @Mock
    private EventMapper mockEventMapper;
    private EventDto testEventDto;
    private EventViewDto testEventViewDto;
    private EventService toTest;
    private EventEntity testEventEntity;
    @Captor
    private ArgumentCaptor<EventEntity> eventEntityArgumentCaptor;

    public EventServiceTest() {
    }

    @BeforeEach
    void setUp() {
        toTest = new EventService(mockEventRepository, mockEventMapper);
        testEventEntity = new EventEntity() {
            {
                setId(1L);
                setTitle("Event");
                setDescription("Description");
                setUrlLink("URL Event");
                setStartDate(LocalDate.of(2023, 10, 1));
                setExpiryDate(LocalDate.of(2023, 10, 15));
                setLocation(Location.Bulgarian);
            }
        };


        testEventDto = new EventDto() {
            {
                setId(1L);
                setTitle("Event");
                setDescription("Description");
                setUrlLink("URL Event");
                setStartDate(LocalDate.of(2023, 10, 1));
                setExpiryDate(LocalDate.of(2023, 10, 15));
                setLocation(Location.Bulgarian);
            }

        };
        testEventViewDto = new EventViewDto() {
            {
                setId(1L);
                setTitle("Event");
                setDescription("Description");
                setUrlLink("URL Event");
                setStartDate(LocalDate.of(2023, 10, 1));
                setExpiryDate(LocalDate.of(2023, 10, 15));
                setLocation(Location.Bulgarian);
            }
        };

        when(mockEventRepository.findById(1L)).thenReturn(Optional.of(testEventEntity));
        when(mockEventRepository.findById(2L)).thenReturn(Optional.empty());
        when(mockEventRepository.findAll()).thenReturn(List.of(testEventEntity));
        when(mockEventMapper.eventEntityToEventViewDto(testEventEntity)).thenReturn(testEventViewDto);
        when(mockEventMapper.eventDtoToEventEntity(testEventDto)).thenReturn(testEventEntity);

    }

    @Test
    void addEvent_SaveInvokedTest() {
        toTest.addEvent(testEventDto);
        Mockito.verify(mockEventRepository).save(any());
    }

    @Test
    void testAddEvent_withCaptor() {
        toTest.addEvent(testEventDto);
        Mockito.verify(mockEventRepository).save(eventEntityArgumentCaptor.capture());
        EventEntity savedEventEntity = eventEntityArgumentCaptor.getValue();
        assertEquals(testEventDto.getUrlLink(), savedEventEntity.getUrlLink());
        assertEquals(testEventDto.getLocation(), savedEventEntity.getLocation());
        assertEquals(testEventDto.getStartDate(), savedEventEntity.getStartDate());
        assertEquals(testEventDto.getExpiryDate(), savedEventEntity.getExpiryDate());
        assertEquals(testEventDto.getTitle(), savedEventEntity.getTitle());
        assertEquals(testEventDto.getDescription(), savedEventEntity.getDescription());

    }

    @Test
    void testGetAllBG() {
        EventsViewDto expected = new EventsViewDto();
        expected.setPassedBg(List.of(testEventViewDto));
        assertEquals(expected.getPassedBg(), toTest.getAll().getPassedBg());
        assertEquals(expected.getPassedInt(), toTest.getAll().getPassedInt());
        assertEquals(expected.getUpcomingBg(), toTest.getAll().getUpcomingBg());
        assertEquals(expected.getUpcomingInt(), toTest.getAll().getUpcomingInt());
    }


    @Test
    void testGetAllInternational() {
        testEventViewDto.setLocation(Location.International);
        testEventEntity.setLocation(Location.International);
        when(mockEventMapper.eventEntityToEventViewDto(testEventEntity)).thenReturn(testEventViewDto);
        EventsViewDto expected = new EventsViewDto();
        expected.setPassedInt(List.of(testEventViewDto));
        assertEquals(expected.getPassedBg(), toTest.getAll().getPassedBg());
        assertEquals(expected.getPassedInt(), toTest.getAll().getPassedInt());
        assertEquals(expected.getUpcomingBg(), toTest.getAll().getUpcomingBg());
        assertEquals(expected.getUpcomingInt(), toTest.getAll().getUpcomingInt());
    }


    @Test
    void testDeleteEventWhenExist() {
        toTest.deleteEvent(1L);
        Mockito.verify(mockEventRepository, times(1)).deleteById(1L);

    }

    @Test()
    void testDeleteEventWhenNotExist() {
        Exception exception = assertThrows(EventNotFoundException.class, () -> toTest.deleteEvent(2L));
        String expectedMessage = "Event with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Mockito.verify(mockEventRepository, times(0)).deleteById(2L);
    }

    @Test
    void testEditEventWhenIsUpdated() {
        EventDto edited = testEventDto;
        edited.setTitle("New");
        when(mockEventRepository.findById(edited.getId())).thenReturn(Optional.of(testEventEntity));
        EventEntity testTempEventEntity = new EventEntity() {{
            setId(1L);
            setTitle(edited.getTitle());
            setStartDate(LocalDate.of(2023, 10, 1));
            setExpiryDate(LocalDate.of(2023, 10, 15));
            setLocation(Location.Bulgarian);
            setDescription("Description");
            setUrlLink("URL Event");
        }};

        when(mockEventMapper.eventDtoToEventEntity(edited)).thenReturn(testTempEventEntity);
        toTest.editEvent(edited.getId(), edited);
        Mockito.verify(mockEventRepository, times(1)).findById(testEventEntity.getId());
        Mockito.verify(mockEventRepository, times(1)).save(testTempEventEntity);
    }

    @Test
    void testEditEventWhenIsNotUpdated() {
        when(mockEventMapper.eventDtoToEventEntity(testEventDto)).thenReturn(testEventEntity);
        toTest.editEvent(testEventDto.getId(), testEventDto);
        Mockito.verify(mockEventRepository, times(1)).findById(testEventEntity.getId());
        Mockito.verify(mockEventRepository, times(0)).save(testEventEntity);

    }

    @Test
    void testEditEventWhenEventIsNotFound() {

        Exception exception = assertThrows(EventNotFoundException.class, () -> toTest.editEvent(2L, testEventDto));
        String expectedMessage = "Event with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Mockito.verify(mockEventRepository, times(1)).findById(2L);
        Mockito.verify(mockEventRepository, times(0)).save(testEventEntity);
    }
}
