package bg.boxerclub.boxerclubbgrestserver.service.impl.user;

import bg.boxerclub.boxerclubbgrestserver.event.OnForgottenPasswordCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.event.OnMembershipRequestCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.event.OnUserRegistrationCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.exception.UserNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.exception.UserNotUniqueException;
import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserRoleEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.VerificationToken;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Role;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserMapper;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserRoleMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRoleRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.VerificationTokenRepository;
import bg.boxerclub.boxerclubbgrestserver.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
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
public class UserServiceImpl implements UserService {
    private final ApplicationEventPublisher eventPublisher;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Value("${app.admin.password}")
    public String adminPass;
    @Value("${app.url}")
    public String baseUrl;

    public UserServiceImpl(ApplicationEventPublisher eventPublisher,
                           UserRoleRepository userRoleRepository,
                           UserRepository userRepository,
                           VerificationTokenRepository tokenRepository,
                           UserMapper userMapper,
                           UserRoleMapper userRoleMapper,
                           UserDetailsService userDetailsService,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.eventPublisher = eventPublisher;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
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

        String appUrl = baseUrl + "/users/register";
        eventPublisher.publishEvent(new OnUserRegistrationCompleteEvent(this, userDto,
                request.getLocale(), appUrl));
        return userDto;
    }

    @Override
    public BoxerClubUserDetails authenticate(String userName) {
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

    @Override
    public BoxerClubUserDetails login(String userName, String password) {

        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userName,
                        password));

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);
        return (BoxerClubUserDetails) auth.getPrincipal();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::userEntityToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            tokenRepository.deleteByUserId(id);
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }

    }

    @Override
    public UserDto getUserByVerificationToken(VerificationToken verificationToken) {
        return userMapper.userEntityToUserDto(verificationToken.getUser());
    }

    @Override
    public UserDto getUserByUserEmail(AuthRequest request) {
        return userMapper.userEntityToUserDto(userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException(request.getUsername())));
    }

    @Override
    public UserEntity getUserByUserEmail(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public List<UserRoleDto> getAllRoles() {
        return userRoleRepository.findAll()
                .stream()
                .map(userRoleMapper::userRoleEntityToUserRoLeDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto editUser(EditUserDto userEditDto, Long id) {
        UserEntity edit = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        Optional<UserEntity> userEmail = userRepository.findByEmail(userEditDto.getEmail());


        if (userEmail.isPresent() && !Objects.equals(edit.getId(), userEmail.get().getId())) {
            throw new UserNotUniqueException(userEditDto.getEmail());
        } else {
            UserEntity temp = userMapper.userEditDtoToUserEntity(userEditDto);

            if (!temp.equals(edit)) {
                edit.setCity(temp.getCity());
                edit.setCountry(temp.getCountry());
                edit.setFirstName(temp.getFirstName());
                edit.setLastName(temp.getLastName());
                edit.setEmail(temp.getEmail());
                edit.setModified(LocalDateTime.now());

                List<UserRoleEntity> newRoles = temp.getRoles()
                        .stream().map(r -> userRoleRepository.findByRole(r.getRole()).orElse(null))
                        .collect(Collectors.toList());
                edit.setRoles(newRoles);

                return userMapper.userEntityToUserDto(userRepository.save(edit));
            }
            return userMapper.userEntityToUserDto(edit);
        }
    }

    @Override
    public EditUserDto getUser(Long id) {
        return userMapper.userEntityToUserEditDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)

        ));
    }

    @Override
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

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public VerificationToken getVerificationTokenByUser(UserEntity user) {

        return tokenRepository.findByUser(user).orElse(null);
    }

    @Override
    public void createVerificationToken(UserDto user, String token) {
        UserEntity userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));
        VerificationToken myToken = new VerificationToken(token, userEntity);
        tokenRepository.save(myToken);
    }

    @Override
    public void saveRegisteredUser(UserDto userDto) {
        UserEntity user = userRepository
                .findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userDto.getId()));
        if (user != null) {
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    @Override
    public boolean changePassword(UserChangePasswordDto userChangePasswordDto, BoxerClubUserDetails user) {
        UserEntity userEntity = userRepository.findByEmail(user.getUsername()).orElseThrow(() -> new UserNotFoundException(user.getUsername()));

        boolean isAuthenticated = passwordEncoder.matches(userChangePasswordDto.getOldPassword(), userEntity.getPassword());
        if (isAuthenticated) {
            userEntity.setPassword(passwordEncoder.encode(userChangePasswordDto.getNewPassword()));
            userEntity.setModified(LocalDateTime.now());
            userRepository.save(userEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void forgottenPassword(AuthRequest authRequest, ServletWebRequest request) {
        String appUrl = baseUrl + "/users/forgotten-password";
        eventPublisher.publishEvent(new OnForgottenPasswordCompleteEvent(this,
                appUrl, request.getLocale(), authRequest));
    }

    @Override
    public void setNewPassword(UserForgottenPasswordDto forgottenPasswordNewPasswordDto) {
        UserDto userDto = this.getUserByVerificationToken(this.getVerificationToken(forgottenPasswordNewPasswordDto.getVerificationToken()));
        UserEntity user = userRepository.findById(userDto.getId()).orElseThrow(() -> new UserNotFoundException(userDto.getId()));
        user.setPassword(passwordEncoder.encode(forgottenPasswordNewPasswordDto.getPassword()));
        user.setModified(LocalDateTime.now());
        this.userRepository.save(user);
    }

    @Override
    public void membership(AuthRequest authRequest, ServletWebRequest request) {
        eventPublisher.publishEvent(new OnMembershipRequestCompleteEvent(this,
                authRequest.getUsername(), request.getLocale()));
    }
}