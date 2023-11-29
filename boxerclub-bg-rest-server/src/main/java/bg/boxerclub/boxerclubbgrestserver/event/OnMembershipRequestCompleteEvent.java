package bg.boxerclub.boxerclubbgrestserver.event;

import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnDogRegistrationCompleteEvent extends ApplicationEvent {

    private String registrationNum;
    private Locale locale;

    public OnDogRegistrationCompleteEvent(Object source, String registrationNum, Locale locale) {
        super(source);

        this.registrationNum = registrationNum;
        this.locale = locale;
    }


    public String getRegistrationNum() {
        return registrationNum;
    }

    public OnDogRegistrationCompleteEvent setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public OnDogRegistrationCompleteEvent setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }
}
