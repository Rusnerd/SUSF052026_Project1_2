package app.repository;

import app.model.entity.ItineraryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ItineraryItemRepository extends JpaRepository<ItineraryItem, UUID> {
    List<ItineraryItem> findByTripIdOrderByDayNumberAsc(UUID tripId);
}