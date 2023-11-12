package bg.boxerclub.boxerclubbgrestserver.util;

import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.EventEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserRoleEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Color;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Location;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Role;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Sex;
import bg.boxerclub.boxerclubbgrestserver.repository.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class TestDataUtils {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final DogRepository dogRepository;
    private final PedigreeFileRepository pedigreeFileRepository;
    private final EventRepository eventRepository;


    public TestDataUtils(UserRepository userRepository, UserRoleRepository userRoleRepository, DogRepository dogRepository, PedigreeFileRepository pedigreeFileRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.dogRepository = dogRepository;
        this.pedigreeFileRepository = pedigreeFileRepository;
        this.eventRepository = eventRepository;
    }


    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setRole(Role.ADMIN);
            UserRoleEntity moderatorRole = new UserRoleEntity().setRole(Role.MODERATOR);
            UserRoleEntity memberRole = new UserRoleEntity().setRole(Role.MEMBER);
            UserRoleEntity userRole = new UserRoleEntity().setRole(Role.USER);

            userRoleRepository.save(adminRole);
            userRoleRepository.save(moderatorRole);
            userRoleRepository.save(memberRole);
            userRoleRepository.save(userRole);
        }
    }

    public UserEntity createTestAdmin(String email) {

        initRoles();

        var admin = new UserEntity()
                .setEmail(email)
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setEnabled(true)
                .setPassword("123456")
                .setRoles(userRoleRepository.findByRole(Role.ADMIN).stream().toList());

        return userRepository.save(admin);
    }

    public UserEntity createTestMember(String email) {

        initRoles();
        var user = new UserEntity()
                .setEmail(email)
                .setFirstName("User")
                .setLastName("Userov")
                .setEnabled(true)
                .setPassword("123456")
                .setRoles(userRoleRepository.findByRole(Role.MEMBER).stream().toList());

        return userRepository.save(user);
    }

    public UserEntity createTestUser(String email) {

        initRoles();
        var user = new UserEntity()
                .setEmail(email)
                .setFirstName("User")
                .setLastName("Userov")
                .setEnabled(true)
                .setPassword("123456")
                .setRoles(userRoleRepository.findByRole(Role.USER).stream().toList());

        return userRepository.save(user);
    }


    public EventEntity createEvent(LocalDate date) {
        EventEntity passed = new EventEntity()
                .setTitle("Event")
                .setDescription("Description")
                .setUrlLink("URL Event")
                .setStartDate(LocalDate.of(2023, 10, 1))
                .setExpiryDate(date)
                .setLocation(Location.Bulgarian);

        return eventRepository.save(passed);

    }

    public DogEntity createDog(String registrationNum) {
        DogEntity dog = new DogEntity()
                .setName("New dog")
                .setRegistrationNum(registrationNum)
                .setPictureUrl("")
                .setMicroChip("111")
                .setSex(Sex.Male)
                .setColor(Color.Brindle)
                .setBirthday(LocalDate.of(2023, 10, 10))
                .setApproved(true)
                .setHealthStatus("ok")
                .setKennel("NA")
                .setOwner(userRepository.findById(1L).get());
        return dogRepository.save(dog);
    }


    public void cleanUpDatabase() {
        pedigreeFileRepository.deleteAll();
        eventRepository.deleteAll();
        dogRepository.deleteAll();
    }

}
