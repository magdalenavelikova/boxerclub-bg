package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.LinkDto;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.LinkMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.LinkRepository;
import org.springframework.stereotype.Service;

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

    public List<LinkDto> getAll() {
        return linkRepository.findAll().stream()
                .map(linkMapper::linkEntityToLinkDto)
                .collect(Collectors.toList());
    }
}
