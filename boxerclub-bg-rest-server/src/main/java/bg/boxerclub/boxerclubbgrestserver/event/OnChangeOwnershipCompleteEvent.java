package bg.boxerclub.boxerclubbgrestserver.event;

import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnChangeOwnershipCompleteEvent extends ApplicationEvent {

    private UserEntity currentOwner;

    private UserEntity newOwner;
    private Locale locale;

    public OnChangeOwnershipCompleteEvent(Object source, UserEntity currentOwner, UserEntity newOwner, Locale locale) {
        super(source);

        this.currentOwner = currentOwner;
        this.locale = locale;
        this.newOwner = newOwner;
    }

    public UserEntity getCurrentOwner() {
        return currentOwner;
    }

    public OnChangeOwnershipCompleteEvent setCurrentOwner(UserEntity currentOwner) {
        this.currentOwner = currentOwner;
        return this;
    }

    public UserEntity getNewOwner() {
        return newOwner;
    }

    public OnChangeOwnershipCompleteEvent setNewOwner(UserEntity newOwner) {
        this.newOwner = newOwner;
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public OnChangeOwnershipCompleteEvent setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }
}
