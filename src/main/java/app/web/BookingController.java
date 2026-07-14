package app.web;

import app.model.entity.Booking;
import app.service.BookingService;
import app.web.dto.BookingCreateDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // My Bookings (for logged users)
    @GetMapping("/my")
    public String myBookings(HttpSession session, Model model) {
        requireLogin(session); // at least logged in
        UUID userId = (UUID) session.getAttribute("userId");
        model.addAttribute("bookings", bookingService.findByTraveler(userId));
        return "bookings/my-bookings";
    }

    // Create booking form (from trip details page)
    @GetMapping("/new/{tripId}")
    public String newBookingForm(@PathVariable UUID tripId, HttpSession session, Model model) {
        requireLogin(session);
        model.addAttribute("tripId", tripId);
        model.addAttribute("dto", new BookingCreateDto());
        return "bookings/newBooking";
    }

    @PostMapping
    public String createBooking(HttpSession session,
                                @Valid @ModelAttribute("dto") BookingCreateDto dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        requireLogin(session);

        if (bindingResult.hasErrors()) {
            return "bookings/new";
        }

        try {
            UUID userId = (UUID) session.getAttribute("userId");
            Booking booking = bookingService.createBooking(dto, userId);
            redirectAttributes.addFlashAttribute("success",
                    "Booking created successfully! Reference: " + booking.getId());
            return "redirect:/bookings/my";
        } catch (Exception e) {
            bindingResult.rejectValue("numberOfPeople", "error.booking", e.getMessage());
            return "bookings/new";
        }
    }

    // Cancel booking
    @DeleteMapping("/{id}")
    public String cancelBooking(@PathVariable UUID id, HttpSession session, RedirectAttributes redirectAttributes) {
        requireLogin(session);
        UUID userId = (UUID) session.getAttribute("userId");

        bookingService.cancelBooking(id, userId);
        redirectAttributes.addFlashAttribute("success", "Booking cancelled.");
        return "redirect:/bookings/my";
    }

    private void requireLogin(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            throw new IllegalStateException("Please login first.");
        }
    }


}