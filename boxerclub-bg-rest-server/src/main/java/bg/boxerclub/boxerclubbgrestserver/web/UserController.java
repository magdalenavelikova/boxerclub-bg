package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.event.OnRegistrationCompleteEvent;
import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.VerificationToken;
import bg.boxerclub.boxerclubbgrestserver.service.AppUserDetailService;
import bg.boxerclub.boxerclubbgrestserver.service.JwtService;
import bg.boxerclub.boxerclubbgrestserver.service.UserService;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import java.rmi.NoSuchObjectException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


@RestController

@RequestMapping("/users")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserDetailService userDetailService;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public UserController(AuthenticationManager authenticationManager, JwtService jwtService, AppUserDetailService userDetailService, UserService userService, ApplicationEventPublisher eventPublisher) {
        this.authenticationManager = authenticationManager;

        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        if (isValid(request) != null) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            BoxerClubUserDetails user = (BoxerClubUserDetails) authentication.getPrincipal();
            user.setPassword(null);
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtService.generateToken(user)
                    )
                    .body(user);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserDto registerUserDto, ServletWebRequest request) {
        UserEntity user = userService.registerNewUserAccount(registerUserDto);

        String appUrl = "http://localhost:3000/users";


        BoxerClubUserDetails userDetails = userService.login(user.getEmail());
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user,
                request.getLocale(), appUrl));
        user.setPassword(null);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtService.generateToken(userDetails)
                )
                .body(user);
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<?> confirmRegistration
            (@RequestParam("token") String token) {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = "User is disabled";

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(message);
        }

        UserEntity user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = "User account has expired";

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body((messageValue));
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        user.setPassword(null);
        BoxerClubUserDetails userDetails = userService.login(user.getEmail());
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtService.generateToken(userDetails)
                )
                .body(user);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers(@AuthenticationPrincipal BoxerClubUserDetails user) {

        return ResponseEntity.ok()
                .body(userService.getAllUsers());

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> editUser(@RequestBody @Valid EditUserDto editUserDto, @PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) throws NoSuchObjectException {
        if (Objects.equals(id, user.getId()) || user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.ok()
                    .body(userService.editUser(editUserDto));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {

        return ResponseEntity.ok()
                .body(userService.getUser(id));
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserRoleDto>> getAllRoles(@AuthenticationPrincipal BoxerClubUserDetails user) {
        return ResponseEntity.ok()
                .body(userService.getAllRoles());

    }

    private UserDetails isValid(AuthRequest request) {
        return userDetailService.loadUserByUsername(request.getUsername());
    }


}
