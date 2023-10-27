package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventsDto;
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

    public EventService(EventRepository eventRepository, EventMapper eventMapper, DifferenceService differenceService) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }


    public EventsDto getAll() {
        List<EventViewDto> events = eventRepository.findAll()
                .stream()
                .map(eventMapper::eventEntityToEventViewDto).toList();

        EventsDto eventsDto = new EventsDto();
        events.forEach(e -> {
            if (e.getLocation().equals(Location.Bulgarian)) {
                if (status(e.getExpiryDate()).equals("upcoming")) {
                    eventsDto.getUpcomingBg().add(e);
                } else {
                    eventsDto.getPassedBg().add(e);
                }
            } else {
                if (status(e.getExpiryDate()).equals("upcoming")) {
                    eventsDto.getUpcomingInt().add(e);
                } else {
                    eventsDto.getPassedInt().add(e);
                }


            }
        });

        return eventsDto;
    }

    private static String status(LocalDate expireDate) {

        if (LocalDate.now().isAfter(expireDate)) {
            return "passed";
        } else {
            return "upcoming";

        }
    }


    public EventViewDto addEvent(EventDto eventDto) {
        EventEntity eventEntity = eventMapper.eventDtoToEventEntity(eventDto);
        eventEntity.setCreated(LocalDateTime.now());
        return eventMapper.eventEntityToEventViewDto(eventRepository.save(eventEntity));


    }
}
