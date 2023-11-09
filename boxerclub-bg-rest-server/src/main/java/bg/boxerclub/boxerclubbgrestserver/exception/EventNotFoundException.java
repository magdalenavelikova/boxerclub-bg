package bg.boxerclub.boxerclubbgrestserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event was not found.")
public class EventNotFoundException extends RuntimeException {
    private Long id;

    public EventNotFoundException(Long id) {
        super("Event with ID " + id + " not found!");
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
