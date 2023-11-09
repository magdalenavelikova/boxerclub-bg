package bg.boxerclub.boxerclubbgrestserver.model.dto.contact;

public class ContactErrorDto {
    private final Long id;
    private final String description;

    public ContactErrorDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
