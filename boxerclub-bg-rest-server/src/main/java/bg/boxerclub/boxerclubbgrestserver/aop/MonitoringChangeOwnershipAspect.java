package bg.boxerclub.boxerclubbgrestserver.aop;

import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogDtoWithNewOwner;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class MonitoringChangeOwnershipAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringChangeOwnershipAspect.class);


    @Around("Pointcuts.trackChangeOwnership()")
    public Object changeOwnership(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args.length > 0 && args[0] instanceof DogDtoWithNewOwner dog) {
            String registrationNum = dog.getRegistrationNum();

            LOGGER.info("Change ownership was performed for dog with registrationNum: {}", registrationNum);
        } else {
            LOGGER.warn("Invalid method arguments for changeOwnership");
        }

        return pjp.proceed();
    }


    @Around("Pointcuts.trackChangeOwnershipConfirm()")
    public Object changeOwnershipConfirm(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args.length > 0 && args[0] instanceof String) {
            String registrationNum = args[0].toString();

            LOGGER.info("Change ownership was confirmed for dog with registrationNum: {}", registrationNum);

        } else {
            LOGGER.warn("Invalid method arguments for changeOwnershipConfirm");
        }

        return pjp.proceed();
    }

}
