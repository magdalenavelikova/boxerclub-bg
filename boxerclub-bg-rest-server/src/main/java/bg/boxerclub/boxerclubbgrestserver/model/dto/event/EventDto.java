package bg.boxerclub.boxerclubbgrestserver.model.dto.event;

import bg.boxerclub.boxerclubbgrestserver.model.enums.Location;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class EventDto {

    private Long id;
    @NotEmpty(message = "Field can not be empty")
    private String title;

    @NotEmpty(message = "Field can not be empty")
    private String description;
    @NotEmpty(message = "Field can not be empty")
    private String urlLink;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
    @NotNull(message = "Field can not be empty")
    private Location location;

    public EventDto() {
    }

    public Long getId() {
        return id;
    }

    public EventDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public EventDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public EventDto setUrlLink(String urlLink) {
        this.urlLink = urlLink;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public EventDto setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public EventDto setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public EventDto setLocation(Location location) {
        this.location = location;
        return this;
    }
}
