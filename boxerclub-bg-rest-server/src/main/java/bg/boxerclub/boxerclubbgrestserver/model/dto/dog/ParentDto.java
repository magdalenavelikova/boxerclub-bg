package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

import bg.boxerclub.boxerclubbgrestserver.model.validations.UniqueRegistrationNumber;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParentDto {
    @NotEmpty
    private String name;
    @UniqueRegistrationNumber(message = "There is already a registered dog with this number.")
    // @NotEmpty
    private String registrationNum;
    private String pictureUrl;
    private String microChip;
    @NotEmpty
    private String sex;
    @NotEmpty
    private String color;

    private String birthday;
    private String healthStatus;
    private String kennel;
    private String childId;

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

    public String getBirthday() {
        return birthday;
    }

    public ParentDto setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public ParentDto setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
        return this;
    }

    public String getKennel() {
        return kennel;
    }

    public ParentDto setKennel(String kennel) {
        this.kennel = kennel;
        return this;
    }

    public String getChildId() {
        return childId;
    }

    public ParentDto setChildId(String childId) {
        this.childId = childId;
        return this;
    }
}
