package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkViewDto;
import bg.boxerclub.boxerclubbgrestserver.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/links")
@Tag(name = "Links")
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Operation(summary = "Get all links")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "View all links")}
    )
    @GetMapping("")
    public ResponseEntity<List<LinkViewDto>> getAllLinks() {
        return
                ResponseEntity.ok(linkService.getAll());
    }

    @Operation(summary = "Add Link", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Link was created.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LinkDto.class))}),
                    @ApiResponse(responseCode = "409", description = "Some fields was incorrect."),
                    @ApiResponse(responseCode = "401", description = "User is not authorized with role ADMIN or MODERATOR.")
            }
    )
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') ")
    public ResponseEntity<LinkViewDto> addLink(@RequestBody @Valid LinkDto addLinkDto, @AuthenticationPrincipal BoxerClubUserDetails user) {
        return
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(linkService.addLink(addLinkDto));
    }

    @Operation(summary = "Delete Link", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Link was deleted."),
                    @ApiResponse(responseCode = "404", description = "Link was not found."),
                    @ApiResponse(responseCode = "401", description = "User is not authorized with role ADMIN or MODERATOR.")
            }
    )

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteLink(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        linkService.deleteLink(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Edit Link", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Link was edited.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LinkDto.class))}),
                    @ApiResponse(responseCode = "404", description = "Link was not found."),
                    @ApiResponse(responseCode = "409", description = "Some fields was incorrect."),
                    @ApiResponse(responseCode = "401", description = "User is not authorized with role ADMIN or MODERATOR.")
            }
    )

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<LinkViewDto> editLink(@RequestBody @Valid LinkDto linkDto, @PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        return ResponseEntity.ok()
                .body(linkService.editLink(id, linkDto));


    }
}
