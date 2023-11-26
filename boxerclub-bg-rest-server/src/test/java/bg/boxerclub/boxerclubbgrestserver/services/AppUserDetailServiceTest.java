package bg.boxerclub.boxerclubbgrestserver.services;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserRoleEntity;
import bg.boxerclub.boxerclubbgrestserver.model.enums.Role;
import bg.boxerclub.boxerclubbgrestserver.repository.UserRepository;
import bg.boxerclub.boxerclubbgrestserver.service.impl.user.AppUserDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AppUserDetailServiceTest {
    @Mock
    private UserRepository mockUserRepository;
    private UserEntity testUserEntity;
    private AppUserDetailService toTest;

    @BeforeEach
    void setup() {

        toTest = new AppUserDetailService(mockUserRepository);
        testUserEntity = new UserEntity() {
            {
                setEmail("test@gmail.com");
                setPassword("456123");
                setFirstName("Maggie");
                setLastName("Velikova");
                setEnabled(true);
                setRoles(List.of(new UserRoleEntity() {{
                    setRole(Role.ADMIN);
                }}, new UserRoleEntity() {{
                    setRole(Role.USER);
                }}));
            }

        };
        when(mockUserRepository.findByEmail(testUserEntity.getEmail())).thenReturn(Optional.of(testUserEntity));
    }

    @Test
    void testLoadUserByUsernameWhenUserExist() {
        BoxerClubUserDetails userDetails = (BoxerClubUserDetails) toTest.loadUserByUsername(testUserEntity.getEmail());
        assertEquals(testUserEntity.getEmail(), userDetails.getUsername());
        assertEquals(testUserEntity.getFirstName(), userDetails.getFirstName());
        assertEquals(testUserEntity.getLastName(), userDetails.getLastName());
        assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        assertEquals(testUserEntity.getFirstName() + " " + testUserEntity.getLastName(), userDetails.getFullName());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(2, authorities.size());
        Iterator<? extends GrantedAuthority> authoritiesIterator = authorities.iterator();

        assertEquals("ROLE_" + Role.ADMIN.name(), authoritiesIterator.next().getAuthority());
        assertEquals("ROLE_" + Role.USER.name(), authoritiesIterator.next().getAuthority());
    }

    @Test
    void testLoadUserByUsernameWhenUserDoesNotExist() {
        assertThrows(UsernameNotFoundException.class, () -> toTest.loadUserByUsername("nonExist@gmail.com"));

    }
}
