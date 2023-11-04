package bg.boxerclub.boxerclubbgrestserver.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Parent is younger than child")
public class ParentYoungerThanChildException extends RuntimeException {

    private String registrationNum;

    public ParentYoungerThanChildException(String registrationNum) {
        super("Parent with registration number " + registrationNum + " is younger than child !");
        this.registrationNum = registrationNum;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }
}
