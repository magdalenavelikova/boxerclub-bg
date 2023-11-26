package bg.boxerclub.boxerclubbgrestserver.init;

import bg.boxerclub.boxerclubbgrestserver.service.impl.BlackListServiceImpl;
import bg.boxerclub.boxerclubbgrestserver.service.impl.ContactServiceImpl;
import bg.boxerclub.boxerclubbgrestserver.service.impl.JobServiceImpl;
import bg.boxerclub.boxerclubbgrestserver.service.impl.user.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {
    private final UserServiceImpl userService;
    private final JobServiceImpl jobService;
    private final ContactServiceImpl contactService;
    private final BlackListServiceImpl blackListService;

    public AppInit(UserServiceImpl userService, JobServiceImpl jobService, ContactServiceImpl contactService, BlackListServiceImpl blackListService) {
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