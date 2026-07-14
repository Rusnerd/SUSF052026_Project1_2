package app.model.dto;

import jakarta.validation.constraints.*;
        import java.time.LocalDateTime;

public class ItineraryItemDto {

    @NotNull @Min(1)
    private Integer dayNumber;

    @NotBlank @Size(max = 200)
    private String activityTitle;

    @Size(max = 200)
    private String location;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Size(max = 1000)
    private String notes;

    // getters + setters
    public Integer getDayNumber() { return dayNumber; }
    public void setDayNumber(Integer dayNumber) { this.dayNumber = dayNumber; }
    public String getActivityTitle() { return activityTitle; }
    public void setActivityTitle(String activityTitle) { this.activityTitle = activityTitle; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}