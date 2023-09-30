package bg.boxerclub.boxerclubbgrestserver.model.dto;

import bg.boxerclub.boxerclubbgrestserver.model.validations.UniqueRegistrationNumber;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterDogDto {
    @NotEmpty
    private String name;
    @UniqueRegistrationNumber(message = "There is already a registered dog with this number.")
    @NotEmpty
    private String registrationNum;

    @NotEmpty
    private String microChip;
    @NotEmpty
    private String sex;
    @NotEmpty
    private String color;
    @NotEmpty
    private String birthday;
    private String healthStatus;
    @NotEmpty
    private String kennel;
    @NotEmpty
    private String ownerId;

    private String motherId;

    private String fatherId;

    public RegisterDogDto() {
    }

    public String getName() {
        return name;
    }

    public RegisterDogDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public RegisterDogDto setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }

//    public MultipartFile getPicture() {
//        return file;
//    }
//
//    public DogRegisterDto setPicture(MultipartFile file) {
//        this.file = file;
//        return this;
//    }

    public String getMicroChip() {
        return microChip;
    }

    public RegisterDogDto setMicroChip(String microChip) {
        this.microChip = microChip;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public RegisterDogDto setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getColor() {
        return color;
    }

    public RegisterDogDto setColor(String color) {
        this.color = color;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public RegisterDogDto setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public RegisterDogDto setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
        return this;
    }

    public String getKennel() {
        return kennel;
    }

    public RegisterDogDto setKennel(String kennel) {
        this.kennel = kennel;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public RegisterDogDto setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getMotherId() {
        return motherId;
    }

    public RegisterDogDto setMotherId(String motherId) {
        this.motherId = motherId;
        return this;
    }

    public String getFatherId() {
        return fatherId;
    }

    public RegisterDogDto setFatherId(String fatherId) {
        this.fatherId = fatherId;
        return this;
    }
}
