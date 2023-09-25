package bg.boxerclub.boxerclubbgrestserver.model.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class ParentDto {
    @NotEmpty
    private String name;
    //   @UniqueRegistrationNumber(message = "There is already a registered dor with this number.")
    // @NotEmpty
    private String registrationNum;
    private String pictureUrl;
    private String microChip;
    @NotEmpty
    private String sex;
    @NotEmpty
    private String color;
    @NotEmpty
    private LocalDate birthday;
    private String healthStatusEntity;
}
