package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

public class DogDto {
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

    public DogDto() {
    }

    public String getId() {
        return id;
    }

    public DogDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DogDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public DogDto setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public DogDto setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }

    public String getMicroChip() {
        return microChip;
    }

    public DogDto setMicroChip(String microChip) {
        this.microChip = microChip;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public DogDto setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getColor() {
        return color;
    }

    public DogDto setColor(String color) {
        this.color = color;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public DogDto setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public DogDto setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
        return this;
    }

    public String getKennel() {
        return kennel;
    }

    public DogDto setKennel(String kennel) {
        this.kennel = kennel;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public DogDto setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }
}
