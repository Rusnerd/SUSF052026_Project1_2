package app.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SeaTripCreateUpdateDto {

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

    @Size(max = 500)
    private String marineActivities;

    @Size(max = 200)
    private String destinationPort;

    // Getters and Setters (generate or write them)
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
    public String getMarineActivities() { return marineActivities; }
    public void setMarineActivities(String marineActivities) { this.marineActivities = marineActivities; }
    public String getDestinationPort() { return destinationPort; }
    public void setDestinationPort(String destinationPort) { this.destinationPort = destinationPort; }
}