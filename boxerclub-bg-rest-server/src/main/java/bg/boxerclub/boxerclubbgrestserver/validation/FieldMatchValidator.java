package bg.boxerclub.boxerclubbgrestserver.validation;

import bg.boxerclub.boxerclubbgrestserver.validation.annotation.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String first;
    private String second;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
        Object firstValue = beanWrapper.getPropertyValue(this.first);
        Object secondValue = beanWrapper.getPropertyValue(this.second);
        boolean valid = Objects.equals(firstValue, secondValue);
        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(second)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
