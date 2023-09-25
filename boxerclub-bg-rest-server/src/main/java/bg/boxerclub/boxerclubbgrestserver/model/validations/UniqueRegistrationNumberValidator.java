package bg.boxerclub.boxerclubbgrestserver.model.validations;

import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueRegistrationNumberValidator implements ConstraintValidator<UniqueRegistrationNumber, String> {
    private final DogRepository dogRepository;

    public UniqueRegistrationNumberValidator(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return dogRepository.findDogEntityByRegistrationNum(value).isEmpty();
    }
}
