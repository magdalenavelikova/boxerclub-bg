package bg.boxerclub.boxerclubbgrestserver.listener;


import bg.boxerclub.boxerclubbgrestserver.event.OnChangeOwnershipCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.service.impl.dog.ChangeOwnershipDogMailServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class ChangeOwnershipListener implements
        ApplicationListener<OnChangeOwnershipCompleteEvent> {
    private final ChangeOwnershipDogMailServiceImpl mailService;

    public ChangeOwnershipListener(ChangeOwnershipDogMailServiceImpl mailService) {
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(OnChangeOwnershipCompleteEvent event) {
        try {

            mailService.sendEmail(event.getCurrentOwner(),
                    event.getNewOwner(),
                    event.getDogViewDto(),
                    event.getLocale(),
                    event.getRequestURL());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}