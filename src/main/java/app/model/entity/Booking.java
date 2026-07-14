package app.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "char(36)", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private PhotoTrip trip;   // You can change to a base Trip later

    @Column(name = "traveler_id", nullable = false)
    private UUID travelerId;  // Simple relation via ID (avoids full User load if not needed)

    @Column(nullable = false)
    private Integer numberOfPeople;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private String status = "CONFIRMED";  // CONFIRMED, CANCELLED, PENDING

    private LocalDateTime bookingDate = LocalDateTime.now();

    protected Booking() {}

    public Booking(PhotoTrip trip, UUID travelerId, Integer numberOfPeople, String notes) {
        this.trip = trip;
        this.travelerId = travelerId;
        this.numberOfPeople = numberOfPeople;
        this.notes = notes;
    }

    public void cancel() {
        this.status = "CANCELLED";
    }

    // Getters
    public UUID getId() { return id; }
    public PhotoTrip getTrip() { return trip; }
    public UUID getTravelerId() { return travelerId; }
    public Integer getNumberOfPeople() { return numberOfPeople; }
    public String getNotes() { return notes; }
    public String getStatus() { return status; }
    public LocalDateTime getBookingDate() { return bookingDate; }
}