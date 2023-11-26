package bg.boxerclub.boxerclubbgrestserver.services;


import bg.boxerclub.boxerclubbgrestserver.exception.DogNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.exception.DogNotUniqueException;
import bg.boxerclub.boxerclubbgrestserver.exception.ParentYoungerThanChildException;
import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserRoleEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Color;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Role;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Sex;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.DogMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import bg.boxerclub.boxerclubbgrestserver.service.impl.CloudinaryServiceImpl;
import bg.boxerclub.boxerclubbgrestserver.service.impl.dog.DogServiceImpl;
import bg.boxerclub.boxerclubbgrestserver.service.impl.dog.PedigreeFileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DogServiceTest {
    @Mock
    private DogRepository mockDogRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private DogMapper mockDogMapper;
    @Mock
    private PedigreeFileServiceImpl mockPedigreeFileService;
    @Mock
    private CloudinaryServiceImpl mockCloudinaryService;
    @Mock
    private ApplicationEventPublisher mockEventPublisher;
    @Mock
    private ServletWebRequest mockRequest;


    private BoxerClubUserDetails testUserDetailsAdmin;
    private BoxerClubUserDetails testUserDetailsMember;
    private RegisterDogDto testRegisterDogDto;
    private ParentDto testParentDto;
    private AddParentDto testAddParentDto;
    private EditDogDto testEditDogDto;
    private DogViewDto testDogViewDto;
    private DogServiceImpl toTest;
    private MultipartFile testFile;
    private DogEntity testDogEntity;
    private UserEntity testUserEntityAdmin;
    private UserEntity testUserEntityMember;
    private UserRoleEntity testUserRoleEntityAdmin;
    private UserRoleEntity testUserRoleEntityMember;

    @Captor
    private ArgumentCaptor<DogEntity> dogEntityArgumentCaptor;


    public DogServiceTest() {
    }

    @BeforeEach
    void setUp() {

        toTest = new DogServiceImpl(mockDogRepository, mockUserRepository, mockDogMapper, mockPedigreeFileService, mockCloudinaryService, mockEventPublisher);
        Collection<GrantedAuthority> authoritiesAdmin = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        testUserDetailsAdmin = new BoxerClubUserDetails(1L, "newUser@example.com", "456123", "Maggie", "Velikova", true, authoritiesAdmin);
        Collection<GrantedAuthority> authoritiesMember = List.of(new SimpleGrantedAuthority("ROLE_MEMBER"));
        testUserDetailsMember = new BoxerClubUserDetails(2L, "newUser@example.com", "456123", "Maggie", "Velikova", true, authoritiesMember);
        testUserRoleEntityAdmin = new UserRoleEntity() {{
            setRole(Role.ADMIN);
        }};
        testUserRoleEntityMember = new UserRoleEntity() {{
            setRole(Role.MEMBER);
        }};
        testUserEntityAdmin = new UserEntity() {
            {
                setId(1L);
                setEmail("newUser@example.com");
                setPassword("456123");
                setFirstName("Maggie");
                setLastName("Velikova");
                setRoles(List.of(testUserRoleEntityAdmin));

            }
        };
        testUserEntityMember = new UserEntity() {
            {
                setId(2L);
                setEmail("newUser@example.com");
                setPassword("456123");
                setFirstName("Maggie");
                setLastName("Velikova");
                setRoles(List.of(testUserRoleEntityMember));

            }
        };
        testDogEntity = new DogEntity() {
            {
                setId(1L);
                setName("New");
                setRegistrationNum("111");
                setPictureUrl("");
                setMicroChip("111");
                setSex(Sex.Male);
                setColor(Color.Brindle);
                setBirthday(LocalDate.of(2023, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");
            }
        };


        testRegisterDogDto = new RegisterDogDto() {
            {
                setName("New");
                setRegistrationNum("111");
                setMicroChip("111");
                setSex(Sex.Male.toString());
                setColor(Color.Brindle.toString());
                setBirthday(LocalDate.of(2023, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");

            }

        };
        testEditDogDto = new EditDogDto() {{
            setId(1L);

            setName("Dog");
            setRegistrationNum("111");
            setMicroChip("111");
            setSex(Sex.Male.toString());
            setColor(Color.Brindle.toString());
            setBirthday(LocalDate.of(2023, 10, 10));
            setHealthStatus("ok");
            setKennel("NA");
        }};
        testDogViewDto = new DogViewDto() {
            {
                setId(1L);
                setName("New");
                setRegistrationNum("111");
                setMicroChip("111");
                setSex(Sex.Male.toString());
                setColor(Color.Brindle.toString());
                setBirthday(String.valueOf(LocalDate.of(2023, 10, 10)));
                setHealthStatus("ok");
                setKennel("NA");
            }
        };
        testFile = null;
//        mockRequest = new ServletWebRequest(mockServletRequest);
//
//        when(mockRequest.getRequest().getLocale()).thenReturn(new Locale("EN"));

        // when(mockEventPublisher.publishEvent(mockOnDogRegistrationComplete))
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(testUserEntityAdmin));
        when(mockUserRepository.findById(2L)).thenReturn(Optional.of(testUserEntityMember));
        when(mockDogRepository.findAll()).thenReturn(List.of(testDogEntity));
        when(mockDogRepository.findById(1L)).thenReturn(Optional.of(testDogEntity));
        when(mockDogRepository.findById(2L)).thenReturn(Optional.empty());
        when(mockDogMapper.dogEntityToDogViewDto(testDogEntity)).thenReturn(testDogViewDto);
        when(mockDogMapper.dogRegisterDtoToDogEntity(testRegisterDogDto)).thenReturn(testDogEntity);


    }

    @Test
    void testRegisterDog_SaveInvoked() throws IOException {
        testRegisterDogDto.setOwnerId("2");
        toTest.registerDog(testFile, testFile, testRegisterDogDto, testUserDetailsAdmin, mockRequest);
        verify(mockDogRepository).save(any());
    }

    @Test
    void testRegisterDog_withCaptorWhenUserIsAdmin() throws IOException {
        testRegisterDogDto.setOwnerId("1");
        when(mockDogMapper.dogRegisterDtoToDogEntity(testRegisterDogDto)).thenReturn(testDogEntity.setOwner(testUserEntityAdmin));
        toTest.registerDog(testFile, testFile, testRegisterDogDto, testUserDetailsAdmin, mockRequest);
        verify(mockDogRepository).save(dogEntityArgumentCaptor.capture());
        DogEntity savedDogEntity = dogEntityArgumentCaptor.getValue();
        assertEquals(testRegisterDogDto.getName(), savedDogEntity.getName());
        assertEquals(testRegisterDogDto.getColor(), savedDogEntity.getColor().toString());
        assertEquals(testRegisterDogDto.getSex(), savedDogEntity.getSex().toString());
        assertEquals(testRegisterDogDto.getKennel(), savedDogEntity.getKennel());
        assertNull(savedDogEntity.getOwner());

    }

    @Test
    void testRegisterDog_withCaptorWhenUserIsMember() throws IOException {
        testRegisterDogDto.setOwnerId("2");
        when(mockDogMapper.dogRegisterDtoToDogEntity(testRegisterDogDto)).thenReturn(testDogEntity.setOwner(testUserEntityMember));
        when(mockDogRepository.save(testDogEntity)).thenReturn(testDogEntity);
        toTest.registerDog(testFile, testFile, testRegisterDogDto, testUserDetailsMember, mockRequest);
        verify(mockDogRepository).save(dogEntityArgumentCaptor.capture());
        DogEntity savedDogEntity = dogEntityArgumentCaptor.getValue();
        assertEquals(testRegisterDogDto.getName(), savedDogEntity.getName());
        assertEquals(testRegisterDogDto.getColor(), savedDogEntity.getColor().toString());
        assertEquals(testRegisterDogDto.getSex(), savedDogEntity.getSex().toString());
        assertEquals(testRegisterDogDto.getKennel(), savedDogEntity.getKennel());
        assertEquals(testUserDetailsMember.getId(), savedDogEntity.getOwner().getId());
        assertFalse(savedDogEntity.getApproved());

    }

    @Test
    void testRegisterDog_withCaptorWithoutRegistrationNum() throws IOException {
        testRegisterDogDto.setOwnerId("2");
        testRegisterDogDto.setRegistrationNum("");
        when(mockDogMapper.dogRegisterDtoToDogEntity(testRegisterDogDto)).thenReturn(testDogEntity.setOwner(testUserEntityMember).setRegistrationNum("Newborn"));
        when(mockDogRepository.save(testDogEntity)).thenReturn(testDogEntity);
        toTest.registerDog(testFile, testFile, testRegisterDogDto, testUserDetailsMember, mockRequest);
        verify(mockDogRepository).save(dogEntityArgumentCaptor.capture());
        DogEntity savedDogEntity = dogEntityArgumentCaptor.getValue();
        assertEquals(testRegisterDogDto.getName(), savedDogEntity.getName());
        assertEquals(testRegisterDogDto.getColor(), savedDogEntity.getColor().toString());
        assertEquals(testRegisterDogDto.getSex(), savedDogEntity.getSex().toString());
        assertEquals(testRegisterDogDto.getKennel(), savedDogEntity.getKennel());
        assertEquals(testUserDetailsMember.getId(), savedDogEntity.getOwner().getId());
        assertFalse(savedDogEntity.getApproved());

    }

    @Test
    void testRegisterDog_WhenDogRegisterNumIsNotUnique() {
        testRegisterDogDto.setOwnerId("1");
        when(mockDogRepository.findDogEntityByRegistrationNum(testRegisterDogDto.getRegistrationNum())).thenReturn(Optional.of(testDogEntity));
        when(mockDogMapper.dogRegisterDtoToDogEntity(testRegisterDogDto)).thenReturn(testDogEntity.setOwner(testUserEntityMember));
        Exception exception = assertThrows(DogNotUniqueException.class, () -> toTest.registerDog(testFile, testFile, testRegisterDogDto, testUserDetailsMember, mockRequest));
        String expectedMessage = "There is already a registered dog with number " + testRegisterDogDto.getRegistrationNum() + "!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(mockDogRepository, times(1)).findDogEntityByRegistrationNum(any());
        verify(mockDogRepository, times(0)).save(any());
    }

    @Test
    void testGetAll() {
        List<DogViewDto> expected = List.of(testDogViewDto);
        assertEquals(expected, toTest.getAll());
    }

    @Test
    void testGetAllApproved() {
        when(mockDogRepository.findAllByIsApprovedTrue()).thenReturn(new ArrayList<>());
        assertTrue(toTest.getAllApproved().isEmpty());
    }

    @Test
    void testDeleteDogWhenExist() {
        toTest.deleteDog(1L);
        verify(mockDogRepository, times(1)).deleteById(1L);

    }

    @Test()
    void testDeleteDog_WhenNotExist() {
        Exception exception = assertThrows(DogNotFoundException.class, () -> toTest.deleteDog(2L));
        String expectedMessage = "Dog with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(mockDogRepository, times(0)).deleteById(2L);
    }

    @Test
    void testEditDog_WhenIsUpdated() {
        EditDogDto edited = testEditDogDto;
        edited.setName("Change");
        when(mockDogRepository.findById(edited.getId())).thenReturn(Optional.of(testDogEntity));
        DogEntity testTempDogEntity = new DogEntity() {{
            setId(1L);
            setName(edited.getName());
            setRegistrationNum("111");
            setPictureUrl("");
            setMicroChip("111");
            setSex(Sex.Male);
            setColor(Color.Brindle);
            setBirthday(LocalDate.of(2023, 10, 10));
            setHealthStatus("ok");
            setKennel("NA");
        }};
        when(mockDogMapper.editDogDtoToDogEntity(edited)).thenReturn(testTempDogEntity);

        toTest.editDog(testFile, testFile, edited.getId(), edited, testUserDetailsMember);

        verify(mockDogRepository).save(dogEntityArgumentCaptor.capture());
        DogEntity savedDogEntity = dogEntityArgumentCaptor.getValue();
        assertEquals(edited.getName(), savedDogEntity.getName());
        verify(mockDogRepository, times(1)).findById(testDogEntity.getId());
        verify(mockDogRepository, times(1)).findDogEntityByRegistrationNum(testDogEntity.getRegistrationNum());
        verify(mockDogRepository, times(1)).save(any());
    }

    @Test
    void testEditDog_WhenIsNotUpdated() {
        when(mockDogMapper.editDogDtoToDogEntity(testEditDogDto)).thenReturn(testDogEntity);
        toTest.editDog(testFile, testFile, testEditDogDto.getId(), testEditDogDto, testUserDetailsMember);
        verify(mockDogRepository, times(1)).findById(testDogEntity.getId());
        verify(mockDogRepository, times(1)).findDogEntityByRegistrationNum(testDogEntity.getRegistrationNum());
        verify(mockDogRepository, times(0)).save(testDogEntity);

    }

    @Test
    void testEditDog_WhenDogIsNotFound() {
        Exception exception = assertThrows(DogNotFoundException.class, () -> toTest.editDog(testFile, testFile, 2L, testEditDogDto, testUserDetailsMember));
        String expectedMessage = "Dog with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(mockDogRepository, times(1)).findById(2L);
        verify(mockDogRepository, times(0)).save(testDogEntity);
    }

    @Test
    void testEditDog_WhenDogRegisterNumIsNotUnique() {
        when(mockDogRepository.findDogEntityByRegistrationNum(testEditDogDto.getRegistrationNum())).thenReturn(Optional.of(testDogEntity));
        when(mockDogMapper.editDogDtoToDogEntity(testEditDogDto)).thenReturn(testDogEntity);
        Exception exception = assertThrows(DogNotUniqueException.class, () -> toTest.registerDog(testFile, testFile, testRegisterDogDto, testUserDetailsMember, mockRequest));
        String expectedMessage = "There is already a registered dog with number " + testRegisterDogDto.getRegistrationNum() + "!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(mockDogRepository, times(1)).findDogEntityByRegistrationNum(any());
        verify(mockDogRepository, times(0)).save(any());
    }

    @Test
    void testDogDetails() {
        DogDetailsDto dogDetailsDto = toTest.dogDetails(1L);
        assertEquals(1L, dogDetailsDto.getDog().getId());
        assertEquals(0, dogDetailsDto.getParents().size());
    }

    @Test
    void testDogDetails_WhenDogIsNotFound() {
        Exception exception = assertThrows(DogNotFoundException.class, () -> toTest.dogDetails(2L));
        String expectedMessage = "Dog with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testApproveDogById() {
        EditDogViewDto testEditViewDto = new EditDogViewDto() {{
            setId(1L);
            setName("New");
            setRegistrationNum("111");
            setApproved(true);
            setMicroChip("111");
            setSex("Male");
            setColor("Brindle");
            setBirthday("2023-10-10");
            setHealthStatus("ok");
            setKennel("NA");
        }};
        when(mockDogMapper.dogEntityToEditViewDogDto(testDogEntity)).thenReturn(testEditViewDto);
        toTest.approveDogById(1L);
        verify(mockDogRepository).save(dogEntityArgumentCaptor.capture());
        DogEntity savedDogEntity = dogEntityArgumentCaptor.getValue();
        assertEquals(1L, savedDogEntity.getId());
        assertTrue(savedDogEntity.getApproved());
    }

    @Test
    void testApproveDogById_WhenDogIsNotFound() {
        Exception exception = assertThrows(DogNotFoundException.class, () -> toTest.approveDogById(2L));
        String expectedMessage = "Dog with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testFindDogById() {
        EditDogViewDto testEditViewDto = new EditDogViewDto() {{
            setId(1L);
            setName("New");
            setRegistrationNum("111");
            setApproved(true);
            setMicroChip("111");
            setSex("Male");
            setColor("Brindle");
            setBirthday("2023-10-10");
            setHealthStatus("ok");
            setKennel("NA");
        }};
        when(mockDogMapper.dogEntityToEditViewDogDto(testDogEntity)).thenReturn(testEditViewDto);
        EditDogViewDto dogById = toTest.findDogById(1L);
        assertEquals(1L, dogById.getId());

    }

    @Test
    void testFindDogById_WhenDogIsNotFound() {
        Exception exception = assertThrows(DogNotFoundException.class, () -> toTest.approveDogById(2L));
        String expectedMessage = "Dog with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testRegisterParentDog_withCaptorWhenParentIsMale() throws IOException {
        testParentDto = new ParentDto() {{
            setName("New");
            setRegistrationNum("111");
            setPictureUrl("");
            setMicroChip("111");
            setSex("Male");
            setColor("Brindle");
            setBirthday("2019-10-10");
            setHealthStatus("ok");
            setKennel("NA");
            setChildId("1");

        }};

        DogEntity testParentDogEntity = new DogEntity() {
            {
                setId(4L);
                setName("New");
                setRegistrationNum("111");
                setPictureUrl("");
                setMicroChip("111");
                setSex(Sex.Male);
                setColor(Color.Brindle);
                setBirthday(LocalDate.of(2019, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");
            }
        };
        when(mockDogRepository.save(testParentDogEntity)).thenReturn(testParentDogEntity);
        when(mockDogMapper.parentDtoToDogEntity(testParentDto)).thenReturn(testParentDogEntity);
        toTest.registerParentDog(testFile, testFile, testParentDto, testUserDetailsAdmin);

        verify(mockDogRepository, times(2)).save(dogEntityArgumentCaptor.capture());
        List<DogEntity> dogs = dogEntityArgumentCaptor.getAllValues();
        assertEquals(testParentDto.getName(), dogs.get(0).getName());
        assertEquals(testParentDto.getColor(), dogs.get(0).getColor().toString());
        assertEquals(testParentDto.getSex(), dogs.get(0).getSex().toString());
        assertEquals(testParentDto.getKennel(), dogs.get(0).getKennel());
        assertNull(dogs.get(0).getOwner());
        assertEquals(dogs.get(1).getFather().getId(), dogs.get(0).getId());

    }

    @Test
    void testRegisterParentDog_withCaptorWhenParentIsFeMale() throws IOException {
        testParentDto = new ParentDto() {{
            setName("New");
            setRegistrationNum("111");
            setPictureUrl("");
            setMicroChip("111");
            setSex("Female");
            setColor("Brindle");
            setBirthday("2019-10-10");
            setHealthStatus("ok");
            setKennel("NA");
            setChildId("1");

        }};

        DogEntity testParentDogEntity = new DogEntity() {
            {
                setId(4L);
                setName("New");
                setRegistrationNum("111");
                setPictureUrl("");
                setMicroChip("111");
                setSex(Sex.Female);
                setColor(Color.Brindle);
                setBirthday(LocalDate.of(2019, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");
            }
        };
        when(mockDogRepository.save(testParentDogEntity)).thenReturn(testParentDogEntity);
        when(mockDogMapper.parentDtoToDogEntity(testParentDto)).thenReturn(testParentDogEntity);
        toTest.registerParentDog(testFile, testFile, testParentDto, testUserDetailsAdmin);

        verify(mockDogRepository, times(2)).save(dogEntityArgumentCaptor.capture());
        List<DogEntity> dogs = dogEntityArgumentCaptor.getAllValues();
        assertEquals(testParentDto.getName(), dogs.get(0).getName());
        assertEquals(testParentDto.getColor(), dogs.get(0).getColor().toString());
        assertEquals(testParentDto.getSex(), dogs.get(0).getSex().toString());
        assertEquals(testParentDto.getKennel(), dogs.get(0).getKennel());
        assertNull(dogs.get(0).getOwner());
        assertEquals(dogs.get(1).getMother().getId(), dogs.get(0).getId());

    }

    @Test
    void testRegisterParentDog_withCaptorWithoutRegistrationNum() throws IOException {
        testParentDto = new ParentDto() {{
            setName("New");
            setRegistrationNum("");
            setPictureUrl("");
            setMicroChip("111");
            setSex("Female");
            setColor("Brindle");
            setBirthday("2019-10-10");
            setHealthStatus("ok");
            setKennel("NA");
            setChildId("1");

        }};

        DogEntity testParentDogEntity = new DogEntity() {
            {
                setId(4L);
                setName("New");
                setRegistrationNum("Parent9879797");
                setPictureUrl("");
                setMicroChip("111");
                setSex(Sex.Female);
                setColor(Color.Brindle);
                setBirthday(LocalDate.of(2019, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");
            }
        };
        when(mockDogRepository.save(testParentDogEntity)).thenReturn(testParentDogEntity);
        when(mockDogMapper.parentDtoToDogEntity(testParentDto)).thenReturn(testParentDogEntity);
        toTest.registerParentDog(testFile, testFile, testParentDto, testUserDetailsAdmin);

        verify(mockDogRepository, times(2)).save(dogEntityArgumentCaptor.capture());
        List<DogEntity> dogs = dogEntityArgumentCaptor.getAllValues();
        assertTrue(dogs.get(0).getRegistrationNum().contains("Parent"));
        assertEquals(testParentDto.getColor(), dogs.get(0).getColor().toString());
        assertEquals(testParentDto.getSex(), dogs.get(0).getSex().toString());
        assertEquals(testParentDto.getKennel(), dogs.get(0).getKennel());
        assertNull(dogs.get(0).getOwner());
        assertEquals(dogs.get(1).getMother().getId(), dogs.get(0).getId());

    }


    @Test
    void testRegisterParentDog_WhenParentIsYoungerThenChild() {
        testParentDto = new ParentDto() {{
            setName("New");
            setRegistrationNum("111");
            setPictureUrl("");
            setMicroChip("111");
            setSex("Female");
            setColor("Brindle");
            setBirthday("2022-10-10");
            setHealthStatus("ok");
            setKennel("NA");
            setChildId("1");

        }};

        DogEntity testParentDogEntity = new DogEntity() {
            {
                setId(4L);
                setName("New");
                setRegistrationNum("111");
                setPictureUrl("");
                setMicroChip("111");
                setSex(Sex.Female);
                setColor(Color.Brindle);
                setBirthday(LocalDate.of(2022, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");
            }
        };


        when(mockDogRepository.save(testParentDogEntity)).thenReturn(testParentDogEntity);
        when(mockDogMapper.parentDtoToDogEntity(testParentDto)).thenReturn(testParentDogEntity);
        Exception exception = assertThrows(ParentYoungerThanChildException.class, () -> toTest.registerParentDog(testFile, testFile, testParentDto, testUserDetailsMember));
        String expectedMessage = "Parent with registration number " + testParentDogEntity.getRegistrationNum() + " is younger than child!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(mockDogRepository, times(1)).findDogEntityByRegistrationNum(any());
        verify(mockDogRepository, times(0)).save(any());

    }

    @Test
    void testRegisterParentDog_WhenParentRegisterNumIsNotUnique() {
        testParentDto = new ParentDto() {{
            setName("New");
            setRegistrationNum("111");
            setPictureUrl("");
            setMicroChip("111");
            setSex("Female");
            setColor("Brindle");
            setBirthday("2022-10-10");
            setHealthStatus("ok");
            setKennel("NA");
            setChildId("1");

        }};

        when(mockDogRepository.findDogEntityByRegistrationNum(testParentDto.getRegistrationNum())).thenReturn(Optional.of(testDogEntity));

        Exception exception = assertThrows(DogNotUniqueException.class, () -> toTest.registerParentDog(testFile, testFile, testParentDto, testUserDetailsMember));
        String expectedMessage = "There is already a registered dog with number " + testParentDto.getRegistrationNum() + "!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(mockDogRepository, times(1)).findDogEntityByRegistrationNum(any());
        verify(mockDogRepository, times(0)).save(any());
    }

    @Test
    void testAddParentDog_withCaptorWhenParentIsMale() throws IOException {
        testAddParentDto = new AddParentDto() {{
            setId(4L);
            setName("New");
            setSex("Male");
            setChildId("1");

        }};

        DogEntity testParentDogEntity = new DogEntity() {
            {
                setId(4L);
                setName("New");
                setRegistrationNum("111");
                setPictureUrl("");
                setMicroChip("111");
                setSex(Sex.Male);
                setColor(Color.Brindle);
                setBirthday(LocalDate.of(2019, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");
            }
        };
        when(mockDogRepository.findById(testParentDogEntity.getId())).thenReturn(Optional.of(testParentDogEntity));
        when(mockDogRepository.save(testParentDogEntity)).thenReturn(testParentDogEntity);
        when(mockDogMapper.parentDtoToDogEntity(testParentDto)).thenReturn(testParentDogEntity);
        toTest.addParentDog(testAddParentDto);
        verify(mockDogRepository, times(1)).save(dogEntityArgumentCaptor.capture());
        DogEntity child = dogEntityArgumentCaptor.getValue();
        assertEquals(child.getFather().getId(), testAddParentDto.getId());

    }

    @Test
    void testAddParentDog_withCaptorWhenParentIsFeMale() throws IOException {
        testAddParentDto = new AddParentDto() {{
            setId(4L);
            setName("New");
            setSex("Female");
            setChildId("1");

        }};

        DogEntity testParentDogEntity = new DogEntity() {
            {
                setId(4L);
                setName("New");
                setRegistrationNum("111");
                setPictureUrl("");
                setMicroChip("111");
                setSex(Sex.Female);
                setColor(Color.Brindle);
                setBirthday(LocalDate.of(2019, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");
            }
        };
        when(mockDogRepository.findById(testParentDogEntity.getId())).thenReturn(Optional.of(testParentDogEntity));
        when(mockDogRepository.save(testParentDogEntity)).thenReturn(testParentDogEntity);
        when(mockDogMapper.parentDtoToDogEntity(testParentDto)).thenReturn(testParentDogEntity);
        toTest.addParentDog(testAddParentDto);
        verify(mockDogRepository, times(1)).save(dogEntityArgumentCaptor.capture());
        DogEntity child = dogEntityArgumentCaptor.getValue();
        assertEquals(child.getMother().getId(), testAddParentDto.getId());

    }


    @Test
    void testAddParentDog_WhenParentIsYoungerThenChild() {
        testAddParentDto = new AddParentDto() {{
            setId(4L);
            setName("New");
            setSex("Female");
            setChildId("1");

        }};

        DogEntity testParentDogEntity = new DogEntity() {
            {
                setId(4L);
                setName("New");
                setRegistrationNum("111");
                setPictureUrl("");
                setMicroChip("111");
                setSex(Sex.Female);
                setColor(Color.Brindle);
                setBirthday(LocalDate.of(2022, 10, 10));
                setHealthStatus("ok");
                setKennel("NA");
            }
        };
        when(mockDogRepository.save(testParentDogEntity)).thenReturn(testParentDogEntity);
        when(mockDogMapper.parentDtoToDogEntity(testParentDto)).thenReturn(testParentDogEntity);
        when(mockDogRepository.findById(testParentDogEntity.getId())).thenReturn(Optional.of(testParentDogEntity));
        Exception exception = assertThrows(ParentYoungerThanChildException.class, () -> toTest.addParentDog(testAddParentDto));
        String expectedMessage = "Parent with registration number " + testParentDogEntity.getRegistrationNum() + " is younger than child!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(mockDogRepository, times(0)).save(any());


    }


}

