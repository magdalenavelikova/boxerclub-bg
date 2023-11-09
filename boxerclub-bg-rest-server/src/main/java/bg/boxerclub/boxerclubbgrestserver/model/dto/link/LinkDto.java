package bg.boxerclub.boxerclubbgrestserver.model.dto.link;

import jakarta.validation.constraints.NotEmpty;

public class LinkDto {
    private Long id;
    @NotEmpty(message = "Field can not be empty")
    private String type;
    @NotEmpty(message = "Field can not be empty")
    private String title;
    private String description;
    @NotEmpty(message = "Field can not be empty")
    private String urlLink;

    public LinkDto() {
    }

    public Long getId() {
        return id;
    }

    public LinkDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public LinkDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public LinkDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LinkDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public LinkDto setUrlLink(String urlLink) {
        this.urlLink = urlLink;
        return this;
    }
}
