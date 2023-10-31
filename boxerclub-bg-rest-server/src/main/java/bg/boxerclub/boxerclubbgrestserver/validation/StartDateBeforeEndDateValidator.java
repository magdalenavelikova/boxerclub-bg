package bg.boxerclub.boxerclubbgrestserver.validation;

import bg.boxerclub.boxerclubbgrestserver.validation.annotation.StartDateBeforeEndDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.time.LocalDate;

public class StartDateBeforeEndDateValidator implements ConstraintValidator<StartDateBeforeEndDate, Object> {
    private String startDate;

    private String expiryDate;

    private String message;

    @Override
    public void initialize(StartDateBeforeEndDate constraintAnnotation) {
        this.startDate = constraintAnnotation.startDate();
        this.expiryDate = constraintAnnotation.expiryDate();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);


        final LocalDate startDate = LocalDate.parse(beanWrapper.getPropertyValue(this.startDate).toString());

        final LocalDate endDate = LocalDate.parse(beanWrapper.getPropertyValue(this.expiryDate).toString());

        if (startDate.isAfter(endDate)) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(expiryDate)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return !startDate.isAfter(endDate);
    }
}
