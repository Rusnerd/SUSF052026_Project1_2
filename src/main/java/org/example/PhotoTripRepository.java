package app.repository;

import app.model.entity.PhotoTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface PhotoTripRepository extends JpaRepository<PhotoTrip, UUID> {

}