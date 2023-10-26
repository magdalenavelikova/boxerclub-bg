package bg.boxerclub.boxerclubbgrestserver.model.dto.user;

public class UserErrorDto {


    private final String userId;
    private final String description;

    public UserErrorDto(String userId, String description) {
        this.userId = userId;
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

}
