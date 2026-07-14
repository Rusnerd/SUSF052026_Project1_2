package app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.Id;

import java.util.UUID;

// app/model/entity/MountainTrip.java
@Entity
@Table(name = "mountain_trips")
public class MountainTrip {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "char(36)") private UUID id;
    // ... common fields + mountain-specific (e.g. hikingTrails, elevationGain)
    // getters, constructor, updateFrom(...)
}