package bg.boxerclub.boxerclubbgrestserver.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* bg.boxerclub.boxerclubbgrestserver.service.impl.dog.DogServiceImpl.changeOwnership(..))")
    public void trackChangeOwnership() {
    }

    @Pointcut("execution(* bg.boxerclub.boxerclubbgrestserver.service.impl.dog.DogServiceImpl.confirmChangeOwnership(..))")
    public void trackChangeOwnershipConfirm() {
    }

}
