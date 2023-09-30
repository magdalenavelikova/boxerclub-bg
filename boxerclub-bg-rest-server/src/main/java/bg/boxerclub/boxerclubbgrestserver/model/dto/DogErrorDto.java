package bg.boxerclub.boxerclubbgrestserver.model.dto;

public class DogErrorDto {

    private final String dogId;
    private final String description;

    public DogErrorDto(String dogId, String description) {
        this.dogId = dogId;
        this.description = description;
    }

    public String getDogId() {
        return dogId;
    }

    public String getDescription() {
        return description;
    }
}
