package bg.boxerclub.boxerclubbgrestserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Contact was not found.")
public class ContactNotFoundException extends RuntimeException {
    private Long id;

    public ContactNotFoundException(Long id) {
        super("Contact with ID " + id + " not found!");
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
