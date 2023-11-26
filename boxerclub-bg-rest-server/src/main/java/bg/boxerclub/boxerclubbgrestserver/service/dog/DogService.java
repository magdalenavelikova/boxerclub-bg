package bg.boxerclub.boxerclubbgrestserver.service.dog;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DogService {
    List<DogViewDto> getAll();

    List<DogViewDto> getAllApproved();

    SavedDogDto registerDog(MultipartFile file, MultipartFile pedigree, RegisterDogDto registerDogDto, BoxerClubUserDetails user, ServletWebRequest request) throws IOException;

    ParentDto registerParentDog(MultipartFile file, MultipartFile pedigree, ParentDto parentDto, BoxerClubUserDetails user) throws IOException;

    ParentDto addParentDog(AddParentDto parentDto) throws IOException;

    DogEntity findByRegisterNum(String value);

    boolean deleteDog(Long id);

    EditDogViewDto findDogById(Long id);

    EditDogViewDto approveDogById(Long id);

    DogViewDto editDog(MultipartFile file, MultipartFile pedigree, Long id, EditDogDto editDogDto, BoxerClubUserDetails user);

    DogDetailsDto dogDetails(Long id);

    void changeOwnerShip(DogDtoWithNewOwner dog, ServletWebRequest request);

    void confirmChangeOwnerShip(String registrationNum, String newOwnerId);
}
