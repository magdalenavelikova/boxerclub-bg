package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.ContactEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Sex;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.ContactMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.ContactRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;


    public ContactService(ContactRepository contactRepository,
                          ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;

    }

    public List<ContactViewDto> getAll() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::contactEntityToContactViewDto)
                .toList();

    }

    public ContactViewDto addContact(ContactDto addContact) {
        ContactEntity contactEntity = contactMapper.contactDtoToContactEntity(addContact);
        return contactMapper.contactEntityToContactViewDto(contactRepository.save(contactEntity));

    }

    public void deleteContact(Long id) {
        if (contactRepository.findById(id).isPresent()) {
            contactRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException(ContactEntity.class, "Contact");
        }

    }

    public ContactViewDto editContact(Long id, ContactDto contactDto) throws NoSuchObjectException {
        ContactEntity edit = contactRepository.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("No such contact"));

        ContactEntity temp = contactMapper.contactDtoToContactEntity(contactDto);
        if (!temp.equals(edit)) {
            return contactMapper.contactEntityToContactViewDto(contactRepository.save(temp));
        }
        return contactMapper.contactEntityToContactViewDto(edit);
    }

    public void init() {
        if (contactRepository.count() == 0) {
            ContactEntity president = new ContactEntity(
                    "Bozhidar Velikov",
                    "Божидар Великов",
                    Sex.Male,
                    "President",
                    "Президент",
                    "http://res.cloudinary.com/dusaavzkc/image/upload/v1698394862/iwmekjihxzje1yrm7p1u.jpg",
                    "Bulgaria",
                    "България",
                    "Varna",
                    "Варна",
                    "9000",
                    "PO Box 88",
                    "Пощенска кутия 88",
                    "bozhidar.velikov@gmail.com",
                    "+35952342908");


            ContactEntity vicePresident = new ContactEntity(

                    "Maya Ileva",
                    "Майа Илева",
                    Sex.Female,
                    "Vice President",
                    "Зам. председател",
                    "http://res.cloudinary.com/dusaavzkc/image/upload/v1698394889/vuauzvsxnu57s4x9htpf.jpg",
                    "Bulgaria",
                    "България",
                    "Stara Zagora",
                    "Стара Загора",
                    "6000",
                    "93, Ivan Asen str.",
                    "ул. Цар Иван-Асен 93",
                    "maya.ileva@boxerclub-bg.org",
                    "+359899906111");


            ContactEntity breedsCouncilChairman = new ContactEntity(
                    "Merlina Radeva",
                    "Мерлина Радева",
                    Sex.Female,
                    "Breed's council Chairman",
                    "Отговорник развъдна дейност",
                    "http://res.cloudinary.com/dusaavzkc/image/upload/v1698394915/hov6cf0blsd5zslyepym.jpg",
                    "Bulgaria",
                    "България",
                    "Sofia",
                    "София",
                    "1225",
                    "105 Parva Balgarska Armia str.",
                    "ул. Първа Българска армия 105",
                    "merlina.radeva@boxerclub-bg.org",
                    "+359887407508");

            contactRepository.save(president);
            contactRepository.save(vicePresident);
            contactRepository.save(breedsCouncilChairman);
        }
    }


}
