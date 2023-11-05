package bg.boxerclub.boxerclubbgrestserver.event;

import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnChangeOwnershipCompleteEvent extends ApplicationEvent {
    private DogViewDto dogViewDto;

    private UserEntity currentOwner;

    private UserEntity newOwner;
    private Locale locale;
    private String requestURL;

    public OnChangeOwnershipCompleteEvent(Object source, DogViewDto dogViewDto, UserEntity currentOwner, UserEntity newOwner, Locale locale, String requestURL) {
        super(source);
        this.dogViewDto = dogViewDto;
        this.currentOwner = currentOwner;
        this.locale = locale;
        this.newOwner = newOwner;
        this.requestURL = requestURL;
    }

    public DogViewDto getDogViewDto() {
        return dogViewDto;
    }

    public OnChangeOwnershipCompleteEvent setDogViewDto(DogViewDto dogViewDto) {
        this.dogViewDto = dogViewDto;
        return this;
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

    public String getRequestURL() {
        return requestURL;
    }

    public OnChangeOwnershipCompleteEvent setRequestURL(String requestURL) {
        this.requestURL = requestURL;
        return this;
    }
}
