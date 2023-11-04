package bg.boxerclub.boxerclubbgrestserver.scheduler;

import bg.boxerclub.boxerclubbgrestserver.model.entity.VerificationToken;
import bg.boxerclub.boxerclubbgrestserver.repository.VerificationTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class VerificationTokenCleanUpScheduler {
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenCleanUpScheduler(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }


    @Scheduled(cron = "0 0 * * * *", zone = "Europe/Sofia")
    public void clenUpTokens() {
        List<VerificationToken> tokens = verificationTokenRepository.findAll();
        Calendar cal = Calendar.getInstance();
        tokens.forEach(verificationToken -> {
            if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                verificationTokenRepository.delete(verificationToken);
            }

        });
    }
}
