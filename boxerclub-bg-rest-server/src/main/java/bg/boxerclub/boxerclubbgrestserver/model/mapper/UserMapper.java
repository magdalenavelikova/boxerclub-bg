package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.dto.UserRegisterDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //@Mapping(target = "email", source = "username")
    UserEntity userDtoToUserEntity(UserRegisterDto registerDto);


}
