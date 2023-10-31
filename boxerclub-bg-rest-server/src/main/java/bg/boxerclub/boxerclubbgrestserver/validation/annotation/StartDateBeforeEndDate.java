package bg.boxerclub.boxerclubbgrestserver.validation.annotation;

import bg.boxerclub.boxerclubbgrestserver.validation.StartDateBeforeEndDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = StartDateBeforeEndDateValidator.class)
public @interface StartDateBeforeEndDate {

    String startDate();

    String expiryDate();

    String message() default "Expiry date must be after start date!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
