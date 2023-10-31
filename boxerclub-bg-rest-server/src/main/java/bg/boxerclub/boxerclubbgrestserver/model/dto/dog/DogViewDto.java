package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

public class DogViewDto {
    private String id;
    private String name;
    private String pictureUrl;
    private String registrationNum;
    private String microChip;
    private String sex;
    private String color;


    private String birthday;
    private String healthStatus;
    private String kennel;
    private String ownerId;
    private boolean hasPedigree = false;

    public DogViewDto() {
    }

    public String getId() {
        return id;
    }

    public DogViewDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DogViewDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public DogViewDto setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public DogViewDto setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }

    public String getMicroChip() {
        return microChip;
    }

    public DogViewDto setMicroChip(String microChip) {
        this.microChip = microChip;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public DogViewDto setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getColor() {
        return color;
    }

    public DogViewDto setColor(String color) {
        this.color = color;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public DogViewDto setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public DogViewDto setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
        return this;
    }

    public String getKennel() {
        return kennel;
    }

    public DogViewDto setKennel(String kennel) {
        this.kennel = kennel;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public DogViewDto setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public boolean isHasPedigree() {
        return hasPedigree;
    }

    public DogViewDto setHasPedigree(boolean hasPedigree) {
        this.hasPedigree = hasPedigree;
        return this;
    }
}
