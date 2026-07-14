package app.service;

import app.model.entity.Booking;
import app.model.entity.PhotoTrip;
import app.repository.BookingRepository;
import app.repository.PhotoTripRepository;
import app.web.dto.BookingCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PhotoTripRepository photoTripRepository;

    public BookingService(BookingRepository bookingRepository,
                          PhotoTripRepository photoTripRepository) {
        this.bookingRepository = bookingRepository;
        this.photoTripRepository = photoTripRepository;
    }

    public Booking createBooking(BookingCreateDto dto, UUID travelerId) {
        PhotoTrip trip = photoTripRepository.findById(dto.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        // Business validation
        if (dto.getNumberOfPeople() < 1) {
            throw new RuntimeException("At least 1 person required");
        }
        if (dto.getNumberOfPeople() > trip.getMaxParticipants()) {
            throw new RuntimeException("Not enough available spots");
        }

        Booking booking = new Booking(trip, travelerId, dto.getNumberOfPeople(), dto.getNotes());
        return bookingRepository.save(booking);
    }

    public void cancelBooking(UUID bookingId, UUID travelerId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getTravelerId().equals(travelerId)) {
            throw new RuntimeException("You can only cancel your own bookings");
        }

        if ("CANCELLED".equals(booking.getStatus())) {
            throw new RuntimeException("Booking already cancelled");
        }

        booking.cancel();
        bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public List<Booking> findByTraveler(UUID travelerId) {
        return bookingRepository.findByTravelerId(travelerId);
    }
}