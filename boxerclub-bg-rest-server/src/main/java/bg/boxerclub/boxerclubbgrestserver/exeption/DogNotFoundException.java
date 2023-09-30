package bg.boxerclub.boxerclubbgrestserver.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Dog was not found.")
public class DogNotFoundException extends RuntimeException {

    private Long id;

    public DogNotFoundException(Long id) {
        super("Dog with ID " + id + " not found!");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
