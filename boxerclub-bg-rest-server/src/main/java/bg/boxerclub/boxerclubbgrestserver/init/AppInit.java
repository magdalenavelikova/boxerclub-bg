package bg.boxerclub.boxerclubbgrestserver.init;

import bg.boxerclub.boxerclubbgrestserver.service.BlackListService;
import bg.boxerclub.boxerclubbgrestserver.service.ContactService;
import bg.boxerclub.boxerclubbgrestserver.service.JobService;
import bg.boxerclub.boxerclubbgrestserver.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {
    private final UserService userService;
    private final JobService jobService;
    private final ContactService contactService;
    private final BlackListService blackListService;

    public AppInit(UserService userService, JobService jobService, ContactService contactService, BlackListService blackListService) {
        this.userService = userService;
        this.jobService = jobService;
        this.contactService = contactService;
        this.blackListService = blackListService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.init();
        jobService.init();
        contactService.init();
        blackListService.init();


    }
}