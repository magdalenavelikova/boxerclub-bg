package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.AddParentDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.ParentDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.RegisterDogDto;
import bg.boxerclub.boxerclubbgrestserver.service.DogService;
import bg.boxerclub.boxerclubbgrestserver.service.PedigreeFileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
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
        return
                ResponseEntity.ok(dogService.getAll());
    }

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> register(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("dto") @Valid RegisterDogDto registerDogDto,
            @AuthenticationPrincipal BoxerClubUserDetails user
    ) throws IOException {
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(dogService.registerDog(file, registerDogDto, user));
    }

    @PostMapping(value = "/register/parent", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> registerParent(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("dto") @Valid ParentDto parentDto,
            @AuthenticationPrincipal BoxerClubUserDetails user
    ) throws IOException {


        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(dogService.registerParentDog(file, parentDto, user));

    }

    @PostMapping(value = "/pedigree/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> uploadPedigree(@RequestPart("file") MultipartFile file,
                                            @RequestPart("dto") String dto,
                                            @AuthenticationPrincipal BoxerClubUserDetails user) throws IOException {
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(fileService.upload(file, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDog(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {

        return ResponseEntity.ok().body(dogService.deleteDog(id));
    }

    @PostMapping("/add/parent")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> addParentDog(@RequestBody @Valid AddParentDto parentDto, @AuthenticationPrincipal BoxerClubUserDetails user) {
        try {
            return ResponseEntity.ok().
                    body(dogService.addParentDog(parentDto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> getDog(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        return ResponseEntity.
                status(HttpStatus.FOUND).
                body(dogService.findDogById(id));
    }
}
