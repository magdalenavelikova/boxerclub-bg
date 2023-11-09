package bg.boxerclub.boxerclubbgrestserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email/Username is not unique.")
public class UserNotUniqueException extends RuntimeException {

    private final String username;

    public UserNotUniqueException(String username) {
        super("There is already a registered user with this email " + username + "!");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
