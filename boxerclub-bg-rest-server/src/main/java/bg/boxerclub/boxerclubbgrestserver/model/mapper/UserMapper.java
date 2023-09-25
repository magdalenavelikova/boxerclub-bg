package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.UserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.UserEditDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.UserRegisterDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity userDtoToUserEntity(UserRegisterDto registerDto);

    UserEntity boxerClubUserDetailsToUserEntity(BoxerClubUserDetails boxerClubUserDetails);

    UserEntity userEditDtoToUserEntity(UserEditDto userEditDto);

    @Mapping(source = "created", target = "created", dateFormat = "dd.MM.yyyy")
    UserDto userEntityToUserDto(UserEntity userEntity);

}
