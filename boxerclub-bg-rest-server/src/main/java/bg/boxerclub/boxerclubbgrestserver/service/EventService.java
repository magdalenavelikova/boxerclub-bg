package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.exception.EventNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventsViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.EventEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Location;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.EventMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;


    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;

    }


    public EventsViewDto getAll() {
        List<EventViewDto> events = eventRepository.findAll()
                .stream()
                .map(eventMapper::eventEntityToEventViewDto).toList();

        EventsViewDto eventsViewDto = new EventsViewDto();
        events.forEach(e -> {
            if (e.getLocation().equals(Location.Bulgarian)) {
                if (e.getExpiryDate().isAfter(LocalDate.now()) || e.getExpiryDate().isEqual(LocalDate.now())) {
                    eventsViewDto.getUpcomingBg().add(e);
                } else {
                    eventsViewDto.getPassedBg().add(e);
                }
            }
            if (e.getLocation().equals(Location.International)) {
                if (e.getExpiryDate().isAfter(LocalDate.now()) || e.getExpiryDate().isEqual(LocalDate.now())) {
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
            throw new EventNotFoundException(id);
        }
    }

    public EventViewDto editEvent(Long id, EventDto eventDto) {
        EventEntity edit = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        EventEntity temp = eventMapper.eventDtoToEventEntity(eventDto);
        if (!temp.equals(edit)) {
            return eventMapper.eventEntityToEventViewDto(eventRepository.save(temp));
        }
        return eventMapper.eventEntityToEventViewDto(edit);
    }


}
