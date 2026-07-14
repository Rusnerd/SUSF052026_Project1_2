package app.service;

import app.model.entity.MountainTrip;
import app.model.entity.SeaTrip;
import app.repository.SeaTripRepository;
import app.service.exceptions.InvalidTripDateRangeException;
import app.service.exceptions.SeaTripNotFoundException;
import app.web.dto.SeaTripCreateUpdateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class SeaTripService {

    private final SeaTripRepository photoTripRepository;

    public SeaTripService(SeaTripRepository seaTripRepository) {
        this.seaTripRepository = seaTripRepository;
    }

    @Transactional(readOnly = true)
    public List<SeaTrip> listAll() {
        return seaTripRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SeaTrip getById(UUID id) {
        return photoTripRepository.findById(id).orElseThrow(() -> new SeaTripNotFoundException(id));
    }

    public SeaTrip create(SeaTripCreateUpdateDto dto) {
        validateBusiness(dto);

        SeaTrip trip = new SeaTrip(
                dto.getTitle(),
                dto.getDescription(),
                dto.getLocation(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getMaxParticipants(),
                dto.getPricePerPerson()
        );
        return seaTripRepository.save(trip);
    }

    public SeaTrip update(UUID id, SeaTripCreateUpdateDto dto) {
        validateBusiness(dto);

        SeaTrip existing = getById(id);
        SeaTrip updated = new SeaTrip(
                dto.getTitle(),
                dto.getDescription(),
                dto.getLocation(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getMaxParticipants(),
                dto.getPricePerPerson()
        );
        existing.updateFrom(updated);
        return existing;
    }

    public void delete(UUID id) {
        SeaTrip existing = getById(id);
        seaTripRepository.delete(existing);
    }

    private void validateBusiness(SeaTripCreateUpdateDto dto) {
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidTripDateRangeException("End date must be on/after start date.");
        }
    }
}
