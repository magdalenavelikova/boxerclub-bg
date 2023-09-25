package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.dto.DogRegisterDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.ParentDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DogMapper {
    DogEntity dogRegisterDtoToDogEntity(DogRegisterDto registerDto);

    ParentDto dogEntityToParentDto(DogEntity dogEntity);
}
