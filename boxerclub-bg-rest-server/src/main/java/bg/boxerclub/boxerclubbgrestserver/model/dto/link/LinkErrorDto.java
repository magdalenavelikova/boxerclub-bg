package bg.boxerclub.boxerclubbgrestserver.model.dto.link;

public class LinkErrorDto {
    private final Long linkId;
    private final String description;

    public LinkErrorDto(Long linkId, String description) {
        this.linkId = linkId;
        this.description = description;
    }

    public Long getLinkId() {
        return linkId;
    }

    public String getDescription() {
        return description;
    }
}
