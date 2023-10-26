package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactViewDto;
import bg.boxerclub.boxerclubbgrestserver.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactViewDto>> getAll() {
        return ResponseEntity.ok(contactService.getAll());
    }
}
