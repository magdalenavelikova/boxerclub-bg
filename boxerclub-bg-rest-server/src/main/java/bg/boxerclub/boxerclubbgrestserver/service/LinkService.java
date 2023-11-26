package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkViewDto;

import java.util.List;

public interface LinkService {
    List<LinkViewDto> getAll();

    LinkViewDto addLink(LinkDto linkDto);

    void deleteLink(Long id);

    LinkViewDto editLink(Long id, LinkDto linkDto);
}
