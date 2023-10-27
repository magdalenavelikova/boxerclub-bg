package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventsDto;
import bg.boxerclub.boxerclubbgrestserver.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("")
    public ResponseEntity<EventsDto> getAllEvents() {
        return
                ResponseEntity.ok(eventService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') ")
    public ResponseEntity<EventViewDto> addLink(@RequestBody @Valid EventDto eventDto, @AuthenticationPrincipal BoxerClubUserDetails user) {
        return
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(eventService.addEvent(eventDto));
    }
}
