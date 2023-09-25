package bg.boxerclub.boxerclubbgrestserver.model.dto;

import jakarta.validation.constraints.NotEmpty;

public class DogRegisterDto {
    @NotEmpty
    private String name;
    //   @UniqueRegistrationNumber(message = "There is already a registered dor with this number.")
    // @NotEmpty
    private String registrationNum;
    // private MultipartFile picture;
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

    public DogRegisterDto() {
    }

    public String getName() {
        return name;
    }

    public DogRegisterDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public DogRegisterDto setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }

//    public MultipartFile getPicture() {
//        return picture;
//    }
//
//    public DogRegisterDto setPicture(MultipartFile picture) {
//        this.picture = picture;
//        return this;
//    }

    public String getMicroChip() {
        return microChip;
    }

    public DogRegisterDto setMicroChip(String microChip) {
        this.microChip = microChip;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public DogRegisterDto setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getColor() {
        return color;
    }

    public DogRegisterDto setColor(String color) {
        this.color = color;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public DogRegisterDto setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public DogRegisterDto setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
        return this;
    }

    public String getKennel() {
        return kennel;
    }

    public DogRegisterDto setKennel(String kennel) {
        this.kennel = kennel;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public DogRegisterDto setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getMotherId() {
        return motherId;
    }

    public DogRegisterDto setMotherId(String motherId) {
        this.motherId = motherId;
        return this;
    }

    public String getFatherId() {
        return fatherId;
    }

    public DogRegisterDto setFatherId(String fatherId) {
        this.fatherId = fatherId;
        return this;
    }
}
