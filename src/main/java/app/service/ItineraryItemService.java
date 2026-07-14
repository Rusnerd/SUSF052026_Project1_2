package app.service;


import app.model.entity.ItineraryItem;
import app.model.entity.PhotoTrip;
import app.repository.ItineraryItemRepository;
import app.repository.PhotoTripRepository;
import app.model.dto.ItineraryItemDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ItineraryItemService {

    private final ItineraryItemRepository itemRepository;
    private final PhotoTripRepository tripRepository;

    public ItineraryItemService(ItineraryItemRepository itemRepository,
                                PhotoTripRepository tripRepository) {
        this.itemRepository = itemRepository;
        this.tripRepository = tripRepository;
    }

    public ItineraryItem createItem(ItineraryItemDto dto, UUID tripId) {
        PhotoTrip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        ItineraryItem item = new ItineraryItem(
                trip,
                dto.getDayNumber(),
                dto.getActivityTitle(),
                dto.getLocation(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getNotes()
        );

        return itemRepository.save(item);
    }

    public void deleteItem(UUID itemId, UUID tripId) {
        ItineraryItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getTrip().getId().equals(tripId)) {
            throw new RuntimeException("Item does not belong to this trip");
        }

        itemRepository.delete(item);
    }

    @Transactional(readOnly = true)
    public List<ItineraryItem> findByTrip(UUID tripId) {
        return itemRepository.findByTripIdOrderByDayNumberAsc(tripId);
    }
}