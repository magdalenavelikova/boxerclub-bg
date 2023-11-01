package bg.boxerclub.boxerclubbgrestserver.service.dog;

import bg.boxerclub.boxerclubbgrestserver.event.OnChangeOwnershipCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.exeption.DogNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.exeption.DogNotUniqueException;
import bg.boxerclub.boxerclubbgrestserver.exeption.ParentYoungerThanChildException;
import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.DogMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import bg.boxerclub.boxerclubbgrestserver.service.CloudinaryService;
import bg.boxerclub.boxerclubbgrestserver.service.DifferenceService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Locale;
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
    private final ApplicationEventPublisher eventPublisher;


    public DogService(DogRepository dogRepository, UserRepository userRepository, DogMapper dogMapper, PedigreeFileService pedigreeFileService, DifferenceService differenceService, CloudinaryService cloudinaryService, ApplicationEventPublisher eventPublisher) {
        this.dogRepository = dogRepository;
        this.userRepository = userRepository;
        this.dogMapper = dogMapper;
        this.pedigreeFileService = pedigreeFileService;
        this.differenceService = differenceService;
        this.cloudinaryService = cloudinaryService;
        this.eventPublisher = eventPublisher;
    }


    public List<DogViewDto> getAll() {
        return dogRepository.findAll().stream()
                .map(dogMapper::dogEntityToDogViewDto)
                .collect(Collectors.toList());

    }

    public List<DogViewDto> getAllApproved() {
        return dogRepository.findAllByIsApprovedTrueAndOwnerNotNull().stream()
                .map(dogMapper::dogEntityToDogViewDto)
                .collect(Collectors.toList());
    }

    public SavedDogDto registerDog(MultipartFile file,
                                   MultipartFile pedigree,
                                   RegisterDogDto registerDogDto,
                                   BoxerClubUserDetails user) throws IOException {
        if (registerDogDto.getRegistrationNum().isEmpty()) {
            long id = dogRepository.findFirstByOrderByIdDesc().getId() + 1L;
            registerDogDto.setRegistrationNum("NewBorn" + id);
        }
        if (isNewEntity(registerDogDto.getRegistrationNum())) {
            DogEntity dogEntity = dogMapper.dogRegisterDtoToDogEntity(registerDogDto);
            dogEntity.setApproved(isAdminOrModerator(user));
            dogEntity.setCreated(LocalDateTime.now());

            if (isRoleMember(user)) {
                dogEntity.setOwner(userRepository.findById(Long.parseLong(registerDogDto.getOwnerId()))
                        .orElseThrow(() -> new NoSuchObjectException("No such user!")));
            }
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


    public ParentDto registerParentDog(MultipartFile file,
                                       MultipartFile pedigree,
                                       ParentDto parentDto,
                                       BoxerClubUserDetails user) throws IOException {
        DogEntity child = dogRepository.findById(Long.valueOf(parentDto.getChildId()))
                .orElseThrow(() -> new DogNotFoundException(Long.valueOf(parentDto.getChildId())));
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

        DogEntity parent = dogRepository.findById(Long.valueOf(parentDto.getId()))
                .orElseThrow(() -> new DogNotFoundException(parentDto.getId()));

        DogEntity child = dogRepository.findById(Long.valueOf(parentDto.getChildId()))
                .orElseThrow(() -> new DogNotFoundException(parentDto.getChildId()));
        isParentOlderThanChild(parent, child);
        setParentToChild(parent, child);
        dogRepository.save(child);
        return dogMapper.dogEntityToParentDto(parent);
    }

    public DogEntity findByRegisterNum(String value) {
        return dogRepository.findDogEntityByRegistrationNum(value).orElse(null);
    }


    private static void isParentOlderThanChild(DogEntity parent, DogEntity child) {
        if (parent.getBirthday() != null) {
            int years = Period.between(child.getBirthday(), parent.getBirthday()).getYears();

            if (child.getBirthday().isAfter(parent.getBirthday()) || years < 1) {
                throw new ParentYoungerThanChildException(parent.getRegistrationNum());
            }
            if (isFemale(parent.getSex()) && years < 2) {
                throw new ParentYoungerThanChildException(parent.getRegistrationNum());
            }
        }
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

    private static boolean isAdminOrModerator(BoxerClubUserDetails user) {
        return user.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                || user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODERATOR"));
    }

    private static boolean isRoleMember(BoxerClubUserDetails user) {
        return user.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_MEMBER"));
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

    public DogViewDto editDog(MultipartFile file,
                              MultipartFile pedigree,
                              Long id,
                              EditDogDto editDogDto,
                              BoxerClubUserDetails user) {

        DogEntity edit = dogRepository.findById(id)
                .orElseThrow(() -> new DogNotFoundException(id));

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
                    temp.setApproved(
                            isAdminOrModerator(user));

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

    public DogDetailsDto dogDetails(Long id) {
        DogEntity dog = dogRepository.findById(id).orElseThrow(() -> new DogNotFoundException(id));
        DogDetailsDto dogDetailsDto = new DogDetailsDto();
        DogViewDto dogViewDto = dogMapper.dogEntityToDogViewDto(dog);
        dogViewDto.setHasPedigree(pedigreeFileService.findPedigreeByDogId(id));
        dogDetailsDto.setDog(dogViewDto);
        if (dog.getMother() != null) {
            dogDetailsDto.getParents().add(dogMapper.dogEntityToDogViewDto(dog.getMother()));
        }
        if (dog.getFather() != null) {
            dogDetailsDto.getParents().add(dogMapper.dogEntityToDogViewDto(dog.getFather()));
        }

        return dogDetailsDto;
    }

    public void changeOwnerShip(DogDtoWithNewOwner dog, Locale locale) {
        DogViewDto dogViewDto = dogMapper.dogEntityToDogViewDto(dogRepository.findDogEntityByRegistrationNum(dog.getRegistrationNum())
                .orElseThrow(() -> new DogNotFoundException(dog.getRegistrationNum())));
        UserEntity currentOwner = userRepository.findById(Long.valueOf(dogViewDto.getOwnerId()))
                .orElseThrow(() -> new ObjectNotFoundException(UserEntity.class, "User"));
        UserEntity newOwner = userRepository.findById(Long.valueOf(dog.getNewOwnerId()))
                .orElseThrow(() -> new ObjectNotFoundException(UserEntity.class, "User"));
        eventPublisher.publishEvent(new OnChangeOwnershipCompleteEvent(dogViewDto, currentOwner, newOwner, locale));

    }

    public void confirmChangeOwnerShip(String registrationNum, String newOwnerId) {
        DogEntity dog = dogRepository.findDogEntityByRegistrationNum(registrationNum)
                .orElseThrow(() -> new DogNotFoundException(registrationNum));

        UserEntity newOwner = userRepository.findById(Long.valueOf(newOwnerId))
                .orElseThrow(() -> new ObjectNotFoundException(UserEntity.class, "User"));
        dog.setOwner(newOwner);
        dogRepository.save(dog);
    }

    private static boolean isFemale(String sex) {
        return sex.equals("Женски") || sex.equals("Female");
    }


}
