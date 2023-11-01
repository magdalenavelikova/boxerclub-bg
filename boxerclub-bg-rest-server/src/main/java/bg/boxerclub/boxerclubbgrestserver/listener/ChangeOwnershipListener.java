package bg.boxerclub.boxerclubbgrestserver.listener;


import bg.boxerclub.boxerclubbgrestserver.event.OnChangeOwnershipCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogViewDto;
import bg.boxerclub.boxerclubbgrestserver.service.dog.ChangeOwnershipDogMailService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class ChangeOwnershipListener implements
        ApplicationListener<OnChangeOwnershipCompleteEvent> {
    private final ChangeOwnershipDogMailService mailService;

    public ChangeOwnershipListener(ChangeOwnershipDogMailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(OnChangeOwnershipCompleteEvent event) {
        try {
            DogViewDto dog = (DogViewDto) event.getSource();
            mailService.sendEmail(event.getCurrentOwner(), event.getNewOwner(), dog, event.getLocale());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}