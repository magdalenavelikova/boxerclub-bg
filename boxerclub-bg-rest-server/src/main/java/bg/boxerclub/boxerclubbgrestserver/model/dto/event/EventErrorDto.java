package bg.boxerclub.boxerclubbgrestserver.model.dto.event;

public class EventErrorDto {
    private final Long id;
    private final String description;

    public EventErrorDto(Long id, String description) {
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
