package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventsViewDto;

public interface EventService {
    EventsViewDto getAll();

    EventViewDto addEvent(EventDto eventDto);

    void deleteEvent(Long id);

    EventViewDto editEvent(Long id, EventDto eventDto);
}
