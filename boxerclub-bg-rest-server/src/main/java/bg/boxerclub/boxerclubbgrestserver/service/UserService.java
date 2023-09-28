package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.EditUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.RegisterUserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.UserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.UserRoleDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserRoleEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Role;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserMapper;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserRoleMapper;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.admin.password}")
    public String adminPass;

    public UserService(UserRoleRepository userRoleRepository, UserRepository userRepository, UserMapper userMapper, UserRoleMapper userRoleMapper, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails registerAndLogin(RegisterUserDto registerUserDto) {

        UserEntity userEntity = userMapper.userDtoToUserEntity(registerUserDto);
        String rowPassword = userEntity.getPassword();
        String password = passwordEncoder.encode(rowPassword);
        userEntity.setPassword(password);
        userEntity.setCreated(LocalDateTime.now());
        userEntity.addRole(userRoleRepository.findByRole(Role.USER).get());
        userRepository.save(userEntity);
        return login(userEntity.getEmail());

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
        userRepository.deleteById(id);
    }

    public List<UserRoleDto> getAllRoles() {
        return userRoleRepository.findAll()
                .stream()
                .map(userRoleMapper::userRoleEntityToUserRoLeDto)
                .collect(Collectors.toList());
    }

    public boolean editUser(EditUserDto userEditDto) throws NoSuchObjectException {
        UserEntity edit = userRepository.findById(userEditDto.getId())
                .orElseThrow(() -> new NoSuchObjectException("No such user"));
        Optional<UserEntity> userEmail = userRepository.findByEmail(userEditDto.getEmail());


        if (userEmail.isPresent() && !Objects.equals(edit.getId(), userEmail.get().getId())) {
            return false;
        } else {
            UserEntity temp = userMapper.userEditDtoToUserEntity(userEditDto);
            boolean isUpdated = false;
            isUpdated = isUpdated(edit, temp, isUpdated);


            if (isUpdated) {
                edit.setModified(LocalDateTime.now());
            }
            userRepository.saveAndFlush(edit);
            return true;
        }


    }

    private static boolean isUpdated(UserEntity edit, UserEntity temp, boolean isUpdated) {
        if (!edit.getEmail().equals(temp.getEmail())) {
            edit.setEmail(temp.getEmail());
            isUpdated = true;
        }
        if (!edit.getFirstName().equals(temp.getFirstName())) {
            edit.setFirstName(temp.getFirstName());
            isUpdated = true;
        }
        if (!edit.getLastName().equals(temp.getLastName())) {
            edit.setLastName(temp.getLastName());
            isUpdated = true;
        }
        if (!edit.getCountry().equals(temp.getCountry())) {
            edit.setCountry(temp.getCountry());
            isUpdated = true;
        }
        if (!edit.getCity().equals(temp.getCity())) {
            edit.setCity(temp.getCity());
            isUpdated = true;
        }
        if (!edit.getRoles().equals(temp.getRoles())) {
            edit.setRoles(temp.getRoles());
            isUpdated = true;
        }
        return isUpdated;
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
            admin.setFirstName("Bozhidar");
            admin.setLastName("Velikov");
            admin.setCountry("Bulgaria");
            admin.setCity("Varna");
            admin.setCreated(LocalDateTime.now());
            admin.addRole(roleAdmin);


            userRepository.save(admin);

        }

    }
}
