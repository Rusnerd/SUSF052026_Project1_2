package app.repository;

import app.model.entity.MountainTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MountainTripRepository extends JpaRepository<MountainTrip, UUID> {
}