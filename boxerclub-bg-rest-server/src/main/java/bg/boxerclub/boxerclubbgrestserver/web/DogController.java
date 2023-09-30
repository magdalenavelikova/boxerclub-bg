package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.DogDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.ParentDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.RegisterDogDto;
import bg.boxerclub.boxerclubbgrestserver.service.DogService;
import bg.boxerclub.boxerclubbgrestserver.service.PedigreeFileService;
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
    private final PedigreeFileService fileService;


    public DogController(DogService dogService, PedigreeFileService fileService) {
        this.dogService = dogService;

        this.fileService = fileService;
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
        return ResponseEntity.ok().body(dogService.registerDog(file, registerDto, user));
    }

    @PostMapping(value = "/register/parent",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> registerParent(
            @RequestPart("file") MultipartFile file,
            @RequestPart("dto") String dogParentDto,
            @AuthenticationPrincipal BoxerClubUserDetails user
    ) throws IOException {
        ParentDto parentDto = new ParentDto();

        byte[] bytes = dogParentDto.getBytes(StandardCharsets.ISO_8859_1);
        String utf8Encoded = new String(bytes, StandardCharsets.UTF_8);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            parentDto = objectMapper.readValue(utf8Encoded, ParentDto.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok().body(dogService.registerParentDog(file, parentDto, user));


    }


    @PostMapping(value = "/pedigree/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> uploadModel(@RequestPart("file") MultipartFile file,
                                         @RequestPart("dto") String dto,
                                         @AuthenticationPrincipal BoxerClubUserDetails user) throws IOException {
        return ResponseEntity.ok().body(fileService.upload(file, dto));
    }



  /*  @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDogDto registerDogDto,
                                      @AuthenticationPrincipal BoxerClubUserDetails user
    ) throws IOException {

        return ResponseEntity.ok().body(dogService.registerDogWithoutPicture(registerDogDto));
    }*/

}
