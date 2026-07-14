package app.web;

import app.model.entity.SeaTrip;
import app.service.SeaTripService;
import app.service.exceptions.InvalidTripDateRangeException;
import app.web.dto.SeaTripCreateUpdateDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class SeaTripController {

    private final SeaTripService seaTripService;

    public SeaTripController(SeaTripService seaTripService) {
        this.seaTripService = seaTripService;
    }

    // Public
    @GetMapping("/sea-trips")
    public String list(Model model) {
        model.addAttribute("trips", seaTripService.listAll());
        return "seaTrips/list";
    }

    @GetMapping("/sea-trips/{id}")
    public String details(@PathVariable UUID id, Model model) {
        model.addAttribute("trip", seaTripService.getById(id));
        return "seaTrips/details";
    }

    // Host CRUD
    @GetMapping("/host/sea-trips/new")
    public String newForm(HttpSession session, Model model) {
        requireHost(session);
        model.addAttribute("dto", new SeaTripCreateUpdateDto());
        return "seaTrips/new";
    }

    @PostMapping("/host/sea-trips")
    public String create(HttpSession session,
                         @Valid @ModelAttribute("dto") SeaTripCreateUpdateDto dto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        requireHost(session);
        if (bindingResult.hasErrors()) {
            return "seaTrips/new";
        }
        try {
            seaTripService.create(dto);
            redirectAttributes.addFlashAttribute("success", "Sea Trip created successfully!");
            return "redirect:/host/sea-trips";
        } catch (InvalidTripDateRangeException e) {
            bindingResult.rejectValue("endDate", "error.date", e.getMessage());
            return "seaTrips/new";
        }
    }

    @GetMapping("/host/sea-trips")
    public String hostList(HttpSession session, Model model) {
        requireHost(session);
        model.addAttribute("trips", seaTripService.listAll());
        return "seaTrips/hostList";
    }

    @GetMapping("/host/sea-trips/{id}/edit")
    public String editForm(HttpSession session, @PathVariable UUID id, Model model) {
        requireHost(session);
        SeaTrip trip = seaTripService.getById(id);

        SeaTripCreateUpdateDto dto = new SeaTripCreateUpdateDto();
        dto.setTitle(trip.getTitle());
        dto.setDescription(trip.getDescription());
        dto.setLocation(trip.getLocation());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setMaxParticipants(trip.getMaxParticipants());
        dto.setPricePerPerson(trip.getPricePerPerson());
        dto.setMarineActivities(trip.getMarineActivities());
        dto.setDestinationPort(trip.getDestinationPort());

        model.addAttribute("id", id);
        model.addAttribute("dto", dto);
        return "seaTrips/edit";
    }

    @PutMapping("/host/sea-trips/{id}")
    public String update(HttpSession session,
                         @PathVariable UUID id,
                         @Valid @ModelAttribute("dto") SeaTripCreateUpdateDto dto,
                         BindingResult bindingResult,
                         Model model) {   // ← FIXED: added Model model
        requireHost(session);
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "seaTrips/edit";
        }
        try {
            seaTripService.update(id, dto);
            return "redirect:/host/sea-trips";
        } catch (InvalidTripDateRangeException e) {
            bindingResult.rejectValue("endDate", "error.date", e.getMessage());
            model.addAttribute("id", id);
            return "seaTrips/edit";
        }
    }

    @DeleteMapping("/host/sea-trips/{id}")
    public String delete(HttpSession session, @PathVariable UUID id) {
        requireHost(session);
        seaTripService.delete(id);
        return "redirect:/host/sea-trips";
    }

    private void requireHost(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !"HOST".equals(role)) {
            throw new IllegalStateException("Host access required");
        }
    }
}