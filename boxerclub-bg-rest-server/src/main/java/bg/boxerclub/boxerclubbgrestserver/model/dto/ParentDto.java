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

    public ParentDto() {

    }

    public String getName() {
        return name;
    }

    public ParentDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public ParentDto setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public ParentDto setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public String getMicroChip() {
        return microChip;
    }

    public ParentDto setMicroChip(String microChip) {
        this.microChip = microChip;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public ParentDto setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getColor() {
        return color;
    }

    public ParentDto setColor(String color) {
        this.color = color;
        return this;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public ParentDto setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getHealthStatusEntity() {
        return healthStatusEntity;
    }

    public ParentDto setHealthStatusEntity(String healthStatusEntity) {
        this.healthStatusEntity = healthStatusEntity;
        return this;
    }
}
