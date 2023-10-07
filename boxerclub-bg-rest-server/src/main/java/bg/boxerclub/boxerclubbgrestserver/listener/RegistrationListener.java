package bg.boxerclub.boxerclubbgrestserver.listener;

import bg.boxerclub.boxerclubbgrestserver.event.OnRegistrationCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.service.MailService;
import bg.boxerclub.boxerclubbgrestserver.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {


    private final UserService userService;
    private final MailService mailService;

    public RegistrationListener(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }


    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException, UnsupportedEncodingException {
        UserEntity user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        mailService.sendVerificationEmail(user.getFullName(),
                user.getEmail(),
                event.getLocale(),
                confirmationUrl);
    }
}