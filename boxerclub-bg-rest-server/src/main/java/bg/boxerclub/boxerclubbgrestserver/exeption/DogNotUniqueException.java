package bg.boxerclub.boxerclubbgrestserver.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Registration number is not unique.")
public class DogNotUniqueException extends RuntimeException {

    private final String registrationNum;

    public DogNotUniqueException(String registrationNum) {
        super("There is already a registered dog with number " + registrationNum + "!");
        this.registrationNum = registrationNum;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }
}
