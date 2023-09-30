package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.exeption.DogNotUniqueException;
import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.DogDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.ParentDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.RegisterDogDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.SavedDogDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.DogMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogService {
    private final DogRepository dogRepository;
    private final UserRepository userRepository;
    private final DogMapper dogMapper;

    private final CloudinaryService cloudinaryService;

    public DogService(DogRepository dogRepository, UserRepository userRepository, DogMapper dogMapper, CloudinaryService cloudinaryService) {
        this.dogRepository = dogRepository;
        this.userRepository = userRepository;
        this.dogMapper = dogMapper;

        this.cloudinaryService = cloudinaryService;
    }

    //todo : Change sql request => only approved dogs and ownerId!=null
    public List<DogDto> getAll() {
        return dogRepository.findAll().stream()
                .map(dogMapper::dogEntityToDogDto)
                .collect(Collectors.toList());
    }

    public SavedDogDto registerDog(MultipartFile file, RegisterDogDto registerDogDto, BoxerClubUserDetails user) throws IOException {
        if (isValid(registerDogDto.getRegistrationNum())) {
            DogEntity dogEntity = dogMapper.dogRegisterDtoToDogEntity(registerDogDto);

            dogEntity.setApproved(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
            dogEntity.setCreated(LocalDateTime.now());
            dogEntity.setOwner(userRepository.findById(Long.parseLong(registerDogDto.getOwnerId())).orElseThrow());
            dogEntity.setPictureUrl(getPictureUrl(file));
            DogEntity saved = dogRepository.save(dogEntity);
            return dogMapper.dogEntityToSavedDogDto(saved);
        } else {
            throw new DogNotUniqueException(registerDogDto.getRegistrationNum());
        }

    }


    public ParentDto registerParentDog(MultipartFile file, ParentDto parentDto, BoxerClubUserDetails user) throws IOException {

        if (isValid(parentDto.getRegistrationNum())) {
            DogEntity dogEntity = new DogEntity();
            if (parentDto.getBirthday().isEmpty()) {
                mapper(parentDto, dogEntity);
            } else {
                dogEntity = dogMapper.parentDtoToDogEntity(parentDto);
            }

            dogEntity.setApproved(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
            dogEntity.setCreated(LocalDateTime.now());
            dogEntity.setPictureUrl(getPictureUrl(file));
            DogEntity saved = dogRepository.save(dogEntity);
            DogEntity child = dogRepository.findById(Long.valueOf(parentDto.getChildId()))
                    .orElseThrow(() -> new NoSuchObjectException("Child not found!"));
            String sex = parentDto.getSex();
            if ((sex.equals("Женски") || sex.equals("Female"))) {
                child.setMother(saved);
            } else {
                child.setFather(saved);
            }
            dogRepository.save(child);
            return dogMapper.dogEntityToParentDto(saved);
        } else {
            throw new DogNotUniqueException(parentDto.getRegistrationNum());
        }
    }

    public DogDto findByRegisterNum(String value) {
        return dogMapper.dogEntityToDogDto(dogRepository.findDogEntityByRegistrationNum(value).get());
    }


    public boolean isValid(String value) {
        return dogRepository.findDogEntityByRegistrationNum(value).isEmpty();
    }

    private static void mapper(ParentDto parentDto, DogEntity dogEntity) {
        dogEntity.setName(parentDto.getColor());
        dogEntity.setRegistrationNum(parentDto.getRegistrationNum());
        dogEntity.setMicroChip(parentDto.getMicroChip());
        dogEntity.setColor(parentDto.getColor());
        dogEntity.setSex(parentDto.getSex());
        dogEntity.setKennel(parentDto.getKennel());
        dogEntity.setHealthStatus(parentDto.getHealthStatusEntity());
    }

    private String getPictureUrl(MultipartFile file) throws IOException {
        String pictureUrl = "";

        if (!"empty.png".equals(file.getOriginalFilename())) {
            pictureUrl = cloudinaryService.uploadImage(file);
        }
        return pictureUrl;
    }
}
