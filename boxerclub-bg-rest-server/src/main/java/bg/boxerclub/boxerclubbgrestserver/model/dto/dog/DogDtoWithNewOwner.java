package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

public class DogDtoWithNewOwner {

    private String registrationNum;

    private String newOwnerId;

    public DogDtoWithNewOwner() {

    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public DogDtoWithNewOwner setRegistrationNum(String registrationNum) {
        this.registrationNum = registrationNum;
        return this;
    }

    public String getNewOwnerId() {
        return newOwnerId;
    }

    public DogDtoWithNewOwner setNewOwnerId(String newOwnerId) {
        this.newOwnerId = newOwnerId;
        return this;
    }
}
