package bg.boxerclub.boxerclubbgrestserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoxerclubBgRestServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoxerclubBgRestServerApplication.class, args);
    }

}
