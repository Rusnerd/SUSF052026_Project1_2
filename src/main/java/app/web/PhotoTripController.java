package app.web;


import app.model.entity.PhotoTrip;
import app.model.enums.UserRole;
import app.service.PhotoTripService;
import app.service.exceptions.InvalidTripDateRangeException;
import app.web.dto.PhotoTripCreateUpdateDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class PhotoTripController {

    private final PhotoTripService photoTripService;

    public PhotoTripController(PhotoTripService photoTripService) {
        this.photoTripService = photoTripService;
    }

    // Public browsing (guests can access)
    @GetMapping({"/", "/photo-trips"})
    public String list(Model model) {
        model.addAttribute("trips", photoTripService.listAll());
        return "photoTrips/list";
    }

    @GetMapping("/photo-trips/{id}")
    public String details(@PathVariable UUID id, Model model) {
        model.addAttribute("trip", photoTripService.getById(id));
        return "photoTrips/details";
    }

    // Host create
    @GetMapping("/host/photo-trips/new")
    public String newForm(HttpSession session, Model model) {
        requireHost(session);
        model.addAttribute("dto", new PhotoTripCreateUpdateDto());
        return "photoTrips/new";
    }

    @PostMapping("/host/photo-trips")
    public String create(
            HttpSession session,
            @Valid @ModelAttribute("dto") PhotoTripCreateUpdateDto dto,
            BindingResult bindingResult,
            Model model
    ) {
        requireHost(session);

        if (bindingResult.hasErrors()) {
            return "photoTrips/new";
        }

        try {
            photoTripService.create(dto);
            return "redirect:/host/photo-trips";
        } catch (InvalidTripDateRangeException e) {
            bindingResult.rejectValue("endDate", "endDate.range", e.getMessage());
            return "photoTrips/new";
        }
    }

    // Host list
    @GetMapping("/host/photo-trips")
    public String hostList(HttpSession session, Model model) {
        requireHost(session);
        model.addAttribute("trips", photoTripService.listAll());
        return "photoTrips/hostList";
    }

    // Host edit
    @GetMapping("/host/photo-trips/{id}/edit")
    public String editForm(HttpSession session, @PathVariable UUID id, Model model) {
        requireHost(session);
        PhotoTrip trip = photoTripService.getById(id);

        PhotoTripCreateUpdateDto dto = new PhotoTripCreateUpdateDto();
        dto.setTitle(trip.getTitle());
        dto.setDescription(trip.getDescription());
        dto.setLocation(trip.getLocation());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setMaxParticipants(trip.getMaxParticipants());
        dto.setPricePerPerson(trip.getPricePerPerson());

        model.addAttribute("id", id);
        model.addAttribute("dto", dto);
        return "photoTrips/edit";
    }

    @PutMapping("/host/photo-trips/{id}")
    public String update(
            HttpSession session,
            @PathVariable UUID id,
            @Valid @ModelAttribute("dto") PhotoTripCreateUpdateDto dto,
            BindingResult bindingResult,
            Model model
    ) {
        requireHost(session);

        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "photoTrips/edit";
        }

        try {
            photoTripService.update(id, dto);
            return "redirect:/host/photo-trips";
        } catch (InvalidTripDateRangeException e) {
            bindingResult.rejectValue("endDate", "endDate.range", e.getMessage());
            model.addAttribute("id", id);
            return "photoTrips/edit";
        }
    }

    @DeleteMapping("/host/photo-trips/{id}")
    public String delete(HttpSession session, @PathVariable UUID id) {
        requireHost(session);
        photoTripService.delete(id);
        return "redirect:/host/photo-trips";
    }

    private void requireHost(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !"HOST".equals(role.toString())) {
            throw new IllegalStateException("HOST access required. (Implement login next.)");
        }
    }
    private void requireRole(HttpSession session, UserRole requiredRole) {
        String roleStr = (String) session.getAttribute("role");
        if (roleStr == null) {
            throw new IllegalStateException("You must be logged in.");
        }
        UserRole userRole = UserRole.valueOf(roleStr);
        if (userRole != requiredRole && userRole != UserRole.HOST) { // HOST can do almost everything
            throw new IllegalStateException("Access denied. Required: " + requiredRole);
        }
    }
    // PhotoTripController:
    //@GetMapping("/host/photo-trips/new")
    //public String newForm(HttpSession session, Model model) {
    //    requireRole(session, UserRole.HOST);
    //}
}

