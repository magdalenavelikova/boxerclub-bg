package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.DogRegisterDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.ParentDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.DogMapper;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import org.springframework.stereotype.Service;

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


    public DogEntity registerDog(DogRegisterDto dogRegisterDto, BoxerClubUserDetails user) {
        // String pictureUrl = cloudinaryService.uploadImage(file);
        UserEntity userEntity = userMapper.boxerClubUserDetailsToUserEntity(user);
        DogEntity dogEntity = dogMapper.dogRegisterDtoToDogEntity(dogRegisterDto);
        dogEntity.setApproved(false);
        dogEntity.setCreated(LocalDateTime.now());
        if (dogRegisterDto.getOwnerId().equals(String.valueOf(user.getId()))) {
            dogEntity.setOwner(userEntity);
        } else {
            dogEntity.setOwner(userRepository.findById(Long.parseLong(dogRegisterDto.getOwnerId()))
                    .orElseThrow());
        }

        // dogEntity.setPictureUrl(pictureUrl);
        return dogRepository.save(dogEntity);

    }

    public List<ParentDto> getAll() {
        return dogRepository.findAll().stream()
                .map(dogMapper::dogEntityToParentDto)
                .collect(Collectors.toList());
    }
}
