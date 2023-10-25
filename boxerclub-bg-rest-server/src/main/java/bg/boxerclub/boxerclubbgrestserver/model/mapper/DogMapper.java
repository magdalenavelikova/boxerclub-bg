package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DogMapper {
    DogEntity dogRegisterDtoToDogEntity(RegisterDogDto registerDto);

    SavedDogDto dogEntityToSavedDogDto(DogEntity dogEntity);

    @Mapping(source = "owner.id", target = "ownerId")
    DogViewDto dogEntityToDogViewDto(DogEntity dogEntity);

    @Mapping(source = "owner.email", target = "ownerEmail")
    @Mapping(source = "mother.registrationNum", target = "motherRegistrationNum")
    @Mapping(source = "father.registrationNum", target = "fatherRegistrationNum")
    EditDogViewDto dogEntityToEditViewDogDto(DogEntity dogEntity);

    DogEntity editDogDtoToDogEntity(EditDogDto editDogDto);

    ParentDto dogEntityToParentDto(DogEntity dogEntity);

    DogEntity parentDtoToDogEntity(ParentDto parentDto);
}

