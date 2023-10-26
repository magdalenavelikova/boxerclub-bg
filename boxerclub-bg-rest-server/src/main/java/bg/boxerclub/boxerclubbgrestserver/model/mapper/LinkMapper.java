package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    LinkViewDto linkEntityToLinkViewDto(LinkEntity linkEntity);

    LinkEntity linkDtoToLinkEntity(LinkDto addLinkDto);
}
