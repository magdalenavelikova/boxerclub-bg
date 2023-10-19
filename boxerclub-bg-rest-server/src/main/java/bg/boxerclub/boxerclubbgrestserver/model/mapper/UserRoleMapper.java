package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserRoleDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserRoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleDto userRoleEntityToUserRoLeDto(UserRoleEntity userRoleEntity);
}
