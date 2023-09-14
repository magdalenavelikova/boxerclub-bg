package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.UserRegisterDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserRoleEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Role;
import bg.boxerclub.boxerclubbgrestserver.model.mapper.UserMapper;
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

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.admin.password}")
    public String adminPass;

    public UserService(UserRoleRepository userRoleRepository, UserRepository userRepository, UserMapper userMapper, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails registerAndLogin(UserRegisterDto userRegisterDto) {

        UserEntity userEntity = userMapper.userDtoToUserEntity(userRegisterDto);
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
