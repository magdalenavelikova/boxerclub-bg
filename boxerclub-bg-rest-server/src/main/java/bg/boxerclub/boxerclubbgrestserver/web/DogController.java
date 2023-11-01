package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.*;
import bg.boxerclub.boxerclubbgrestserver.service.DogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.List;


@RestController
@RequestMapping("/dogs")
public class DogController {

    private final DogService dogService;


    public DogController(DogService dogService) {
        this.dogService = dogService;
    }


    @GetMapping("/approved")
    public ResponseEntity<List<DogViewDto>> getAllApprovedDogs() {
        return
                ResponseEntity.ok(dogService.getAllApproved());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<DogViewDto>> getAllDogs(@AuthenticationPrincipal BoxerClubUserDetails user) {
        return
                ResponseEntity.ok(dogService.getAll());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<DogDetailsDto> getDogDetails(@PathVariable Long id) {
        return ResponseEntity.
                status(HttpStatus.FOUND).
                body(dogService.dogDetails(id));
    }


    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<SavedDogDto> register(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "pedigree", required = false) MultipartFile pedigree,
            @RequestPart("dto") @Valid RegisterDogDto registerDogDto,
            @AuthenticationPrincipal BoxerClubUserDetails user
    ) throws IOException {
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(dogService.registerDog(file, pedigree, registerDogDto, user));
    }

    @PostMapping(value = "/register/parent", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<ParentDto> registerParent(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "pedigree", required = false) MultipartFile pedigree,
            @RequestPart("dto") @Valid ParentDto parentDto,
            @AuthenticationPrincipal BoxerClubUserDetails user
    ) throws IOException {

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(dogService.registerParentDog(file, pedigree, parentDto, user));

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDog(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {

        return ResponseEntity.ok(dogService.deleteDog(id));


    }

    @PutMapping(value = "/edit/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<DogViewDto> editDog(@RequestPart(value = "file", required = false) MultipartFile file,
                                              @RequestPart(value = "pedigree", required = false) MultipartFile pedigree,
                                              @RequestPart("dto") @Valid EditDogDto editDogDto,
                                              @PathVariable Long id,
                                              @AuthenticationPrincipal BoxerClubUserDetails user) throws NoSuchObjectException {
        if (!user.getUsername().equals(editDogDto.getOwnerEmail())
                && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {

            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().body(dogService.editDog(file, pedigree, id, editDogDto, user));
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
    public ResponseEntity<EditDogViewDto> getDog(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        return ResponseEntity.
                status(HttpStatus.FOUND).
                body(dogService.findDogById(id));
    }


}
