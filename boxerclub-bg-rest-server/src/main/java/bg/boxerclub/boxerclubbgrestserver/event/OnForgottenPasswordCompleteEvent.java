package bg.boxerclub.boxerclubbgrestserver.event;

import bg.boxerclub.boxerclubbgrestserver.model.dto.user.AuthRequest;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnForgottenPasswordCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private AuthRequest request;

    public OnForgottenPasswordCompleteEvent(Object source, String appUrl, Locale locale, AuthRequest request) {
        super(source);
        this.appUrl = appUrl;
        this.locale = locale;
        this.request = request;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public OnForgottenPasswordCompleteEvent setAppUrl(String appUrl) {
        this.appUrl = appUrl;
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public OnForgottenPasswordCompleteEvent setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public AuthRequest getRequest() {
        return request;
    }

    public OnForgottenPasswordCompleteEvent setRequest(AuthRequest request) {
        this.request = request;
        return this;
    }
}
