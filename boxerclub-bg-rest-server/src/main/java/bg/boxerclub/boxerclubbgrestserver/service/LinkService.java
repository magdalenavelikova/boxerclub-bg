package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.link.AddLinkDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.LinkMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;

    public LinkService(LinkRepository linkRepository, LinkMapper linkMapper) {
        this.linkRepository = linkRepository;
        this.linkMapper = linkMapper;
    }

    public List<LinkViewDto> getAll() {
        return linkRepository.findAll().stream()
                .map(linkMapper::linkEntityToLinkViewDto)
                .collect(Collectors.toList());
    }


    public LinkViewDto addLink(AddLinkDto linkDto) {
        LinkEntity linkEntity = linkMapper.linkDtoToLinkEntity(linkDto);
        linkEntity.setCreated(LocalDateTime.now());
        return linkMapper.linkEntityToLinkViewDto(linkRepository.save(linkEntity));
    }

    public void deleteLink(Long id) {
        linkRepository.deleteById(id);
    }
}
