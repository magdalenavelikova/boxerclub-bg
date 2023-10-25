package bg.boxerclub.boxerclubbgrestserver.model.dto.link;

import jakarta.validation.constraints.NotEmpty;

public class AddLinkDto {
    @NotEmpty
    private String type;
    @NotEmpty
    private String title;
    private String description;
    @NotEmpty
    private String urlLink;

    public AddLinkDto() {
    }

    public String getType() {
        return type;
    }

    public AddLinkDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AddLinkDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddLinkDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public AddLinkDto setUrlLink(String urlLink) {
        this.urlLink = urlLink;
        return this;
    }
}
