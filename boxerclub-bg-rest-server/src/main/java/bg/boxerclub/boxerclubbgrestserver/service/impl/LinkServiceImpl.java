package bg.boxerclub.boxerclubbgrestserver.service.impl;

import bg.boxerclub.boxerclubbgrestserver.exception.LinkNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.LinkMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.LinkRepository;
import bg.boxerclub.boxerclubbgrestserver.service.LinkService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;
    private final LinkMapper linkMapper;


    public LinkServiceImpl(LinkRepository linkRepository, LinkMapper linkMapper) {
        this.linkRepository = linkRepository;
        this.linkMapper = linkMapper;

    }

    @Override
    public List<LinkViewDto> getAll() {
        return linkRepository.findAll().stream()
                .map(linkMapper::linkEntityToLinkViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public LinkViewDto addLink(LinkDto linkDto) {
        LinkEntity linkEntity = linkMapper.linkDtoToLinkEntity(linkDto);
        linkEntity.setCreated(LocalDateTime.now());
        return linkMapper.linkEntityToLinkViewDto(linkRepository.save(linkEntity));
    }

    @Override
    public void deleteLink(Long id) {
        if (linkRepository.findById(id).isPresent()) {
            linkRepository.deleteById(id);
        } else {
            throw new LinkNotFoundException(id);
        }
    }

    @Override
    public LinkViewDto editLink(Long id, LinkDto linkDto) {
        LinkEntity edit = linkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(id));

        LinkEntity temp = linkMapper.linkDtoToLinkEntity(linkDto);
        if (!temp.equals(edit)) {
            return linkMapper.linkEntityToLinkViewDto(linkRepository.save(temp));
        }
        return linkMapper.linkEntityToLinkViewDto(edit);
    }


}
