package bg.boxerclub.boxerclubbgrestserver.service.user;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public interface UserRegistrationMailService {
    void sendVerificationEmail(
            String fullName,
            String userEmail,
            Locale preferredLocale,
            String confirmationUrl
    ) throws UnsupportedEncodingException;
}
