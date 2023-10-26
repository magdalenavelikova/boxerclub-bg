package bg.boxerclub.boxerclubbgrestserver.init;

import bg.boxerclub.boxerclubbgrestserver.service.ContactService;
import bg.boxerclub.boxerclubbgrestserver.service.JobService;
import bg.boxerclub.boxerclubbgrestserver.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {
    private final UserService userService;
    private final JobService jobService;
    private final ContactService contactService;

    public AppInit(UserService userService, JobService jobService, ContactService contactService) {
        this.userService = userService;
        this.jobService = jobService;
        this.contactService = contactService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.init();
        jobService.init();
        contactService.init();


    }
}