package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactViewDto;
import bg.boxerclub.boxerclubbgrestserver.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.rmi.NoSuchObjectException;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping()
    public ResponseEntity<List<ContactViewDto>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') ")
    public ResponseEntity<ContactViewDto> addContact(@RequestBody @Valid ContactDto addContact,
                                                     @AuthenticationPrincipal BoxerClubUserDetails user) {
        return
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(contactService.addContact(addContact));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteContact(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        contactService.deleteContact(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<ContactViewDto> editContact(@RequestBody @Valid ContactDto contactDto, @PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) throws NoSuchObjectException {
        return ResponseEntity.ok()
                .body(contactService.editContact(id, contactDto));


    }

}
