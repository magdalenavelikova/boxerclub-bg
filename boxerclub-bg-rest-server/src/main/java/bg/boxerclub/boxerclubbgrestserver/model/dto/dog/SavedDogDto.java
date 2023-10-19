package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

public class SavedDogDto {
    private String id;
    private String name;
    private String registrationNum;

    public SavedDogDto() {

    }

    public String getId() {
        return id;
    }

    public SavedDogDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SavedDogDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public SavedDogDto setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }
}
