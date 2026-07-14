package app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.Id;

import java.util.UUID;

// app/model/entity/SeaTrip.java
@Entity
@Table(name = "sea_trips")
public class SeaTrip {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "char(36)") private UUID id;
    // ... common fields + sea-specific (e.g. marineActivities, destinationPort)
    // getters, constructor, updateFrom(...)
}