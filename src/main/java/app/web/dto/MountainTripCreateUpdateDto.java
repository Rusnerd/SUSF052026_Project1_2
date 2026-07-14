package app.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MountainTripCreateUpdateDto {

    @NotBlank @Size(max = 120)
    private String title;

    @NotBlank @Size(max = 1000)
    private String description;

    @NotBlank @Size(max = 120)
    private String location;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull @Min(1)
    private Integer maxParticipants;

    @NotNull @DecimalMin("0.00")
    private BigDecimal pricePerPerson;

    @Size(max = 50)
    private String difficulty;

    @Size(max = 500)
    private String hikingRoutes;

    // Getters + Setters (add all)
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }
    public BigDecimal getPricePerPerson() { return pricePerPerson; }
    public void setPricePerPerson(BigDecimal pricePerPerson) { this.pricePerPerson = pricePerPerson; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getHikingRoutes() { return hikingRoutes; }
    public void setHikingRoutes(String hikingRoutes) { this.hikingRoutes = hikingRoutes; }
}