package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserRoleEntity;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findByEmail(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credential"));

    }

    private BoxerClubUserDetails map(UserEntity userEntity) {
        return new BoxerClubUserDetails(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getRoles().stream().map(this::map).toList()
        );
    }

    private GrantedAuthority map(UserRoleEntity userRoleEntity) {
        return new SimpleGrantedAuthority("ROLE_" + userRoleEntity.getRole().name());
    }
}
