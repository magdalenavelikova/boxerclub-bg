package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.ParentDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.RegisterDogDto;
import bg.boxerclub.boxerclubbgrestserver.service.DogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<List<ParentDto>> getAll() {
        return ResponseEntity.ok(dogService.getAll());
    }

    @PostMapping(value = "/registerWithFileUpload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    // @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> register(//@RequestBody @Valid DogRegisterDto dogRegisterDto,
                                      @RequestPart("file") MultipartFile file,
                                      @RequestPart("dto") String dogRegisterDto,
                                      @AuthenticationPrincipal BoxerClubUserDetails user
    ) throws IOException {
        RegisterDogDto registerDto = new RegisterDogDto();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            registerDto = objectMapper.readValue(dogRegisterDto, RegisterDogDto.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        return ResponseEntity.ok().body(dogService.registerDog(file, registerDto));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDogDto registerDogDto,
                                      @AuthenticationPrincipal BoxerClubUserDetails user
    ) throws IOException {

        return ResponseEntity.ok().body(dogService.registerDogWithoutPicture(registerDogDto));
    }


}
