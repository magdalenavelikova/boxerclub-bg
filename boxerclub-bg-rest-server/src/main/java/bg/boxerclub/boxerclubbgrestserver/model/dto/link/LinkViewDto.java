package bg.boxerclub.boxerclubbgrestserver.model.dto.link;

public class LinkViewDto {
    private Long id;
    private String type;
    private String title;
    private String description;
    private String urlLink;

    public LinkViewDto() {
    }

    public Long getId() {
        return id;
    }

    public LinkViewDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public LinkViewDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public LinkViewDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LinkViewDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public LinkViewDto setUrlLink(String urlLink) {
        this.urlLink = urlLink;
        return this;
    }
}
