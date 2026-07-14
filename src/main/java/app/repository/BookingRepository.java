package app.repository;

import app.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByTravelerId(UUID travelerId);
    // Optional: List<Booking> findByTripId(UUID tripId);
}