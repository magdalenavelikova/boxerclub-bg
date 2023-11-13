package bg.boxerclub.boxerclubbgrestserver.service.dog;

import bg.boxerclub.boxerclubbgrestserver.event.OnChangeOwnershipCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.event.OnDogRegistrationCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.exception.DogNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.exception.DogNotUniqueException;
import bg.boxerclub.boxerclubbgrestserver.exception.ParentYoungerThanChildException;
import bg.boxerclub.boxerclubbgrestserver.exception.UserNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Color;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Sex;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.DogMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import bg.boxerclub.boxerclubbgrestserver.service.CloudinaryService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DogService {
    private final DogRepository dogRepository;
    private final UserRepository userRepository;
    private final DogMapper dogMapper;
    private final PedigreeFileService pedigreeFileService;
    private final CloudinaryService cloudinaryService;
    private final ApplicationEventPublisher eventPublisher;


    public DogService(DogRepository dogRepository, UserRepository userRepository, DogMapper dogMapper, PedigreeFileService pedigreeFileService, CloudinaryService cloudinaryService, ApplicationEventPublisher eventPublisher) {
        this.dogRepository = dogRepository;
        this.userRepository = userRepository;
        this.dogMapper = dogMapper;
        this.pedigreeFileService = pedigreeFileService;
        this.cloudinaryService = cloudinaryService;
        this.eventPublisher = eventPublisher;
    }


    public List<DogViewDto> getAll() {
        return dogRepository.findAll().stream().map(dogMapper::dogEntityToDogViewDto).collect(Collectors.toList());

    }

    public List<DogViewDto> getAllApproved() {
        return dogRepository.findAllByIsApprovedTrue().stream().map(dogMapper::dogEntityToDogViewDto).collect(Collectors.toList());
    }

    public SavedDogDto registerDog(MultipartFile file, MultipartFile pedigree, RegisterDogDto registerDogDto, BoxerClubUserDetails user, ServletWebRequest request) throws IOException {
        if (registerDogDto.getRegistrationNum().isEmpty() && (isAdminOrModerator(user) || isMember(user))) {
            UUID uuid = UUID.randomUUID();
            String uniqueID = uuid.toString();
            registerDogDto.setRegistrationNum("Newborn" + uniqueID);
        }
        if (isNewEntity(registerDogDto.getRegistrationNum())) {
            DogEntity dogEntity = dogMapper.dogRegisterDtoToDogEntity(registerDogDto);
            dogEntity.setApproved(isAdminOrModerator(user));
            dogEntity.setCreated(LocalDateTime.now());
            UserEntity owner = userRepository.findById(Long.valueOf(registerDogDto.getOwnerId())).orElseThrow(() -> new UserNotFoundException(Long.parseLong(registerDogDto.getOwnerId())));


            if (owner.getId().equals(user.getId()) && isAdminOrModerator(user)) {
                dogEntity.setOwner(null);
            }
            dogEntity.setPictureUrl(getPictureUrl(file));
            DogEntity saved = dogRepository.save(dogEntity);
            if (pedigree != null) {
                pedigreeFileService.upload(pedigree, saved.getId());
            }

            if (isMember(user)) {
                eventPublisher.publishEvent(new OnDogRegistrationCompleteEvent(this, saved.getRegistrationNum(), request.getLocale()));
            }
            return dogMapper.dogEntityToSavedDogDto(saved);
        } else {
            throw new DogNotUniqueException(registerDogDto.getRegistrationNum());
        }

    }


    public ParentDto registerParentDog(MultipartFile file, MultipartFile pedigree, ParentDto parentDto, BoxerClubUserDetails user) throws IOException {
        DogEntity child = dogRepository.findById(Long.valueOf(parentDto.getChildId())).orElseThrow(() -> new DogNotFoundException(Long.valueOf(parentDto.getChildId())));
        if (parentDto.getRegistrationNum().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uniqueID = uuid.toString();
            parentDto.setRegistrationNum("Parent" + uniqueID);
        }

        if (isNewEntity(parentDto.getRegistrationNum())) {
            DogEntity parent = new DogEntity();
            if (parentDto.getBirthday().isEmpty()) {
                mapper(parentDto, parent);
            } else {
                parent = dogMapper.parentDtoToDogEntity(parentDto);
            }
            isParentOlderThanChild(parent, child);
            parent.setApproved(isAdminOrModerator(user));
            parent.setCreated(LocalDateTime.now());
            parent.setPictureUrl(getPictureUrl(file));


            DogEntity saved = dogRepository.save(parent);
            if (pedigree != null) {
                pedigreeFileService.upload(pedigree, saved.getId());
            }

            setParentToChild(saved, child);
            dogRepository.save(child);
            return dogMapper.dogEntityToParentDto(saved);
        } else {

            throw new DogNotUniqueException(parentDto.getRegistrationNum());
        }
    }


    public ParentDto addParentDog(AddParentDto parentDto) throws IOException {

        DogEntity parent = dogRepository.findById(parentDto.getId()).orElseThrow(() -> new DogNotFoundException(parentDto.getId()));

        DogEntity child = dogRepository.findById(Long.valueOf(parentDto.getChildId())).orElseThrow(() -> new DogNotFoundException(parentDto.getChildId()));
        isParentOlderThanChild(parent, child);
        setParentToChild(parent, child);
        dogRepository.save(child);
        return dogMapper.dogEntityToParentDto(parent);
    }

    public DogEntity findByRegisterNum(String value) {
        return dogRepository.findDogEntityByRegistrationNum(value).orElse(null);
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

    public EditDogViewDto approveDogById(Long id) {
        DogEntity dog = dogRepository.findById(id).orElseThrow(() -> new DogNotFoundException(id));
        dog.setApproved(true);
        // DogEntity saved = dogRepository.save(dog);
        return dogMapper.dogEntityToEditViewDogDto(dogRepository.save(dog));
    }


    public DogViewDto editDog(MultipartFile file, MultipartFile pedigree, Long id, EditDogDto editDogDto, BoxerClubUserDetails user) {

        DogEntity edit = dogRepository.findById(id).orElseThrow(() -> new DogNotFoundException(id));

        Optional<DogEntity> dogRegisterNum = dogRepository.findDogEntityByRegistrationNum(editDogDto.getRegistrationNum());


        if (dogRegisterNum.isPresent() && !Objects.equals(edit.getId(), dogRegisterNum.get().getId())) {
            throw new DogNotUniqueException(editDogDto.getRegistrationNum());
        } else {
            DogEntity temp = dogMapper.editDogDtoToDogEntity(editDogDto);
            temp.setApproved(edit.getApproved());
            temp.setCreated(edit.getCreated());
            try {
                DogEntity father = findByRegisterNum(editDogDto.getFatherRegistrationNum());
                DogEntity mother = findByRegisterNum(editDogDto.getMotherRegistrationNum());
                UserEntity owner = userRepository.findByEmail(editDogDto.getOwnerEmail()).orElse(null);
                if (father != null) {
                    temp.setFather(father);
                } else {
                    temp.setFather(edit.getFather());
                }
                if (mother != null) {
                    temp.setMother(mother);
                } else {
                    temp.setMother(edit.getMother());
                }

                if (owner != null) {
                    temp.setOwner(owner);
                } else {
                    temp.setOwner(edit.getOwner());
                }

                String pictureUrl = getPictureUrl(file);
                if (!pictureUrl.isEmpty()) {
                    temp.setPictureUrl(pictureUrl);

                } else {
                    temp.setPictureUrl(edit.getPictureUrl());
                }
                if (pedigree != null) {
                    pedigreeFileService.upload(pedigree, temp.getId());
                }
                if (!temp.equals(edit) || pedigree != null) {
                    temp.setApproved(isAdminOrModerator(user));
                    temp.setModified(LocalDateTime.now());
                    return dogMapper.dogEntityToDogViewDto(dogRepository.save(temp));
                } else {
                    return dogMapper.dogEntityToDogViewDto(edit);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public DogDetailsDto dogDetails(Long id) {
        DogEntity dog = dogRepository.findById(id).orElseThrow(() -> new DogNotFoundException(id));
        DogDetailsDto dogDetailsDto = new DogDetailsDto();
        DogViewDto dogViewDto = dogMapper.dogEntityToDogViewDto(dog);
        dogViewDto.setHasPedigree(pedigreeFileService.findPedigreeByDogId(id));
        dogDetailsDto.setDog(dogViewDto);
        if (dog.getMother() != null) {
            dogDetailsDto.getParents().add(dogMapper.dogEntityToDogViewDto(dog.getMother()));
            List<DogViewDto> siblings = dogRepository.findAllByMotherId(dog.getMother().getId())
                    .stream()
                    .filter(sibling -> !Objects.equals(sibling.getId(), dog.getId()))
                    .map(dogMapper::dogEntityToDogViewDto).toList();
            siblings.forEach(s -> dogDetailsDto.getSiblings().add(s));

        }
        if (dog.getFather() != null) {
            dogDetailsDto.getParents().add(dogMapper.dogEntityToDogViewDto(dog.getFather()));
            List<DogViewDto> siblings = dogRepository.findAllByFatherId(dog.getFather().getId())
                    .stream()
                    .filter(sibling -> !Objects.equals(sibling.getId(), dog.getId()))
                    .map(dogMapper::dogEntityToDogViewDto)
                    .toList();
            siblings.forEach(s -> dogDetailsDto.getSiblings().add(s));
        }
        if (!dogDetailsDto.getSiblings().isEmpty()) {
            Set<Long> uniqueSiblingIds = new HashSet<>();
            List<DogViewDto> uniqueSiblings = dogDetailsDto.getSiblings().stream()
                    .filter(sibling -> uniqueSiblingIds.add(sibling.getId())).toList();
            dogDetailsDto.setSiblings(uniqueSiblings);
        }
        List<DogViewDto> descendants = dogRepository.findAllByMotherIdOrFatherId(dog.getId(), dog.getId()).stream()
                .map(dogMapper::dogEntityToDogViewDto).toList();
        descendants.forEach(d -> dogDetailsDto.getDescendants().add(d));

        return dogDetailsDto;
    }

    public void changeOwnerShip(DogDtoWithNewOwner dog, ServletWebRequest request) {
        DogEntity dogEntity = dogRepository.findDogEntityByRegistrationNum(dog.getRegistrationNum()).orElseThrow(() -> new DogNotFoundException(dog.getRegistrationNum()));
        UserEntity newOwner = userRepository.findById(Long.valueOf(dog.getNewOwnerId())).orElseThrow(() -> new ObjectNotFoundException(UserEntity.class, "User"));

        if (dogEntity.getOwner() == null) {
            dogEntity.setOwner(newOwner);
            dogRepository.save(dogEntity);

        } else {
            DogViewDto dogViewDto = dogMapper.dogEntityToDogViewDto(dogEntity);
            UserEntity currentOwner = userRepository.findById(Long.valueOf(dogViewDto.getOwnerId())).orElseThrow(() -> new ObjectNotFoundException(UserEntity.class, "User"));
            String requestURL = String.valueOf(request.getRequest().getRequestURL());
            String appUrl = requestURL.replace("8080", "3000");
            eventPublisher.publishEvent(new OnChangeOwnershipCompleteEvent(this, dogViewDto, currentOwner, newOwner, request.getLocale(), appUrl));
        }


    }

    public void confirmChangeOwnerShip(String registrationNum, String newOwnerId) {
        DogEntity dog = dogRepository.findDogEntityByRegistrationNum(registrationNum).orElseThrow(() -> new DogNotFoundException(registrationNum));

        UserEntity newOwner = userRepository.findById(Long.valueOf(newOwnerId)).orElseThrow(() -> new ObjectNotFoundException(UserEntity.class, "User"));
        dog.setOwner(newOwner);
        dogRepository.save(dog);
    }

    private static boolean isFemale(Sex sex) {
        return sex.equals(Sex.Female);
    }

    private boolean isNewEntity(String value) {
        return dogRepository.findDogEntityByRegistrationNum(value).isEmpty();
    }

    private static void setParentToChild(DogEntity saved, DogEntity child) {
        if (isFemale(saved.getSex())) {
            child.setMother(saved);
        } else {
            child.setFather(saved);
        }
    }

    private static void mapper(ParentDto parentDto, DogEntity dogEntity) {
        dogEntity.setName(parentDto.getName());
        dogEntity.setRegistrationNum(parentDto.getRegistrationNum());
        dogEntity.setMicroChip(parentDto.getMicroChip());
        dogEntity.setColor(Color.valueOf(parentDto.getColor()));
        dogEntity.setSex(Sex.valueOf(parentDto.getSex()));
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

    private static boolean isAdminOrModerator(BoxerClubUserDetails user) {
        return user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODERATOR"));
    }

    private static boolean isMember(BoxerClubUserDetails user) {
        return user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"));
    }

    private static void isParentOlderThanChild(DogEntity parent, DogEntity child) {
        if (parent.getBirthday() != null) {
            int years = Period.between(parent.getBirthday(), child.getBirthday()).getYears();

            if (parent.getBirthday().isAfter(child.getBirthday()) || years < 1) {
                throw new ParentYoungerThanChildException(parent.getRegistrationNum());
            }
            if (isFemale(parent.getSex()) && years < 2) {
                throw new ParentYoungerThanChildException(parent.getRegistrationNum());
            }
        }
    }
}
