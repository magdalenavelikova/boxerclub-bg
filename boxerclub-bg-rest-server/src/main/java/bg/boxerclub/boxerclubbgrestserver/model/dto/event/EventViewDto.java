package bg.boxerclub.boxerclubbgrestserver.model.dto.event;

import bg.boxerclub.boxerclubbgrestserver.model.enums.Location;

import java.time.LocalDate;

public class EventViewDto {
    private Long id;

    private String title;

    private String description;


    private LocalDate startDate;

    private LocalDate expiryDate;
    private String urlLink;
    private Location location;

    public EventViewDto() {
    }

    public Long getId() {
        return id;
    }

    public EventViewDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public EventViewDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventViewDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public EventViewDto setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public EventViewDto setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public EventViewDto setUrlLink(String urlLink) {
        this.urlLink = urlLink;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public EventViewDto setLocation(Location location) {
        this.location = location;
        return this;
    }
}
