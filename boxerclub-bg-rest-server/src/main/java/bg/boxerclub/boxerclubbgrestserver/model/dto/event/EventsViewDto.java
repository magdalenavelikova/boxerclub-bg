package bg.boxerclub.boxerclubbgrestserver.model.dto.event;

import java.util.ArrayList;
import java.util.List;

public class EventsViewDto {
    private List<EventViewDto> upcomingBg;
    private List<EventViewDto> passedBg;
    private List<EventViewDto> upcomingInt;
    private List<EventViewDto> passedInt;

    public EventsViewDto() {
        this.upcomingBg = new ArrayList<>();
        this.passedBg = new ArrayList<>();
        this.upcomingInt = new ArrayList<>();
        this.passedInt = new ArrayList<>();
    }

    public List<EventViewDto> getUpcomingBg() {
        return upcomingBg;
    }

    public EventsViewDto setUpcomingBg(List<EventViewDto> upcomingBg) {
        this.upcomingBg = upcomingBg;
        return this;
    }

    public List<EventViewDto> getPassedBg() {
        return passedBg;
    }

    public EventsViewDto setPassedBg(List<EventViewDto> passedBg) {
        this.passedBg = passedBg;
        return this;
    }

    public List<EventViewDto> getUpcomingInt() {
        return upcomingInt;
    }

    public EventsViewDto setUpcomingInt(List<EventViewDto> upcomingInt) {
        this.upcomingInt = upcomingInt;
        return this;
    }

    public List<EventViewDto> getPassedInt() {
        return passedInt;
    }

    public EventsViewDto setPassedInt(List<EventViewDto> passedInt) {
        this.passedInt = passedInt;
        return this;
    }
}
