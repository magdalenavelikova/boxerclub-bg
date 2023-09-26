package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.dto.DogDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.ParentDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.RegisterDogDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.SavedDogDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DogMapper {
    DogEntity dogRegisterDtoToDogEntity(RegisterDogDto registerDto);

    SavedDogDto dogEntityToSavedDogDto(DogEntity dogEntity);

    DogDto dogEntityToDogDto(DogEntity dogEntity);

    ParentDto dogEntityToParentDto(DogEntity dogEntity);
}
