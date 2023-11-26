package bg.boxerclub.boxerclubbgrestserver.service.dog;

import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public interface ChangeOwnershipDogMailService {
    void sendEmail(UserEntity currentOwner, UserEntity newOwner,
                   DogViewDto dog, Locale preferredLocale, String url


    ) throws UnsupportedEncodingException;
}
