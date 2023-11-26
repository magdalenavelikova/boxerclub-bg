package bg.boxerclub.boxerclubbgrestserver.listener;

import bg.boxerclub.boxerclubbgrestserver.event.OnDogRegistrationCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.service.impl.dog.DogRegistrationMailServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class DogRegistrationListener implements
        ApplicationListener<OnDogRegistrationCompleteEvent> {
    private final DogRegistrationMailServiceImpl mailService;

    public DogRegistrationListener(DogRegistrationMailServiceImpl mailService) {
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
