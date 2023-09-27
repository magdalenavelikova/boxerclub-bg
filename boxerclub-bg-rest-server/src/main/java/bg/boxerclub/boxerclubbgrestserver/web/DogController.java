package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.DogDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.RegisterDogDto;
import bg.boxerclub.boxerclubbgrestserver.service.DogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:8080",
        "https://www.boxerclub-bg.org/"},
        allowCredentials = "true", allowedHeaders = "true")
@RequestMapping("/dogs")
public class DogController {

    private final DogService dogService;


    public DogController(DogService dogService) {
        this.dogService = dogService;

    }


    @GetMapping
    public ResponseEntity<List<DogDto>> getAll() {
        return ResponseEntity.ok(dogService.getAll());
    }

    @PostMapping(value = "/register",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> register(
            @RequestPart("file") MultipartFile file,
            @RequestPart("dto") String dogRegisterDto,
            @AuthenticationPrincipal BoxerClubUserDetails user
    ) throws IOException {
        RegisterDogDto registerDto = new RegisterDogDto();

        byte[] bytes = dogRegisterDto.getBytes(StandardCharsets.ISO_8859_1);
        String utf8Encoded = new String(bytes, StandardCharsets.UTF_8);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            registerDto = objectMapper.readValue(utf8Encoded, RegisterDogDto.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok().body(dogService.registerDog(file, registerDto));
    }

  /*  @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDogDto registerDogDto,
                                      @AuthenticationPrincipal BoxerClubUserDetails user
    ) throws IOException {

        return ResponseEntity.ok().body(dogService.registerDogWithoutPicture(registerDogDto));
    }*/

}
