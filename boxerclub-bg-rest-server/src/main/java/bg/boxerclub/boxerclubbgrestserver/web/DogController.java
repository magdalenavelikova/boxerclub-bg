package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.*;
import bg.boxerclub.boxerclubbgrestserver.service.dog.DogChartService;
import bg.boxerclub.boxerclubbgrestserver.service.dog.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/dogs")
@Tag(name = "Dogs")
public class DogController {

    private final DogService dogService;

    private final DogChartService dogChartService;

    public DogController(DogService dogService, DogChartService dogChartService) {
        this.dogService = dogService;
        this.dogChartService = dogChartService;
    }

    @Operation(summary = "Get all approved dogs")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "View all approved dogs")}
    )
    @GetMapping("/approved")
    public ResponseEntity<List<DogViewDto>> getAllApprovedDogs() {
        return
                ResponseEntity.ok(dogService.getAllApproved());
    }

    @Operation(summary = "Get all dogs", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "View all dogs"),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR.")}
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<DogViewDto>> getAllDogs(@AuthenticationPrincipal BoxerClubUserDetails user) {
        return
                ResponseEntity.ok(dogService.getAll());
    }

    @Operation(summary = "Get dog's details")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "View all details about dog"),
                    @ApiResponse(responseCode = "404", description = "Dog was not found.")}
    )
    @GetMapping("/details/{id}")
    public ResponseEntity<DogDetailsDto> getDogDetails(@PathVariable Long id) {
        return ResponseEntity.
                status(HttpStatus.FOUND).
                body(dogService.dogDetails(id));
    }

    @Operation(summary = "Get dog's pedigree chart")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "View dog's pedigree chart"),
                    @ApiResponse(responseCode = "404", description = "Dog was not found.")}
    )
    @GetMapping("/chart/{id}")
    public DogChartNodeDTO getDogChart(@PathVariable Long id) {
        return dogChartService.createDogChart(id, 4);
    }

    @Operation(summary = "Confirm new owner")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Ownership change successful"),
                    @ApiResponse(responseCode = "404", description = "Dog or owner was not found.")}

    )
    @GetMapping("/ownershipConfirm")
    public ResponseEntity<?> confirmChangeOwnerShip
            (@RequestParam("registrationNum") String registrationNum,
             @RequestParam("newOwner") String newOwner) {
        dogService.confirmChangeOwnership(registrationNum, newOwner);

        String messageValue = "Ownership change successful";

        return ResponseEntity.ok()
                .body("{ \"message\": \"" + messageValue + "\" }");
    }

    @Operation(summary = "Get dog's details for edit form", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {@ApiResponse(responseCode = "302", description = "Get dog's details"),
                    @ApiResponse(responseCode = "404", description = "Dog was not found."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR or MEMBER."),
                    @ApiResponse(responseCode = "401", description = "User is a MEMBER, but not the owner of the dog.")}
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<EditDogViewDto> getDog(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        return ResponseEntity.
                status(HttpStatus.FOUND).
                body(dogService.findDogById(id));
    }

    @Operation(summary = "Create dog", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {@ApiResponse(responseCode = "201", description = "Dog was registered.",
                    content = {@Content(mediaType = "multipart/form-data",
                            schema = @Schema(implementation = RegisterDogDto.class))}),
                    @ApiResponse(responseCode = "409", description = "Some fields were incorrect."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR or MEMBER.")}
    )
    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<SavedDogDto> register(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "pedigree", required = false) MultipartFile pedigree,
            @RequestPart("dto") @Valid RegisterDogDto registerDogDto,
            @AuthenticationPrincipal BoxerClubUserDetails user,
            ServletWebRequest request
    ) throws IOException {
        SavedDogDto saved = dogService.registerDog(file, pedigree, registerDogDto, user, request);

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(saved);
    }

    @Operation(summary = "Create dog's parent", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {@ApiResponse(responseCode = "201", description = "Parent was registered.",
                    content = {@Content(mediaType = "multipart/form-data",
                            schema = @Schema(implementation = RegisterDogDto.class))}),
                    @ApiResponse(responseCode = "404", description = "The parent's descendant was not found."),
                    @ApiResponse(responseCode = "409", description = "Some fields were incorrect."),
                    @ApiResponse(responseCode = "409", description = "Parent is younger than child."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR or MEMBER.")}
    )
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

    @Operation(summary = "Approve dog's info", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {@ApiResponse(responseCode = "302", description = "Dog was approved."),
                    @ApiResponse(responseCode = "404", description = "Dog was not found."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR.")}
    )

    @PostMapping("/approve/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') ")
    public ResponseEntity<EditDogViewDto> approveDog(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {

        return ResponseEntity.
                status(HttpStatus.FOUND).
                body(dogService.approveDogById(id));


    }

    @Operation(summary = "Add existing dog as parent", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {@ApiResponse(responseCode = "201", description = "Parent was added.", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AddParentDto.class))}),
                    @ApiResponse(responseCode = "404", description = "The parent's descendant was not found."),
                    @ApiResponse(responseCode = "409", description = "Parent is younger than child."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR or MEMBER.")}
    )
    @PostMapping("/add/parent")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<ParentDto> addParentDog(@RequestBody @Valid AddParentDto parentDto,
                                                  @AuthenticationPrincipal BoxerClubUserDetails user) {

        try {
            return ResponseEntity.ok().
                    body(dogService.addParentDog(parentDto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Operation(summary = "Request for ownership", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "An email has been sent to the current owner. Once he confirms, the ownership will be changed.", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = DogDtoWithNewOwner.class))}),
                    @ApiResponse(responseCode = "404", description = "Dog was not found."),
                    @ApiResponse(responseCode = "404", description = "Owner was not found."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR or MEMBER.")}
    )
    @PostMapping("/ownership")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<?> requestChangeOwnerShip(@RequestBody @Valid DogDtoWithNewOwner dog,
                                                    @AuthenticationPrincipal BoxerClubUserDetails user,
                                                    ServletWebRequest request) {
        dogService.changeOwnership(dog, request);
        String messageValue = "An email has been sent to the current owner. Once he confirms, the ownership will be changed.";
        return ResponseEntity.ok()
                .body("{ \"message\": \"" + messageValue + "\" }");


    }

    @Operation(summary = "Edit dog's info", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Dog's info was edited.",
                    content = {@Content(mediaType = "multipart/form-data",
                            schema = @Schema(implementation = RegisterDogDto.class))}),
                    @ApiResponse(responseCode = "404", description = "Dog was not found."),
                    @ApiResponse(responseCode = "409", description = "Some fields were incorrect."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR or MEMBER."),
                    @ApiResponse(responseCode = "401", description = "User is a MEMBER, but not the owner of the dog.")}
    )
    @PatchMapping(value = "/edit/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('MEMBER')")
    public ResponseEntity<DogViewDto> editDog(@RequestPart(value = "file", required = false) MultipartFile file,
                                              @RequestPart(value = "pedigree", required = false) MultipartFile pedigree,
                                              @RequestPart("dto") @Valid EditDogDto editDogDto,
                                              @PathVariable Long id,
                                              @AuthenticationPrincipal BoxerClubUserDetails user,
                                              ServletWebRequest request) {
        if (!user.getUsername().equals(editDogDto.getOwnerEmail())
                && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEMBER"))) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().body(dogService.editDog(file, pedigree, id, editDogDto, user, request));
    }

    @Operation(summary = "Delete dog", security = {
            @SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Dog was deleted."),
                    @ApiResponse(responseCode = "404", description = "Dog was not found."),
                    @ApiResponse(responseCode = "401", description = "User has no privileges as an ADMIN or MODERATOR.")
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') ")
    public ResponseEntity<?> deleteDog(@PathVariable Long id, @AuthenticationPrincipal BoxerClubUserDetails user) {
        boolean isDeleted = dogService.deleteDog(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(true);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
    }


}
