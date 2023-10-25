package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

public class EditDogViewDto {

    private Long id;

    private String name;

    private String registrationNum;


    private String microChip;

    private String sex;

    private String color;

    private String birthday;
    private String healthStatus;

    private String kennel;

    private String ownerEmail;

    private String motherRegistrationNum;

    private String fatherRegistrationNum;

    public EditDogViewDto() {
    }

    public Long getId() {
        return id;
    }

    public EditDogViewDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EditDogViewDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public EditDogViewDto setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }

    public String getMicroChip() {
        return microChip;
    }

    public EditDogViewDto setMicroChip(String microChip) {
        this.microChip = microChip;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public EditDogViewDto setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getColor() {
        return color;
    }

    public EditDogViewDto setColor(String color) {
        this.color = color;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public EditDogViewDto setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public EditDogViewDto setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
        return this;
    }

    public String getKennel() {
        return kennel;
    }

    public EditDogViewDto setKennel(String kennel) {
        this.kennel = kennel;
        return this;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public EditDogViewDto setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
        return this;
    }

    public String getMotherRegistrationNum() {
        return motherRegistrationNum;
    }

    public EditDogViewDto setMotherRegistrationNum(String motherRegistrationNum) {
        this.motherRegistrationNum = motherRegistrationNum;
        return this;
    }

    public String getFatherRegistrationNum() {
        return fatherRegistrationNum;
    }

    public EditDogViewDto setFatherRegistrationNum(String fatherRegistrationNum) {
        this.fatherRegistrationNum = fatherRegistrationNum;
        return this;
    }
}
