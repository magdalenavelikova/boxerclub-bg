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
    private final DifferenceService differenceService;

    public LinkService(LinkRepository linkRepository, LinkMapper linkMapper, DifferenceService differenceService) {
        this.linkRepository = linkRepository;
        this.linkMapper = linkMapper;
        this.differenceService = differenceService;
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
        try {

            if (!differenceService.getDifference(temp, edit).isEmpty()) {
                return linkMapper.linkEntityToLinkViewDto(linkRepository.save(temp));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return linkMapper.linkEntityToLinkViewDto(edit);
    }


}
