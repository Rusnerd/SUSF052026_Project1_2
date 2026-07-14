package app.web.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class BookingCreateDto {

    @NotNull
    private UUID tripId;

    @NotNull @Min(1) @Max(10)
    private Integer numberOfPeople;

    private String notes;

    // getters + setters
    public UUID getTripId() { return tripId; }
    public void setTripId(UUID tripId) { this.tripId = tripId; }
    public Integer getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}