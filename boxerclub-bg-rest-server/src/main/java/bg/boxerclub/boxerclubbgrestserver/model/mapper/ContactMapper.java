package bg.boxerclub.boxerclubbgrestserver.model.mapper;

import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.ContactEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactViewDto contactEntityToContactViewDto(ContactEntity contact);
}
