package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventsViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.EventEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Location;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.EventMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.EventRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final DifferenceService differenceService;

    public EventService(EventRepository eventRepository, EventMapper eventMapper, DifferenceService differenceService, DifferenceService differenceService1) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.differenceService = differenceService1;
    }


    public EventsViewDto getAll() {
        List<EventViewDto> events = eventRepository.findAll()
                .stream()
                .map(eventMapper::eventEntityToEventViewDto).toList();

        EventsViewDto eventsViewDto = new EventsViewDto();
        events.forEach(e -> {
            if (e.getLocation().equals(Location.Bulgarian)) {
                if (status(e.getExpiryDate()).equals("upcoming")) {
                    eventsViewDto.getUpcomingBg().add(e);
                } else {
                    eventsViewDto.getPassedBg().add(e);
                }
            } else {
                if (status(e.getExpiryDate()).equals("upcoming")) {
                    eventsViewDto.getUpcomingInt().add(e);
                } else {
                    eventsViewDto.getPassedInt().add(e);
                }


            }
        });

        return eventsViewDto;
    }


    public EventViewDto addEvent(EventDto eventDto) {
        EventEntity eventEntity = eventMapper.eventDtoToEventEntity(eventDto);
        eventEntity.setCreated(LocalDateTime.now());
        return eventMapper.eventEntityToEventViewDto(eventRepository.save(eventEntity));


    }

    public void deleteEvent(Long id) {
        if (eventRepository.findById(id).isPresent()) {
            eventRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException(EventEntity.class, "Event");
        }
    }

    public EventViewDto editEvent(Long id, EventDto eventDto) throws NoSuchObjectException {
        EventEntity edit = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("No such event"));

        EventEntity temp = eventMapper.eventDtoToEventEntity(eventDto);
        try {

            if (!differenceService.getDifference(temp, edit).isEmpty()) {

                return eventMapper.eventEntityToEventViewDto(eventRepository.save(temp));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return eventMapper.eventEntityToEventViewDto(edit);
    }

    private static String status(LocalDate expireDate) {

        if (LocalDate.now().isAfter(expireDate)) {
            return "passed";
        } else {
            return "upcoming";

        }
    }

}
