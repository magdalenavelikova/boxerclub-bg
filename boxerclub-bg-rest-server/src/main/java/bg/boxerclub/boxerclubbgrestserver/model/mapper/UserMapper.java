package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.EditUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.RegisterUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.UserDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity userDtoToUserEntity(RegisterUserDto registerDto);

    UserEntity boxerClubUserDetailsToUserEntity(BoxerClubUserDetails boxerClubUserDetails);

    UserEntity userEditDtoToUserEntity(EditUserDto editUserDto);

    EditUserDto userEntityToUserEditDto(UserEntity userEntity);

    @Mapping(source = "created", target = "created", dateFormat = "dd.MM.yyyy")
    UserDto userEntityToUserDto(UserEntity userEntity);

}
