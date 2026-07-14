package app.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "trip_id") // or specific subtype if needed
    private PhotoTrip trip; // or use a base Trip if you have inheritance

    @ManyToOne
    @JoinColumn(name = "traveler_id")
    private SecurityProperties.User traveler;

    private LocalDateTime bookingDate;
    private Integer numberOfPeople;
    private String status; // CONFIRMED, CANCELLED

    // constructors, getters
}