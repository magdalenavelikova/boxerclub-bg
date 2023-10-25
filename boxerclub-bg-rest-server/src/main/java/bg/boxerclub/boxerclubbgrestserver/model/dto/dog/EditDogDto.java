package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public class EditDogDto {
    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String registrationNum;

    @NotEmpty
    private String microChip;
    @NotEmpty
    private String sex;
    @NotEmpty
    private String color;
    @PastOrPresent(message = "Birth date must be in the past or present!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String healthStatus;
    @NotEmpty
    private String kennel;
    @NotEmpty
    private String ownerEmail;

    private String motherRegistrationNum;

    private String fatherRegistrationNum;

    public EditDogDto() {
    }

    public Long getId() {
        return id;
    }

    public EditDogDto setId(Long id) {
        this.id = id;
        return this;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public EditDogDto setBirthday(LocalDate birthday) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EditDogDto that = (EditDogDto) o;
        return Objects.equals(name, that.name)
                && Objects.equals(registrationNum, that.registrationNum)
                && Objects.equals(microChip, that.microChip)
                && Objects.equals(sex, that.sex)
                && Objects.equals(color, that.color)
                && Objects.equals(birthday, that.birthday)
                && Objects.equals(healthStatus, that.healthStatus)
                && Objects.equals(kennel, that.kennel)
                && Objects.equals(ownerEmail, that.ownerEmail)
                && Objects.equals(motherRegistrationNum, that.motherRegistrationNum)
                && Objects.equals(fatherRegistrationNum, that.fatherRegistrationNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, registrationNum, microChip, sex, color, birthday, healthStatus, kennel, ownerEmail, motherRegistrationNum, fatherRegistrationNum);
    }
}
