package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactViewDto;
import bg.boxerclub.boxerclubbgrestserver.service.ContactService;
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
@RequestMapping("/contacts")
@Tag(name = "Contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Operation(summary = "Get all contacts")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "View all contacts")}
    )
    @GetMapping()
    public ResponseEntity<List<ContactViewDto>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAll());
    }

    @Operation(summary = "Add Contact", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Contact was created.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ContactDto.class))}),
                    @ApiResponse(responseCode = "409", description = "Some fields were incorrect."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR.")
            }
    )
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') ")
    public ResponseEntity<ContactViewDto> addContact(@RequestBody @Valid ContactDto addContact,
                                                     @AuthenticationPrincipal BoxerClubUserDetails user) {
        return
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(contactService.addContact(addContact));
    }

    @Operation(summary = "Delete Contact", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Contact was deleted."),
                    @ApiResponse(responseCode = "404", description = "Contact was not found."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR.")
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteContact(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        contactService.deleteContact(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Edit Contact", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Contact was edited.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ContactDto.class))}),
                    @ApiResponse(responseCode = "404", description = "Contact was not found."),
                    @ApiResponse(responseCode = "409", description = "Some fields were incorrect."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR.")
            }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<ContactViewDto> editContact(@RequestBody @Valid ContactDto contactDto, @PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        return ResponseEntity.ok()
                .body(contactService.editContact(id, contactDto));


    }

}
