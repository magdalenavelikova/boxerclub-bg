package bg.boxerclub.boxerclubbgrestserver.validation.annotation;

import bg.boxerclub.boxerclubbgrestserver.validation.UniqueRegistrationNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueRegistrationNumberValidator.class)
public @interface UniqueRegistrationNumber {
    String message() default "Invalid registration number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
