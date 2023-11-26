package bg.boxerclub.boxerclubbgrestserver.service.user;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(BoxerClubUserDetails userDetails);

    Boolean canTokenBeRefreshed(String token);
}
