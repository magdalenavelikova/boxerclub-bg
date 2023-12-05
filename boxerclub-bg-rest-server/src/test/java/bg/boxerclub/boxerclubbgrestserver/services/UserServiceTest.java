package bg.boxerclub.boxerclubbgrestserver.services;

import bg.boxerclub.boxerclubbgrestserver.exception.UserNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.exception.UserNotUniqueException;
import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.EditUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.RegisterUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserRoleEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.VerificationToken;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Role;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserMapper;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserRoleMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRoleRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.VerificationTokenRepository;
import bg.boxerclub.boxerclubbgrestserver.service.impl.user.AppUserDetailService;
import bg.boxerclub.boxerclubbgrestserver.service.impl.user.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserMapper mockUserMapper;
    @Mock
    private UserRoleMapper mockUserRoleMapper;
    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @Mock
    private VerificationTokenRepository mockTokenRepository;
    @Mock
    private ServletWebRequest mockRequest;
    @Mock
    private HttpServletRequest mockServletRequest;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private ApplicationEventPublisher mockEventPublisher;
    @Mock
    private UserDetailsService mockUserDetailsService;
    @Mock
    private AuthenticationManager mockAuthenticationManager;

    private RegisterUserDto testRegisterUserDto;
    private UserDto testUserDto;
    private EditUserDto testEditUserDto;
    private UserRoleEntity testUserRoleEntity;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;
    private UserServiceImpl toTest;

    private UserEntity testUserEntity;

    public UserServiceTest() {
    }

    @BeforeEach
    void setUp() {

        mockUserDetailsService = new AppUserDetailService(mockUserRepository);
        toTest = new UserServiceImpl(mockEventPublisher,
                mockUserRoleRepository,
                mockUserRepository,
                mockTokenRepository,
                mockUserMapper,
                mockUserRoleMapper,
                mockUserDetailsService,
                mockPasswordEncoder, mockAuthenticationManager);
        testUserEntity = new UserEntity() {
            {
                setId(1L);
                setEmail("newUser@example.com");
                setPassword("456123");
                setFirstName("Maggie");
                setLastName("Velikova");
                setRoles(new ArrayList<>());

            }

        };


        testRegisterUserDto = new RegisterUserDto() {
            {
                setEmail("newUser@example.com");
                setPassword("456123");
                setFirstName("Maggie");
                setLastName("Velikova");
            }

        };
        testUserDto = new UserDto() {
            {
                setId(1L);
                setEmail("newUser@example.com");
                setFirstName("Maggie");
                setLastName("Velikova");

            }

        };


        testEditUserDto = new EditUserDto() {{
            setId(1L);
            setEmail("newUser@example.com");
            setFirstName("Maggie");
            setLastName("Velikova");
            setRoles(new ArrayList<>());
        }};

        mockRequest = new ServletWebRequest(mockServletRequest);
        List<UserEntity> users = List.of(testUserEntity);

        testUserRoleEntity = new UserRoleEntity() {{
            setRole(Role.USER);
        }};


        when(mockUserRepository.findByEmail(testRegisterUserDto.getEmail())).thenReturn(Optional.of(testUserEntity));
        when(mockUserRepository.findByEmail(testEditUserDto.getEmail())).thenReturn(Optional.of(testUserEntity));
        when(mockUserRepository.findByEmail("nonExist@email.com")).thenReturn(Optional.empty());
        when(mockUserRepository.findAll()).thenReturn(users);
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(testUserEntity));
        when(mockUserRepository.findById(2L)).thenReturn(Optional.empty());
        when(mockPasswordEncoder.encode(testRegisterUserDto.getPassword())).thenReturn(testRegisterUserDto.getPassword());
        when(mockUserMapper.userRegisterDtoToUserEntity(testRegisterUserDto)).thenReturn(testUserEntity);
        when(mockUserMapper.userEntityToUserDto(testUserEntity)).thenReturn(testUserDto);
        when(mockUserRoleRepository.findByRole(Role.USER)).thenReturn(Optional.of(testUserRoleEntity));
        when(mockRequest.getRequest().getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/users/register"));

    }

    @Test
    void testRegister_SaveInvoked() {
        toTest.registerNewUserAccount(testRegisterUserDto, mockRequest);
        Mockito.verify(mockUserRepository).save(any());
    }

    @Test
    void testRegister_withCaptor() {
        List<Object> testRoles = new ArrayList<>();
        testRoles.add(mockUserRoleRepository.findByRole(Role.USER).orElseThrow(null));
        toTest.registerNewUserAccount(testRegisterUserDto, mockRequest);
        Mockito.verify(mockUserRepository).save(userEntityArgumentCaptor.capture());
        UserEntity savedUserEntity = userEntityArgumentCaptor.getValue();
        assertEquals(testRegisterUserDto.getEmail(), savedUserEntity.getEmail());
        assertEquals(testRegisterUserDto.getFirstName(), savedUserEntity.getFirstName());
        assertEquals(testRegisterUserDto.getLastName(), savedUserEntity.getLastName());
        assertEquals(testRegisterUserDto.getPassword(), savedUserEntity.getPassword());
        assertEquals(testRoles.get(0), savedUserEntity.getRoles().get(0));
    }

    @Test
    void login() {
        doReturn(Optional.of(testUserEntity)).when(mockUserRepository).findByEmail("member@member.com");

        BoxerClubUserDetails userDetails = toTest.authenticate("member@member.com");
        assertEquals(testRegisterUserDto.getEmail(), userDetails.getUsername());
        assertEquals(testRegisterUserDto.getFirstName(), userDetails.getFirstName());
        assertEquals(testRegisterUserDto.getLastName(), userDetails.getLastName());
        assertEquals(testRegisterUserDto.getPassword(), userDetails.getPassword());


    }

    @Test
    void testGetAllUsers() {
        List<UserDto> expected = List.of(testUserDto);
        assertEquals(expected, toTest.getAllUsers());
    }

    @Test
    void testDeleteUserWhenExist() {
        toTest.deleteUser(1L);
        Mockito.verify(mockUserRepository, times(1)).deleteById(1L);

    }

    @Test()
    void testDeleteUserWhenNotExist() {

        Exception exception = assertThrows(UserNotFoundException.class, () -> toTest.deleteUser(2L));
        String expectedMessage = "User with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Mockito.verify(mockUserRepository, times(0)).deleteById(2L);
    }

    @Test
    void testSaveRegisteredUser() {
        toTest.saveRegisteredUser(testUserDto);
        Mockito.verify(mockUserRepository).save(testUserEntity);
    }

    @Test
    void testSaveRegisteredUserWhenNotExist() {
        testUserDto.setId(2L);
        Exception exception = assertThrows(UserNotFoundException.class, () -> toTest.saveRegisteredUser(testUserDto));
        String expectedMessage = "User with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Mockito.verify(mockUserRepository, times(0)).save(testUserEntity);
    }

    @Test
    void testEditUserWhenIsUpdated() {
        EditUserDto edited = testEditUserDto;
        edited.setFirstName("New");
        when(mockUserRepository.findById(edited.getId())).thenReturn(Optional.of(testUserEntity));
        UserEntity testTempUserEntity = new UserEntity() {{
            setId(1L);
            setEmail("newUser@example.com");
            setPassword("456123");
            setFirstName(edited.getFirstName());
            setLastName("Velikova");
            setRoles(new ArrayList<>());
        }};

        when(mockUserMapper.userEditDtoToUserEntity(edited)).thenReturn(testTempUserEntity);
        toTest.editUser(edited, edited.getId());
        Mockito.verify(mockUserRepository, times(1)).findById(testUserEntity.getId());
        Mockito.verify(mockUserRepository, times(1)).findByEmail(testUserEntity.getEmail());
        Mockito.verify(mockUserRepository, times(1)).save(testTempUserEntity);
    }

    @Test
    void testEditUserWhenIsNotUpdated() {
        when(mockUserMapper.userEditDtoToUserEntity(testEditUserDto)).thenReturn(testUserEntity);
        toTest.editUser(testEditUserDto, testUserEntity.getId());
        Mockito.verify(mockUserRepository, times(1)).findById(testUserEntity.getId());
        Mockito.verify(mockUserRepository, times(1)).findByEmail(testUserEntity.getEmail());
        Mockito.verify(mockUserRepository, times(0)).save(testUserEntity);

    }

    @Test
    void testEditUserWhenEmailIsNotUnique() {
        EditUserDto edited = testEditUserDto;
        edited.setFirstName("New");
        UserEntity testUserEntityWithSameEmail = new UserEntity() {{
            setId(2L);
            setEmail("newUser@example.com");
            setPassword("456123");
            setFirstName("Maggie");
            setLastName("Velikova");
            setRoles(new ArrayList<>());
        }};
        when(mockUserRepository.findById(edited.getId())).thenReturn(Optional.of(testUserEntity));
        when(mockUserRepository.findByEmail(edited.getEmail())).thenReturn(Optional.of(testUserEntityWithSameEmail));
        UserEntity testTempUserEntity = new UserEntity() {{
            setId(1L);
            setEmail("newUser@example.com");
            setPassword("456123");
            setFirstName(edited.getFirstName());
            setLastName("Velikova");
            setRoles(new ArrayList<>());
        }};

        when(mockUserMapper.userEditDtoToUserEntity(edited)).thenReturn(testTempUserEntity);

        Exception exception = assertThrows(UserNotUniqueException.class, () -> toTest.editUser(edited, testTempUserEntity.getId()));
        String expectedMessage = "There is already a registered user with this email " + edited.getEmail() + "!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        Mockito.verify(mockUserRepository, times(1)).findById(testUserEntity.getId());
        Mockito.verify(mockUserRepository, times(1)).findByEmail(testUserEntity.getEmail());
        Mockito.verify(mockUserRepository, times(0)).save(testTempUserEntity);
    }

    @Test
    void testInitWhenRepoIsNotEmpty() {
        when(mockUserRepository.count()).thenReturn(1L);
        when(mockUserRoleRepository.count()).thenReturn(1L);
        toTest.init();
        Mockito.verify(mockUserRepository, times(0)).save(any());
        Mockito.verify(mockUserRoleRepository, times(0)).save(any());
    }

    @Test
    void testInitWhenRepoEmpty() {
        when(mockUserRepository.count()).thenReturn(0L);
        when(mockUserRoleRepository.count()).thenReturn(0L);
        toTest.init();
        Mockito.verify(mockUserRepository, times(2)).save(any());
        Mockito.verify(mockUserRoleRepository, times(4)).save(any());
    }

    @Test
    void testGetAllRoles() {
        when(mockUserRoleRepository.findAll()).thenReturn(List.of(testUserRoleEntity));
        assertEquals(1, toTest.getAllRoles().size());
    }

    @Test
    void testGetUserWhenFound() {
        EditUserDto testEditUserDto = new EditUserDto() {{
            setId(1L);
            setEmail("newUser@example.com");
            setFirstName("Maggie");
            setLastName("Velikova");
            setRoles(new ArrayList<>());
        }};
        toTest.getUser(1L);
        assertEquals(testEditUserDto.getEmail(), testUserEntity.getEmail());
        assertEquals(testEditUserDto.getFirstName(), testUserEntity.getFirstName());
        assertEquals(testEditUserDto.getLastName(), testUserEntity.getLastName());

    }

    @Test
    void testGetUserWhenIsNotExist() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> toTest.getUser(2L));
        String expectedMessage = "User with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testCreateVerificationTokenWhenUserFound() {
        toTest.createVerificationToken(testUserDto, "8789");
        Mockito.verify(mockTokenRepository, times(1)).save(any());
    }

    @Test
    void testCreateVerificationTokenWhenUserNotExist() {
        testUserDto.setId(2L);
        Exception exception = assertThrows(UserNotFoundException.class, () -> toTest.createVerificationToken(testUserDto, "8789"));
        String expectedMessage = "User with ID 2 not found!";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        Mockito.verify(mockTokenRepository, times(0)).save(any());
    }

    @Test
    void testGetVerificationToken() {
        VerificationToken testVerificationToken = new VerificationToken("7878", testUserEntity);
        when(mockTokenRepository.findByToken("7878")).thenReturn(testVerificationToken);
        toTest.getVerificationToken("7878");
        assertEquals("7878", testVerificationToken.getToken());
        assertEquals(testUserEntity.getId(), testVerificationToken.getUser().getId());
    }

    @Test
    void testGetUserByVerificationToken() {
        VerificationToken testVerificationToken = new VerificationToken("7878", testUserEntity);
        UserDto userByVerificationToken = toTest.getUserByVerificationToken(testVerificationToken);
        assertEquals(userByVerificationToken.getEmail(), testUserEntity.getEmail());
        assertEquals(userByVerificationToken.getFirstName(), testUserEntity.getFirstName());
        assertEquals(userByVerificationToken.getLastName(), testUserEntity.getLastName());

    }
}
