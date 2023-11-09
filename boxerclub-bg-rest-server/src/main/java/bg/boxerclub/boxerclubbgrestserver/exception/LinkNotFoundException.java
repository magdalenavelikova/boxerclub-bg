package bg.boxerclub.boxerclubbgrestserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Link was not found.")
public class LinkNotFoundException extends RuntimeException {
    private Long id;

    public LinkNotFoundException(Long id) {
        super("Link with ID " + id + " not found!");
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
