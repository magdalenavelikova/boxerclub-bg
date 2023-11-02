package bg.boxerclub.boxerclubbgrestserver.event;

import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserDto;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnUserRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private UserDto user;

    public OnUserRegistrationCompleteEvent(Object source,
                                           UserDto user, Locale locale, String appUrl) {
        super(source);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public OnUserRegistrationCompleteEvent setAppUrl(String appUrl) {
        this.appUrl = appUrl;
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public OnUserRegistrationCompleteEvent setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public UserDto getUser() {
        return user;
    }

    public OnUserRegistrationCompleteEvent setUser(UserDto user) {
        this.user = user;
        return this;
    }
}
