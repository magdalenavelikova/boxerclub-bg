package bg.boxerclub.boxerclubbgrestserver.util;

import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ParentFieldSetMapper implements FieldSetMapper<DogEntity> {

    private final DogRepository dogRepository;

    public ParentFieldSetMapper(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Override
    public @NotNull DogEntity mapFieldSet(FieldSet fs) {
        DogEntity dogEntity = dogRepository.findDogEntityByRegistrationNum(fs.readString("registration_num")).orElseThrow(null);
        if (dogEntity != null) {
            if (!fs.readString("father_rn").isEmpty()) {
                Optional<DogEntity> father = dogRepository.findDogEntityByRegistrationNum(fs.readString("father_rn"));
                father.ifPresent(dogEntity::setFather);
            }
            if (!fs.readString("mother_rn").isEmpty()) {
                Optional<DogEntity> mother = dogRepository.findDogEntityByRegistrationNum(fs.readString("mother_rn"));
                mother.ifPresent(dogEntity::setMother);
            }
        }
        return dogEntity;
    }

}

