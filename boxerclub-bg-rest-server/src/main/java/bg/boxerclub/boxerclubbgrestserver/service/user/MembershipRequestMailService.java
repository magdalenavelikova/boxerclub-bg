package bg.boxerclub.boxerclubbgrestserver.service.dog;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public interface DogRegistrationMailService {
    void sendNewDogRegistrationEmail(
            String registrationNum, Locale preferredLocale


    ) throws UnsupportedEncodingException;
}
