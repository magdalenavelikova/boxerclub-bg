package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.AddLinkDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkViewDto;
import bg.boxerclub.boxerclubbgrestserver.service.LinkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/links")
public class LinkController {
    private final LinkService linkService;


    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public ResponseEntity<List<LinkViewDto>> getAll() {
        return
                ResponseEntity.ok(linkService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') ")
    public ResponseEntity<LinkViewDto> addLink(@RequestBody @Valid AddLinkDto addLinkDto, @AuthenticationPrincipal BoxerClubUserDetails user) {
        return
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(linkService.addLink(addLinkDto));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteLink(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        linkService.deleteLink(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();


    }
}
