package app.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "mountain_trips")
public class MountainTrip {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "char(36)", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 120)
    private String location;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer maxParticipants;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerPerson;

    @Column(length = 50)
    private String difficulty;   // easy, medium, hard

    @Column(length = 500)
    private String hikingRoutes;

    protected MountainTrip(String title, String description, String location, LocalDate startDate, LocalDate endDate, Integer maxParticipants, BigDecimal pricePerPerson) {}

    public MountainTrip(String title, String description, String location,
                        LocalDate startDate, LocalDate endDate,
                        Integer maxParticipants, BigDecimal pricePerPerson,
                        String difficulty, String hikingRoutes) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxParticipants = maxParticipants;
        this.pricePerPerson = pricePerPerson;
        this.difficulty = difficulty;
        this.hikingRoutes = hikingRoutes;
    }

    public void updateFrom(MountainTrip other) {
        this.title = other.title;
        this.description = other.description;
        this.location = other.location;
        this.startDate = other.startDate;
        this.endDate = other.endDate;
        this.maxParticipants = other.maxParticipants;
        this.pricePerPerson = other.pricePerPerson;
        this.difficulty = other.difficulty;
        this.hikingRoutes = other.hikingRoutes;
    }

    // Getters
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public Integer getMaxParticipants() { return maxParticipants; }
    public BigDecimal getPricePerPerson() { return pricePerPerson; }
    public String getDifficulty() { return difficulty; }
    public String getHikingRoutes() { return hikingRoutes; }
}