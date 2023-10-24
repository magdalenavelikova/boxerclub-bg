package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.dto.LinkDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    LinkDto linkEntityToLinkDto(LinkEntity linkEntity);
}
