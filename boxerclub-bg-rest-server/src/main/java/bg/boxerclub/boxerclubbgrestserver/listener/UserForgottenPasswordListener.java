package bg.boxerclub.boxerclubbgrestserver.listener;

import bg.boxerclub.boxerclubbgrestserver.event.OnForgottenPasswordCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserDto;
import bg.boxerclub.boxerclubbgrestserver.service.impl.user.UserForgottenPasswordMailServiceImpl;
import bg.boxerclub.boxerclubbgrestserver.service.impl.user.UserServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
public class UserForgottenPasswordListener implements
        ApplicationListener<OnForgottenPasswordCompleteEvent> {
    private final UserServiceImpl userService;
    private final UserForgottenPasswordMailServiceImpl userForgottenPasswordMailService;

    public UserForgottenPasswordListener(UserServiceImpl userService, UserForgottenPasswordMailServiceImpl userForgottenPasswordMailService) {
        this.userService = userService;
        this.userForgottenPasswordMailService = userForgottenPasswordMailService;
    }

    @Override
    public void onApplicationEvent(OnForgottenPasswordCompleteEvent event) {
        try {
            this.changeForgottenPassword(event);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeForgottenPassword(OnForgottenPasswordCompleteEvent event) throws MessagingException, UnsupportedEncodingException {
        UserDto user = userService.getUserByUserEmail(event.getRequest());
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        String confirmationUrl
                = event.getAppUrl() + "/new-password?token=" + token;
        userForgottenPasswordMailService.sendForgottenPasswordEmail(user.getFullName(),
                user.getEmail(),
                event.getLocale(),
                confirmationUrl);
    }
}
