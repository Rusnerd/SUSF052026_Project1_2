package app.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Entity
@Table(name = "itinerary_items")
public class ItineraryItem {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private PhotoTrip photoTrip; // or make nullable for different trip types

    private int dayNumber;
    private String activity;
    // ...
}