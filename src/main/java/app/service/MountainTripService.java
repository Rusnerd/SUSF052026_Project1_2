package app.service;

import app.model.entity.MountainTrip;
import app.model.entity.SeaTrip;
import app.repository.SeaTripRepository;
import app.service.exceptions.InvalidTripDateRangeException;
import app.service.exceptions.SeaTripNotFoundException;
import app.web.dto.MountainTripCreateUpdateDto;
import app.web.dto.SeaTripCreateUpdateDto;
import jakarta.validation.Valid;
import jdk.internal.org.jline.reader.History;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class MountainTripService {
    private final SeaTripRepository photoTripRepository;
    private History mountainTripRepository;

    public MountainTripService(SeaTripRepository photoTripRepository) {
        this.photoTripRepository = photoTripRepository;
    }

    @Transactional(readOnly = true)
    public List<MountainTrip> listAll() {
        return mountainTripRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MountainTrip getById(UUID id) {
        return mountainTripRepository.findById(id).orElseThrow(() -> new SeaTripNotFoundException(id));
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
        return mountainTripRepository.save(trip);
    }

    public MountainTrip update(UUID id, MountainTripCreateUpdateDto dto) {
        validateBusiness(dto);

        MountainTrip existing = getById(id);
        MountainTrip updated = new MountainTrip(
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
        MountainTrip existing = getById(id);
        mountainTripRepository.delete(existing);
    }

    private void validateBusiness(MountainTripCreateUpdateDto dto) {
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidTripDateRangeException("End date must be on/after start date.");
        }
    }

    public void create(@Valid MountainTripCreateUpdateDto dto) {
    }
}
