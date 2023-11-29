package bg.boxerclub.boxerclubbgrestserver.event;

import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnMembershipRequestCompleteEvent extends ApplicationEvent {

    private String username;
    private Locale locale;

    public OnMembershipRequestCompleteEvent(Object source, String username, Locale locale) {
        super(source);

        this.username = username;
        this.locale = locale;
    }


    public String getUsername() {
        return username;
    }

    public OnMembershipRequestCompleteEvent setUsername(String username) {
        this.username = username;
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public OnMembershipRequestCompleteEvent setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }
}
