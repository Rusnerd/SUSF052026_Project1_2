package app.web;

import app.model.entity.MountainTrip;
import app.service.MountainTripService;
import app.service.exceptions.InvalidTripDateRangeException;
import app.web.dto.MountainTripCreateUpdateDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class MountainTripController {

    private final MountainTripService mountainTripService;

    public MountainTripController(MountainTripService mountainTripService) {
        this.mountainTripService = mountainTripService;
    }

    @GetMapping("/mountain-trips")
    public String list(Model model) {
        model.addAttribute("trips", mountainTripService.listAll());
        return "mountainTrips/list";
    }

    @GetMapping("/mountain-trips/{id}")
    public String details(@PathVariable UUID id, Model model) {
        model.addAttribute("trip", mountainTripService.getById(id));
        return "mountainTrips/details";
    }

    // Host CRUD (same pattern)
    @GetMapping("/host/mountain-trips/new")
    public String newForm(HttpSession session, Model model) {
        requireHost(session);
        model.addAttribute("dto", new MountainTripCreateUpdateDto());
        return "mountainTrips/new";
    }

    @PostMapping("/host/mountain-trips")
    public String create(HttpSession session,
                         @Valid @ModelAttribute("dto") MountainTripCreateUpdateDto dto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        requireHost(session);
        if (bindingResult.hasErrors()) return "mountainTrips/new";

        try {
            mountainTripService.create(dto);
            redirectAttributes.addFlashAttribute("success", "Mountain Trip created!");
            return "redirect:/host/mountain-trips";
        } catch (InvalidTripDateRangeException e) {
            bindingResult.rejectValue("endDate", "error.date", e.getMessage());
            return "mountainTrips/new";
        }
    }

    // Add edit, delete, hostList similarly (copy from SeaTripController)
    // ...

    private void requireHost(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !"HOST".equals(role)) {
            throw new IllegalStateException("Host access required");
        }
    }
}