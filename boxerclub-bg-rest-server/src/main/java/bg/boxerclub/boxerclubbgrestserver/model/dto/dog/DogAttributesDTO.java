package bg.boxerclub.boxerclubbgrestserver.model.dto.dog;

public class DogAttributesDTO {
    private String registrationNumber;
    private String color;

    public DogAttributesDTO() {
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public DogAttributesDTO setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        return this;
    }

    public String getColor() {
        return color;
    }

    public DogAttributesDTO setColor(String color) {
        this.color = color;
        return this;
    }
}
