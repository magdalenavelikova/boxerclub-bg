package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.EditUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.RegisterUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity userRegisterDtoToUserEntity(RegisterUserDto registerDto);

    UserEntity userDtoToUserEntity(UserDto userDto);

    UserEntity boxerClubUserDetailsToUserEntity(BoxerClubUserDetails boxerClubUserDetails);

    UserEntity userEditDtoToUserEntity(EditUserDto editUserDto);

    EditUserDto userEntityToUserEditDto(UserEntity userEntity);

    @Mapping(source = "created", target = "created", dateFormat = "dd.MM.yyyy")
    UserDto userEntityToUserDto(UserEntity userEntity);

}
