package app.repository;

import app.model.entity.SeaTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SeaTripRepository extends JpaRepository<SeaTrip, UUID> {
}