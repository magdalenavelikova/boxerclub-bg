package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.ContactEntity;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.ContactMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    public List<ContactViewDto> getAll() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::contactEntityToContactViewDto)
                .toList();
    }

    public void init() {
        if (contactRepository.count() == 0) {
            ContactEntity president = new ContactEntity(
                    "Bozhidar Velikov",
                    "Божидар Великов",
                    "President",
                    "Президент",
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
                    "Vice President",
                    "Зам. председател",
                    "Bulgaria",
                    "България",
                    "Stara Zagora",
                    "Стара Загора",
                    "6000",
                    "93, Ivan Asen str.",
                    "ул. Цар Иван-Асен 93",
                    "maya@sample.com",
                    "+359899906111");


            ContactEntity breedsCouncilChairman = new ContactEntity(
                    "Merlina Radeva",
                    "Мерлина Радева",
                    "Breed's council Chairman",
                    "Отговорник развъдна дейност",
                    "Bulgaria",
                    "България",
                    "Sofia",
                    "София",
                    "1225",
                    "105 Parva Balgarska Armia str.",
                    "ул. Първа Българска армия 105",
                    "merlina@sample.com",
                    "+359887407508");

            contactRepository.save(president);
            contactRepository.save(vicePresident);
            contactRepository.save(breedsCouncilChairman);
        }
    }


}
