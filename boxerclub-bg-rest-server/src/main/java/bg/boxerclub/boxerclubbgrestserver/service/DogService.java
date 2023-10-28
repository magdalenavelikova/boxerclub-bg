package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.exeption.DogNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.exeption.DogNotUniqueException;
import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DogService {
    private final DogRepository dogRepository;
    private final UserRepository userRepository;
    private final DogMapper dogMapper;
    private final PedigreeFileService pedigreeFileService;
    private final DifferenceService differenceService;
    private final CloudinaryService cloudinaryService;


    public DogService(DogRepository dogRepository, UserRepository userRepository, DogMapper dogMapper, PedigreeFileService pedigreeFileService, DifferenceService differenceService, CloudinaryService cloudinaryService) {
        this.dogRepository = dogRepository;
        this.userRepository = userRepository;
        this.dogMapper = dogMapper;
        this.pedigreeFileService = pedigreeFileService;
        this.differenceService = differenceService;
        this.cloudinaryService = cloudinaryService;
    }

    //todo : Change sql request => only approved dogs and ownerId!=null
    public List<DogViewDto> getAll() {
        return dogRepository.findAll().stream()
                .map(dogMapper::dogEntityToDogViewDto)
                .collect(Collectors.toList());
    }

    public SavedDogDto registerDog(MultipartFile file, MultipartFile pedigree, RegisterDogDto registerDogDto, BoxerClubUserDetails user) throws IOException {
        if (registerDogDto.getRegistrationNum().isEmpty()) {
            long id = dogRepository.findFirstByOrderByIdDesc().getId() + 1L;
            registerDogDto.setRegistrationNum("NewBorn" + id);
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


    public ParentDto registerParentDog(MultipartFile file, MultipartFile pedigree, ParentDto parentDto, BoxerClubUserDetails user) throws IOException {

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
            if (pedigree != null) {
                pedigreeFileService.upload(pedigree, saved.getId());
            }
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

    public DogEntity findByRegisterNum(String value) {
        return dogRepository.findDogEntityByRegistrationNum(value).orElse(null);
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


    public EditDogViewDto findDogById(Long id) {
        DogEntity dog = dogRepository.findById(id).orElseThrow(() -> new DogNotFoundException(id));
        return dogMapper.dogEntityToEditViewDogDto(dog);
    }

    public DogViewDto editDog(MultipartFile file, MultipartFile pedigree, Long id, EditDogDto editDogDto) throws NoSuchObjectException {
        DogEntity edit = dogRepository.findById(id)
                .orElseThrow(() -> new NoSuchObjectException("No such dog"));
        Optional<DogEntity> dogRegisterNum = dogRepository.findDogEntityByRegistrationNum(editDogDto.getRegistrationNum());


        if (dogRegisterNum.isPresent() && !Objects.equals(edit.getId(), dogRegisterNum.get().getId())) {
            throw new DogNotUniqueException(editDogDto.getRegistrationNum());
        } else {

            DogEntity temp = dogMapper.editDogDtoToDogEntity(editDogDto);

            try {
                List<String> difference = differenceService.getDifference(temp, edit);
                DogEntity father = findByRegisterNum(editDogDto.getFatherRegistrationNum());
                DogEntity mother = findByRegisterNum(editDogDto.getMotherRegistrationNum());
                UserEntity owner = userRepository.findByEmail(editDogDto.getOwnerEmail()).orElse(null);
                if (!father.getId().equals(edit.getFather().getId())) {
                    difference.add("fatherId: " + father.getId() + " -> " + edit.getFather().getId());
                }
                if (!mother.getId().equals(edit.getMother().getId())) {
                    difference.add("motherId: " + mother.getId() + " -> " + edit.getMother().getId());
                }
                if (!Objects.requireNonNull(owner).getId().equals(edit.getOwner().getId())) {
                    difference.add("ownerId: " + owner.getId() + " -> " + edit.getOwner().getId());
                }
                String pictureUrl = getPictureUrl(file);
                if (!pictureUrl.isEmpty()) {
                    temp.setPictureUrl(pictureUrl);
                    difference.add("pictureUrl: " + temp.getPictureUrl() + " -> " + edit.getPictureUrl());
                } else {
                    temp.setPictureUrl(edit.getPictureUrl());
                }
                if (!difference.isEmpty()) {
                    temp.setFather(father);
                    temp.setMother(mother);
                    temp.setOwner(owner);
                    //todo only if Admin
                    temp.setApproved(edit.getApproved());
                    temp.setCreated(edit.getCreated());
                    temp.setModified(LocalDateTime.now());

                    if (pedigree != null) {
                        pedigreeFileService.upload(pedigree, temp.getId());
                    }
                    return dogMapper.dogEntityToDogViewDto(dogRepository.save(temp));

                }

            } catch (IllegalAccessException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return dogMapper.dogEntityToDogViewDto(edit);
    }


//    private static List<String> getDifference(Object s1, Object s2) throws IllegalAccessException {
//        List<String> values = new ArrayList<>();
//        for (Field field : s1.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            Object value1 = field.get(s1);
//            Object value2 = field.get(s2);
//            if (value1 != null && value2 != null) {
//                if (!Objects.equals(value1.toString(), value2.toString())) {
//                    values.add(field.getName() + ": " + value1 + " -> " + value2);
//                }
//            }
//        }
//        return values;
//    }

    private static boolean isFemale(String sex) {
        return sex.equals("Женски") || sex.equals("Female");
    }
}
