package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.LinkMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.LinkRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
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


    public LinkViewDto addLink(LinkDto linkDto) {
        LinkEntity linkEntity = linkMapper.linkDtoToLinkEntity(linkDto);
        linkEntity.setCreated(LocalDateTime.now());
        return linkMapper.linkEntityToLinkViewDto(linkRepository.save(linkEntity));
    }

    public void deleteLink(Long id) {
        if (linkRepository.findById(id).isPresent()) {
            linkRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException(LinkEntity.class, "Link");
        }
    }

    public LinkViewDto editLink(Long id, LinkDto linkDto) throws NoSuchObjectException {
        LinkEntity edit = linkRepository.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("No such link"));

        LinkEntity temp = linkMapper.linkDtoToLinkEntity(linkDto);
        if (!temp.equals(edit)) {
            return linkMapper.linkEntityToLinkViewDto(linkRepository.save(temp));
        }
        return linkMapper.linkEntityToLinkViewDto(edit);
    }


}
