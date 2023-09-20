package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.dto.UserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.UserRegisterDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserEntity userDtoToUserEntity(UserRegisterDto registerDto);

    @Mapping(source = "created", target = "created", dateFormat = "dd.MM.yyyy")
    UserDto userEntityToUserDto(UserEntity userEntity);

}
