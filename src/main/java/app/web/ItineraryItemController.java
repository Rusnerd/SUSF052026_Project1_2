package app.web;


import app.model.entity.ItineraryItem;
import app.service.ItineraryItemService;
import app.model.dto.ItineraryItemDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/itinerary")
public class ItineraryItemController {

    private final ItineraryItemService itemService;

    public ItineraryItemController(ItineraryItemService itemService) {
        this.itemService = itemService;
    }

    // Guide: List items for a trip
    @GetMapping("/trip/{tripId}")
    public String listForTrip(@PathVariable UUID tripId, HttpSession session, Model model) {
        requireGuideOrHost(session);
        model.addAttribute("items", itemService.findByTrip(tripId));
        model.addAttribute("tripId", tripId);

        return "itinerary/itineraryList";
    }

    // Create form
    @GetMapping("/new/{tripId}")
    public String newForm(@PathVariable UUID tripId, HttpSession session, Model model) {
        requireGuideOrHost(session);
        model.addAttribute("tripId", tripId);
        model.addAttribute("dto", new ItineraryItemDto());

        return "itinerary/newItinerary";
    }

    @PostMapping
    public String create(@RequestParam UUID tripId,
                         @Valid @ModelAttribute("dto") ItineraryItemDto dto,
                         BindingResult bindingResult,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

        requireGuideOrHost(session);

        if (bindingResult.hasErrors()) {
            return "itinerary/new";
        }

        try {
            itemService.createItem(dto, tripId);
            redirectAttributes.addFlashAttribute("success", "Itinerary item added!");
            return "redirect:/itinerary/trip/" + tripId;
        } catch (Exception e) {
            bindingResult.rejectValue("activityTitle", "error.item", e.getMessage());
            return "itinerary/new";
        }
    }

    @DeleteMapping("/{itemId}")
    public String delete(@PathVariable UUID itemId,
                         @RequestParam UUID tripId,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        requireGuideOrHost(session);
        itemService.deleteItem(itemId, tripId);
        redirectAttributes.addFlashAttribute("success", "Item deleted.");
        return "redirect:/itinerary/trip/" + tripId;
    }

    private void requireGuideOrHost(HttpSession session) {
        String roleStr = (String) session.getAttribute("role");
        if (roleStr == null) {
            throw new IllegalStateException("Login required");
        }
        // GUIDE or HOST allowed
    }

}