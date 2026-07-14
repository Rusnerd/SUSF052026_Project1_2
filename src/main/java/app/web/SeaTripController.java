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

import java.util.UUID;

@Controller
public class SeaTripController {

    private final SeaTripService seaTripService;

    public SeaTripController(SeaTripService seaTripService) {
        this.seaTripService = seaTripService;
    }

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

    // Host area
    @GetMapping("/host/sea-trips/new")
    public String newForm(HttpSession session, Model model) {
        requireHost(session);
        model.addAttribute("dto", new SeaTripCreateUpdateDto());
        return "seaTrips/new";
    }

    @PostMapping("/host/sea-trips")
    public String create(HttpSession session,
                         @Valid @ModelAttribute("dto") SeaTripCreateUpdateDto dto,
                         BindingResult bindingResult) {
        requireHost(session);
        if (bindingResult.hasErrors()) return "seaTrips/new";

        try {
            seaTripService.create(dto);
            return "redirect:/host/sea-trips";
        } catch (InvalidTripDateRangeException e) {
            bindingResult.rejectValue("endDate", "error.date", e.getMessage());
            return "seaTrips/new";
        }
    }

    // Similar edit + delete methods as PhotoTripController...
    // (I can provide them if needed)
}