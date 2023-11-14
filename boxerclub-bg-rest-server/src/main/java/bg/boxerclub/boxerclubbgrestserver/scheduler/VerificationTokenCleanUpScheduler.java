package bg.boxerclub.boxerclubbgrestserver.scheduler;

import bg.boxerclub.boxerclubbgrestserver.model.entity.VerificationToken;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.VerificationTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class VerificationTokenCleanUpScheduler {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public VerificationTokenCleanUpScheduler(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }


    @Scheduled(cron = "0 9 * * * *", zone = "Europe/Sofia")
    public void clenUpTokens() {
        List<VerificationToken> tokens = verificationTokenRepository.findAll();
        Calendar cal = Calendar.getInstance();
        tokens.forEach(verificationToken -> {
            if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                verificationTokenRepository.delete(verificationToken);
            }
            if (!verificationToken.getUser().isEnabled()) {
                userRepository.delete(verificationToken.getUser());
            }
        });
    }
}
