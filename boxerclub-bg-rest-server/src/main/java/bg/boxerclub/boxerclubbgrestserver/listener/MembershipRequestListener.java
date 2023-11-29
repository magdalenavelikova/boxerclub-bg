package bg.boxerclub.boxerclubbgrestserver.listener;

import bg.boxerclub.boxerclubbgrestserver.event.OnMembershipRequestCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.service.user.MembershipRequestMailService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class MembershipRequestListener implements
        ApplicationListener<OnMembershipRequestCompleteEvent> {
    private final MembershipRequestMailService mailService;

    public MembershipRequestListener(MembershipRequestMailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(OnMembershipRequestCompleteEvent event) {
        try {
            mailService.sendMembershipRequestEmail(event.getUsername(), event.getLocale());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
