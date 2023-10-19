package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

import bg.boxerclub.boxerclubbgrestserver.model.validations.UniqueRegistrationNumber;
import jakarta.validation.constraints.NotEmpty;

public class EditDogDto {
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
    private String ownerEmail;

    private String motherRegistrationNum;

    private String fatherRegistrationNum;

    public EditDogDto() {
    }

    public String getName() {
        return name;
    }

    public EditDogDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public EditDogDto setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }

    public String getMicroChip() {
        return microChip;
    }

    public EditDogDto setMicroChip(String microChip) {
        this.microChip = microChip;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public EditDogDto setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getColor() {
        return color;
    }

    public EditDogDto setColor(String color) {
        this.color = color;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public EditDogDto setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public EditDogDto setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
        return this;
    }

    public String getKennel() {
        return kennel;
    }

    public EditDogDto setKennel(String kennel) {
        this.kennel = kennel;
        return this;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public EditDogDto setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
        return this;
    }

    public String getMotherRegistrationNum() {
        return motherRegistrationNum;
    }

    public EditDogDto setMotherRegistrationNum(String motherRegistrationNum) {
        this.motherRegistrationNum = motherRegistrationNum;
        return this;
    }

    public String getFatherRegistrationNum() {
        return fatherRegistrationNum;
    }

    public EditDogDto setFatherRegistrationNum(String fatherRegistrationNum) {
        this.fatherRegistrationNum = fatherRegistrationNum;
        return this;
    }
}
