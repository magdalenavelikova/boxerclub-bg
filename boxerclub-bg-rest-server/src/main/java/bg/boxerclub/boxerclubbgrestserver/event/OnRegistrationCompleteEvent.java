package bg.boxerclub.boxerclubbgrestserver.event;

import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private UserEntity user;

    public OnRegistrationCompleteEvent(
            UserEntity user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public OnRegistrationCompleteEvent setAppUrl(String appUrl) {
        this.appUrl = appUrl;
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public OnRegistrationCompleteEvent setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public OnRegistrationCompleteEvent setUser(UserEntity user) {
        this.user = user;
        return this;
    }

}
