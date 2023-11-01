package bg.boxerclub.boxerclubbgrestserver.listener;

import bg.boxerclub.boxerclubbgrestserver.event.OnUserRegistrationCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserDto;
import bg.boxerclub.boxerclubbgrestserver.service.user.UserRegistrationMailService;
import bg.boxerclub.boxerclubbgrestserver.service.user.UserService;
import jakarta.mail.MessagingException;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
public class UserRegistrationListener implements
        ApplicationListener<OnUserRegistrationCompleteEvent> {


    private final UserService userService;
    private final UserRegistrationMailService userRegistrationMailService;

    public UserRegistrationListener(UserService userService, UserRegistrationMailService userRegistrationMailService) {
        this.userService = userService;
        this.userRegistrationMailService = userRegistrationMailService;
    }


    @Override
    public void onApplicationEvent(OnUserRegistrationCompleteEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmRegistration(OnUserRegistrationCompleteEvent event) throws MessagingException, UnsupportedEncodingException {
        UserDto user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        userRegistrationMailService.sendVerificationEmail(user.getFullName(),
                user.getEmail(),
                event.getLocale(),
                confirmationUrl);
    }
}