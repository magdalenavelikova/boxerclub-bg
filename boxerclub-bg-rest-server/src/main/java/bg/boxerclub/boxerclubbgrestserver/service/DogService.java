package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.exeption.DogNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.exeption.DogNotUniqueException;
import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.*;
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
    private final PedigreeFileService pedigreeFileService;

    private final CloudinaryService cloudinaryService;

    public DogService(DogRepository dogRepository, UserRepository userRepository, DogMapper dogMapper, PedigreeFileService pedigreeFileService, CloudinaryService cloudinaryService) {
        this.dogRepository = dogRepository;
        this.userRepository = userRepository;
        this.dogMapper = dogMapper;
        this.pedigreeFileService = pedigreeFileService;
        this.cloudinaryService = cloudinaryService;
    }

    //todo : Change sql request => only approved dogs and ownerId!=null
    public List<DogDto> getAll() {
        return dogRepository.findAll().stream()
                .map(dogMapper::dogEntityToDogDto)
                .collect(Collectors.toList());
    }

    public SavedDogDto registerDog(MultipartFile file, MultipartFile pedigree, RegisterDogDto registerDogDto, BoxerClubUserDetails user) throws IOException {
        if (registerDogDto.getRegistrationNum().isEmpty()) {
            Long id = dogRepository.findFirstByOrderByIdDesc().getId() + 1L;
            registerDogDto.setRegistrationNum("nb" + String.valueOf(id));
        }
        if (isNewEntity(registerDogDto.getRegistrationNum())) {
            DogEntity dogEntity = dogMapper.dogRegisterDtoToDogEntity(registerDogDto);
            dogEntity.setApproved(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
            dogEntity.setCreated(LocalDateTime.now());
            dogEntity.setOwner(userRepository.findById(Long.parseLong(registerDogDto.getOwnerId())).orElseThrow());
            dogEntity.setPictureUrl(getPictureUrl(file));
            DogEntity saved = dogRepository.save(dogEntity);
            if (pedigree != null) {
                pedigreeFileService.upload(pedigree, saved.getId());
            }
            return dogMapper.dogEntityToSavedDogDto(saved);
        } else {
            throw new DogNotUniqueException(registerDogDto.getRegistrationNum());
        }

    }


    public ParentDto registerParentDog(MultipartFile file, ParentDto parentDto, BoxerClubUserDetails user) throws IOException {

        if (isNewEntity(parentDto.getRegistrationNum())) {
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

            if (isFemale(parentDto.getSex())) {
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


    public ParentDto addParentDog(AddParentDto parentDto) throws IOException {

        DogEntity parent = dogRepository.findById(Long.valueOf(parentDto.getId()))
                .orElseThrow(() -> new DogNotFoundException(parentDto.getId()));

        DogEntity child = dogRepository.findById(Long.valueOf(parentDto.getChildId()))
                .orElseThrow(() -> new DogNotFoundException(parentDto.getChildId()));

        if (isFemale(parentDto.getSex())) {
            child.setMother(parent);
        } else {
            child.setFather(parent);
        }
        dogRepository.save(child);
        return dogMapper.dogEntityToParentDto(parent);
    }

    public DogDto findByRegisterNum(String value) {
        return dogMapper.dogEntityToDogDto(dogRepository.findDogEntityByRegistrationNum(value).get());
    }


    public boolean isNewEntity(String value) {
        return dogRepository.findDogEntityByRegistrationNum(value).isEmpty();
    }

    private static void mapper(ParentDto parentDto, DogEntity dogEntity) {
        dogEntity.setName(parentDto.getName());
        dogEntity.setRegistrationNum(parentDto.getRegistrationNum());
        dogEntity.setMicroChip(parentDto.getMicroChip());
        dogEntity.setColor(parentDto.getColor());
        dogEntity.setSex(parentDto.getSex());
        dogEntity.setKennel(parentDto.getKennel());
        dogEntity.setHealthStatus(parentDto.getHealthStatus());
    }

    private String getPictureUrl(MultipartFile file) throws IOException {
        String pictureUrl = "";

        if (file != null) {
            pictureUrl = cloudinaryService.uploadImage(file);
        }
        return pictureUrl;
    }

    // todo what about if the dog is parent to someone
    public boolean deleteDog(Long id) {

        if (dogRepository.findById(id).isPresent()) {
            List<DogEntity> dogEntityByMotherIdOrFatherId = dogRepository.findAllByMotherIdOrFatherId(id, id);
            if (dogEntityByMotherIdOrFatherId.isEmpty()) {
                pedigreeFileService.deleteByDogId(id);
                dogRepository.deleteById(id);
                return true;
            }
            return false;
        } else {
            throw new DogNotFoundException(id);
        }


    }

    private static boolean isFemale(String sex) {
        return sex.equals("Женски") || sex.equals("Female");
    }

    public EditDogDto findDogById(Long id) {
        DogEntity dog = dogRepository.findById(id).orElseThrow(() -> new DogNotFoundException(id));
        return dogMapper.dogEntityToEditDogDto(dog);
    }
}
