package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.dto.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DogMapper {
    DogEntity dogRegisterDtoToDogEntity(RegisterDogDto registerDto);

    SavedDogDto dogEntityToSavedDogDto(DogEntity dogEntity);

    @Mapping(source = "owner.id", target = "ownerId")
    DogDto dogEntityToDogDto(DogEntity dogEntity);

    @Mapping(source = "owner.email", target = "ownerEmail")
    @Mapping(source = "mother.registrationNum", target = "motherRegistrationNum")
    @Mapping(source = "father.registrationNum", target = "fatherRegistrationNum")
    EditDogDto dogEntityToEditDogDto(DogEntity dogEntity);

    ParentDto dogEntityToParentDto(DogEntity dogEntity);

    DogEntity parentDtoToDogEntity(ParentDto parentDto);
}

