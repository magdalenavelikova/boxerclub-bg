package bg.boxerclub.boxerclubbgrestserver.listener;

import bg.boxerclub.boxerclubbgrestserver.event.OnDogRegistrationCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.service.impl.dog.DogRegistrationOrEditMailServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class DogRegistrationOrEditListener implements
        ApplicationListener<OnDogRegistrationCompleteEvent> {
    private final DogRegistrationOrEditMailServiceImpl mailService;

    public DogRegistrationOrEditListener(DogRegistrationOrEditMailServiceImpl mailService) {
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(OnDogRegistrationCompleteEvent event) {
        try {
            mailService.sendNewDogRegistrationEmail(event.getRegistrationNum(), event.getLocale());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
