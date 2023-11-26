package bg.boxerclub.boxerclubbgrestserver.service.user;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public interface UserForgottenPasswordMailService {
    void sendForgottenPasswordEmail(
            String fullName,
            String userEmail,
            Locale preferredLocale,
            String confirmationUrl
    ) throws UnsupportedEncodingException;
}
