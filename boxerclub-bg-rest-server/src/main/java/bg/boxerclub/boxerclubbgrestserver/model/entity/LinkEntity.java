package bg.boxerclub.boxerclubbgrestserver.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "links")
public class LinkEntity extends BaseEntity {
    //todo ask for enumeration for type
    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String title;

    private String description;
    @Column(nullable = false)
    private String urlLink;

    public String getTitle() {
        return title;
    }

    public LinkEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LinkEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public LinkEntity setUrlLink(String urlLink) {
        this.urlLink = urlLink;
        return this;
    }

    public String getType() {
        return type;
    }

    public LinkEntity setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkEntity that)) return false;
        return Objects.equals(type, that.type) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(urlLink, that.urlLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, title, description, urlLink);
    }
}