package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventsViewDto;
import bg.boxerclub.boxerclubbgrestserver.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.rmi.NoSuchObjectException;

@RestController
@RequestMapping("/events")
@Tag(name = "Events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Get all events")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "View all events")}
    )
    @GetMapping("")
    public ResponseEntity<EventsViewDto> getAllEvents() {
        return
                ResponseEntity.ok(eventService.getAll());
    }

    @Operation(summary = "Add Event", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Event was created.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventDto.class))}),
                    @ApiResponse(responseCode = "409", description = "Some fields was incorrect."),
                    @ApiResponse(responseCode = "401", description = "User is not authorized with role ADMIN or MODERATOR.")
            }
    )
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') ")
    public ResponseEntity<EventViewDto> addEvent(@RequestBody @Valid EventDto eventDto, @AuthenticationPrincipal BoxerClubUserDetails user) {
        return
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(eventService.addEvent(eventDto));
    }

    @Operation(summary = "Delete Event", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Event was deleted."),
                    @ApiResponse(responseCode = "404", description = "Event was not found."),
                    @ApiResponse(responseCode = "401", description = "User is not authorized with role ADMIN or MODERATOR.")
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        eventService.deleteEvent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Edit Event", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Event was edited.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventDto.class))}),
                    @ApiResponse(responseCode = "404", description = "Event was not found."),
                    @ApiResponse(responseCode = "409", description = "Some fields was incorrect."),
                    @ApiResponse(responseCode = "401", description = "User is not authorized with role ADMIN or MODERATOR.")
            }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<EventViewDto> editLink(@RequestBody @Valid EventDto eventDto, @PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) throws NoSuchObjectException {
        return ResponseEntity.ok()
                .body(eventService.editEvent(id, eventDto));


    }
}
