package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.DogRegisterDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.ParentDto;
import bg.boxerclub.boxerclubbgrestserver.service.DogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> register(@RequestBody @Valid DogRegisterDto dogRegisterDto,
//                                      @RequestPart(value = "file") MultipartFile file,
//                                      @RequestPart(name = "dto") @Valid DogRegisterDto dogRegisterDto,
                                      @AuthenticationPrincipal BoxerClubUserDetails user) {
        dogService.registerDog(dogRegisterDto, user);

        return ResponseEntity.ok()

                .build();
    }

    // @PostMapping(path = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})


    //  @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/add")
//    public ResponseEntity<?> addDog(/*@RequestPart(value = "file") MultipartFile file,
//                                      @RequestPart(name = "dto") @Valid DogRegisterDto dogRegisterDto,*/
//            @RequestBody DogRegisterDto dogRegisterDto
//    ) throws IOException {
//        dogService.registerDog(dogRegisterDto);
//
//        return ResponseEntity.ok().build();
//    }

}
