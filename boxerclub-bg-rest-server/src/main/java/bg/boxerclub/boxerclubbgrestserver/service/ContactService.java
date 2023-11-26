package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactViewDto;

import java.util.List;

public interface ContactService {
    List<ContactViewDto> getAll();

    ContactViewDto addContact(ContactDto addContact);

    void deleteContact(Long id);

    ContactViewDto editContact(Long id, ContactDto contactDto);

    void init();
}
