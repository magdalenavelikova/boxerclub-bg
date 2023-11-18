package bg.boxerclub.boxerclubbgrestserver.service.user;

import bg.boxerclub.boxerclubbgrestserver.event.OnUserRegistrationCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.exception.UserNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.exception.UserNotUniqueException;
import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.EditUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.RegisterUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserRoleDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserRoleEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.VerificationToken;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Role;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserMapper;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserRoleMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRoleRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ApplicationEventPublisher eventPublisher;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.admin.password}")
    public String adminPass;

    public UserService(ApplicationEventPublisher eventPublisher,
                       UserRoleRepository userRoleRepository,
                       UserRepository userRepository,
                       VerificationTokenRepository tokenRepository,
                       UserMapper userMapper,
                       UserRoleMapper userRoleMapper,
                       UserDetailsService userDetailsService,
                       PasswordEncoder passwordEncoder) {
        this.eventPublisher = eventPublisher;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDto registerNewUserAccount(RegisterUserDto registerUserDto, ServletWebRequest request) {
        UserEntity userEntity = userMapper.userRegisterDtoToUserEntity(registerUserDto);
        String rowPassword = userEntity.getPassword();
        String password = passwordEncoder.encode(rowPassword);
        userEntity.setPassword(password);
        userEntity.setCreated(LocalDateTime.now());
        if (userRoleRepository.findByRole(Role.USER).isPresent()) {
            userEntity.addRole(userRoleRepository.findByRole(Role.USER).get());
        }
        UserDto userDto = userMapper.userEntityToUserDto(userRepository.save(userEntity));

        String requestURL = String.valueOf(request.getRequest().getRequestURL());
        String appUrl = requestURL.replace("8080", "3000");
        //for deploy
        //String appUrl = "https://boxer-club.web.app/users";
        eventPublisher.publishEvent(new OnUserRegistrationCompleteEvent(this, userDto,
                request.getLocale(), appUrl));
        return userDto;
    }

    public BoxerClubUserDetails login(String userName) {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(userName);

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);
        return (BoxerClubUserDetails) auth.getPrincipal();
    }


    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::userEntityToUserDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            tokenRepository.deleteByUserId(id);
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }

    }

    public UserDto getUserByVerificationToken(VerificationToken verificationToken) {
        return userMapper.userEntityToUserDto(verificationToken.getUser());
    }

    public List<UserRoleDto> getAllRoles() {
        return userRoleRepository.findAll()
                .stream()
                .map(userRoleMapper::userRoleEntityToUserRoLeDto)
                .collect(Collectors.toList());
    }

    public UserDto editUser(EditUserDto userEditDto) {
        UserEntity edit = userRepository.findById(userEditDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userEditDto.getId()));
        Optional<UserEntity> userEmail = userRepository.findByEmail(userEditDto.getEmail());


        if (userEmail.isPresent() && !Objects.equals(edit.getId(), userEmail.get().getId())) {
            throw new UserNotUniqueException(userEditDto.getEmail());
        } else {
            UserEntity temp = userMapper.userEditDtoToUserEntity(userEditDto);

            if (!temp.equals(edit)) {
                edit.setModified(LocalDateTime.now());
                return userMapper.userEntityToUserDto(userRepository.save(temp));
            }
            return userMapper.userEntityToUserDto(edit);
        }
    }

    public EditUserDto getUser(Long id) {
        return userMapper.userEntityToUserEditDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)

        ));
    }

    public void init() {
        if (userRoleRepository.count() == 0 && userRepository.count() == 0) {
            UserRoleEntity roleAdmin = new UserRoleEntity();
            roleAdmin.setRole(Role.ADMIN);
            roleAdmin.setCreated(LocalDateTime.now());
            userRoleRepository.save(roleAdmin);
            UserRoleEntity roleModerator = new UserRoleEntity();
            roleModerator.setRole(Role.MODERATOR);
            roleModerator.setCreated(LocalDateTime.now());
            userRoleRepository.save(roleModerator);
            UserRoleEntity roleMember = new UserRoleEntity();
            roleMember.setRole(Role.MEMBER);
            roleMember.setCreated(LocalDateTime.now());
            userRoleRepository.save(roleMember);
            UserRoleEntity roleUser = new UserRoleEntity();
            roleUser.setRole(Role.USER);
            roleUser.setCreated(LocalDateTime.now());
            userRoleRepository.save(roleUser);

            UserEntity admin = new UserEntity();
            admin.setEmail("bozhidar.velikov@gmail.com");
            String pass = passwordEncoder.encode(adminPass);
            admin.setPassword(pass);
            admin.setEnabled(true);
            admin.setFirstName("Bozhidar");
            admin.setLastName("Velikov");
            admin.setCountry("Bulgaria");
            admin.setCity("Varna");
            admin.setCreated(LocalDateTime.now());
            admin.addRole(roleAdmin);
            userRepository.save(admin);
//todo clear this - it is just for test

            UserEntity member = new UserEntity();
            member.setEmail("member@member.com");
            member.setPassword(passwordEncoder.encode("123456"));
            member.setEnabled(true);
            member.setFirstName("Member");
            member.setLastName("Member");
            member.addRole(roleMember);
            userRepository.save(member);

        }

    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public void createVerificationToken(UserDto user, String token) {
        UserEntity userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));
        VerificationToken myToken = new VerificationToken(token, userEntity);
        tokenRepository.save(myToken);
    }

    public void saveRegisteredUser(UserDto userDto) {
        UserEntity user = userRepository
                .findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userDto.getId()));
        if (user != null) {
            user.setEnabled(true);
            userRepository.save(user);
        }
    }
}