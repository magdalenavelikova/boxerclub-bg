package bg.boxerclub.boxerclubbgrestserver.service.user;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public interface MembershipRequestMailService {
    void sendMembershipRequestEmail(
            String username, Locale preferredLocale


    ) throws UnsupportedEncodingException;
}
