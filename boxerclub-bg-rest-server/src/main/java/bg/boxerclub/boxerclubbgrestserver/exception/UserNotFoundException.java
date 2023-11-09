package bg.boxerclub.boxerclubbgrestserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User was not found.")
public class UserNotFoundException extends RuntimeException {
    private Long id;

    public UserNotFoundException(Long id) {
        super("User with ID " + id + " not found!");
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
