package app.model.entity;

import jakarta.persistence.*;
        import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "itinerary_items")
public class ItineraryItem {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "char(36)", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private PhotoTrip trip;   // Link to PhotoTrip (expand later for other trip types)

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(nullable = false, length = 200)
    private String activityTitle;

    @Column(length = 500)
    private String location;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(length = 1000)
    private String notes;

    private LocalDateTime createdAt = LocalDateTime.now();

    protected ItineraryItem() {}

    public ItineraryItem(PhotoTrip trip, Integer dayNumber, String activityTitle,
                         String location, LocalDateTime startTime, LocalDateTime endTime, String notes) {
        this.trip = trip;
        this.dayNumber = dayNumber;
        this.activityTitle = activityTitle;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
    }

    // Getters
    public UUID getId() { return id; }
    public PhotoTrip getTrip() { return trip; }
    public Integer getDayNumber() { return dayNumber; }
    public String getActivityTitle() { return activityTitle; }
    public String getLocation() { return location; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getNotes() { return notes; }
}