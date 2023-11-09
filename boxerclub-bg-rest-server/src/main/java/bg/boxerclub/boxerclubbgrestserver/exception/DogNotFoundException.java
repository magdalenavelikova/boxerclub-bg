package bg.boxerclub.boxerclubbgrestserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Dog was not found.")
public class DogNotFoundException extends RuntimeException {

    private Long id;
    private String registrationNum;

    public DogNotFoundException(Long id) {
        super("Dog with ID " + id + " not found!");
        this.id = id;
    }

    public DogNotFoundException(String registrationNum) {
        super("Dog with RegistrationNum " + registrationNum + " not found!");
        this.registrationNum = registrationNum;
    }

    public Long getId() {
        return id;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }
}
