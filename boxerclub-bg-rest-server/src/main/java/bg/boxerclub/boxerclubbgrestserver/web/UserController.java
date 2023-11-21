package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.VerificationToken;
import bg.boxerclub.boxerclubbgrestserver.service.user.AppUserDetailService;
import bg.boxerclub.boxerclubbgrestserver.service.user.JwtService;
import bg.boxerclub.boxerclubbgrestserver.service.user.UserService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Nullable;
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


    public UserController(AuthenticationManager authenticationManager, JwtService jwtService, AppUserDetailService userDetailService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
        this.userService = userService;


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
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserDto registerUserDto,
                                      ServletWebRequest request) {

        UserDto user = userService.registerNewUserAccount(registerUserDto, request);

        return ResponseEntity.ok().body(user);

    }

    @GetMapping("/registerConfirm")
    public ResponseEntity<?> confirmRegistration
            (@RequestParam("token") String token) {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        ResponseEntity<String> UNAUTHORIZED = getStringResponseEntity(verificationToken);
        if (UNAUTHORIZED != null) return UNAUTHORIZED;

        UserDto user = userService.getUserByVerificationToken(verificationToken);
        userService.saveRegisteredUser(user);
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
    public ResponseEntity<UserDto> editUser(@RequestBody @Valid EditUserDto editUserDto, @PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        if (Objects.equals(id, user.getId()) || user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.ok()
                    .body(userService.editUser(editUserDto));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

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

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid UserChangePasswordDto userChangePasswordDto,
                                            @AuthenticationPrincipal BoxerClubUserDetails user) {
        if (userService.changePassword(userChangePasswordDto, user)) {
            String messageValue = "Successfully changed password";
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("{\"message\": \"" + messageValue + "\"}");
        }
        String messageValue = "Old password does not match";
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("{\"message\": \"" + messageValue + "\" }");
    }

    @PostMapping("/forgotten-password")
    public ResponseEntity<?> forgottenPassword(@RequestBody AuthRequest authRequest, ServletWebRequest request) {
        if (isValid(authRequest) != null) {
            userService.forgottenPassword(authRequest, request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PatchMapping("/forgotten-password/new-password")
    public ResponseEntity<?> forgottenPasswordNewPassword(@RequestBody @Valid UserForgottenPasswordDto forgottenPasswordNewPasswordDto) {
        VerificationToken verificationToken = userService.getVerificationToken(forgottenPasswordNewPasswordDto.getVerificationToken());
        ResponseEntity<String> UNAUTHORIZED =
                getStringResponseEntity(verificationToken);
        if (UNAUTHORIZED != null) return UNAUTHORIZED;
        userService.setNewPassword(forgottenPasswordNewPasswordDto);
        String messageValue = "Successfully changed password";
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("{ \"message\": \"" + messageValue + "\" }");
    }

    @Nullable
    private static ResponseEntity<String> getStringResponseEntity(VerificationToken verificationToken) {
        if (verificationToken == null) {
            String messageValue = "User is disabled";

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(messageValue);
        }

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = "User account has expired";
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(messageValue);
        }
        return null;
    }

    private UserDetails isValid(AuthRequest request) {
        return userDetailService.loadUserByUsername(request.getUsername());
    }


}
