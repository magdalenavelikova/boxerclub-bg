package bg.boxerclub.boxerclubbgrestserver.util;

import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Color;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Sex;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DogFieldSetMapper implements FieldSetMapper<DogEntity> {
    private final UserRepository userRepository;


    public DogFieldSetMapper(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public @NotNull DogEntity mapFieldSet(FieldSet fs) {
        DogEntity dogEntity = new DogEntity();
        dogEntity.setId(Long.valueOf(fs.readString("id")));
        if (!fs.readString("birthday").isEmpty()) {
            dogEntity.setBirthday(LocalDate.parse(fs.readString("birthday")));
        }

        dogEntity.setColor(Color.valueOf(fs.readString("color")));
        if (!fs.readString("date_of_decease").isEmpty()) {
            dogEntity.setDateOfDecease(LocalDate.parse(fs.readString("date_of_decease")));
        }
        dogEntity.setHealthStatus(fs.readString("health_status"));
        dogEntity.setApproved(Boolean.valueOf(fs.readString("is_approved")));
        dogEntity.setKennel(fs.readString("kennel"));
        dogEntity.setMicroChip(fs.readString("micro_chip"));
        dogEntity.setName(fs.readString("name"));
        dogEntity.setPictureUrl(fs.readString("picture_url"));
        dogEntity.setRegistrationNum(fs.readString("registration_num"));
        dogEntity.setSex(Sex.valueOf(fs.readString("sex")));
        if (!fs.readString("owner_id").isEmpty()) {
            dogEntity.setOwner(userRepository.findById(Long.valueOf(fs.readString("owner_id"))).orElseThrow());
        }

        return dogEntity;
    }

}
