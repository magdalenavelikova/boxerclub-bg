package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.dto.ParentDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.RegisterDogDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.SavedDogDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.DogMapper;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogService {
    private final DogRepository dogRepository;
    private final UserRepository userRepository;
    private final DogMapper dogMapper;
    private final UserMapper userMapper;
    private final CloudinaryService cloudinaryService;

    public DogService(DogRepository dogRepository, UserRepository userRepository, DogMapper dogMapper, UserMapper userMapper, CloudinaryService cloudinaryService) {
        this.dogRepository = dogRepository;
        this.userRepository = userRepository;
        this.dogMapper = dogMapper;
        this.userMapper = userMapper;
        this.cloudinaryService = cloudinaryService;
    }

    public List<ParentDto> getAll() {
        return dogRepository.findAll().stream()
                .map(dogMapper::dogEntityToParentDto)
                .collect(Collectors.toList());
    }

    public SavedDogDto registerDog(MultipartFile file, RegisterDogDto registerDogDto) throws IOException {

        String pictureUrl = cloudinaryService.uploadImage(file);

        // UserEntity userEntity = userMapper.boxerClubUserDetailsToUserEntity(user);
        DogEntity dogEntity = createDogEntity(registerDogDto);
        dogEntity.setPictureUrl(pictureUrl);
        DogEntity saved = dogRepository.save(dogEntity);
        return dogMapper.dogEntityToSavedDogDto(saved);

    }


    public SavedDogDto registerDogWithoutPicture(RegisterDogDto registerDogDto) {
        DogEntity dogEntity = createDogEntity(registerDogDto);
        DogEntity saved = dogRepository.save(dogEntity);
        return dogMapper.dogEntityToSavedDogDto(saved);
    }

    @NotNull
    private DogEntity createDogEntity(RegisterDogDto registerDogDto) {
        DogEntity dogEntity = dogMapper.dogRegisterDtoToDogEntity(registerDogDto);
        dogEntity.setApproved(false);
        dogEntity.setCreated(LocalDateTime.now());
        dogEntity.setOwner(userRepository.findById(Long.parseLong(registerDogDto.getOwnerId())).orElseThrow());
        return dogEntity;
    }
}
