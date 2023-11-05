package bg.boxerclub.boxerclubbgrestserver.model.entity;

import bg.boxerclub.boxerclubbgrestserver.model.enums.Location;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "events")

public class EventEntity extends BaseEntity {
    @Column(nullable = false)
    private String title;


    @Column(nullable = false, length = 2000)
    private String description;
    @Column(nullable = false)
    private String urlLink;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Location location;


    public String getTitle() {
        return title;
    }

    public EventEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public EventEntity setUrlLink(String urlLink) {
        this.urlLink = urlLink;
        return this;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public EventEntity setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public EventEntity setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public EventEntity setLocation(Location location) {
        this.location = location;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventEntity that)) return false;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(urlLink, that.urlLink) && Objects.equals(startDate, that.startDate) && Objects.equals(expiryDate, that.expiryDate) && location == that.location;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, urlLink, startDate, expiryDate, location);
    }
}
