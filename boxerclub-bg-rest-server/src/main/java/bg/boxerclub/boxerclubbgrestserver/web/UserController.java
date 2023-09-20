package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.AuthRequest;
import bg.boxerclub.boxerclubbgrestserver.model.dto.UserDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.UserRegisterDto;
import bg.boxerclub.boxerclubbgrestserver.service.AppUserDetailService;
import bg.boxerclub.boxerclubbgrestserver.service.JwtService;
import bg.boxerclub.boxerclubbgrestserver.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:8080",
        "https://www.boxerclub-bg.org/"},
        allowCredentials = "true", allowedHeaders = "true")
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
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {

        BoxerClubUserDetails user = (BoxerClubUserDetails) userService.registerAndLogin(userRegisterDto);
        user.setPassword(null);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtService.generateToken(user)
                )
                .body(user);
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers(@AuthenticationPrincipal BoxerClubUserDetails user) {
        return ResponseEntity.
                ok(userService.getAllUsers());

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal BoxerClubUserDetails user) {

        return ResponseEntity.ok().build();


    }

    private UserDetails isValid(AuthRequest request) {
        return userDetailService.loadUserByUsername(request.getUsername());
    }


}
