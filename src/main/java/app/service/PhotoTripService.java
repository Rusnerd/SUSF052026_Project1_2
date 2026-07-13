package app.service;


import app.model.entity.PhotoTrip;
import app.repository.PhotoTripRepository;
import app.service.exceptions.InvalidTripDateRangeException;
import app.service.exceptions.PhotoTripNotFoundException;
import app.web.dto.PhotoTripCreateUpdateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PhotoTripService {

    private final PhotoTripRepository photoTripRepository;

    public PhotoTripService(PhotoTripRepository photoTripRepository) {
        this.photoTripRepository = photoTripRepository;
    }

    @Transactional(readOnly = true)
    public List<PhotoTrip> listAll() {
        return photoTripRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PhotoTrip getById(UUID id) {
        return photoTripRepository.findById(id).orElseThrow(() -> new PhotoTripNotFoundException(id));
    }

    public PhotoTrip create(PhotoTripCreateUpdateDto dto) {
        validateBusiness(dto);

        PhotoTrip trip = new PhotoTrip(
                dto.getTitle(),
                dto.getDescription(),
                dto.getLocation(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getMaxParticipants(),
                dto.getPricePerPerson()
        );
        return photoTripRepository.save(trip);
    }

    public PhotoTrip update(UUID id, PhotoTripCreateUpdateDto dto) {
        validateBusiness(dto);

        PhotoTrip existing = getById(id);
        PhotoTrip updated = new PhotoTrip(
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
        PhotoTrip existing = getById(id);
        photoTripRepository.delete(existing);
    }

    private void validateBusiness(PhotoTripCreateUpdateDto dto) {
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidTripDateRangeException("End date must be on/after start date.");
        }
    }
}
