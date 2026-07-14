package app.web;

import app.model.dto.UserRegistrationDto;
import app.model.entity.User;
import app.model.enums.UserRole;
import app.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegistrationDto dto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            User user = userService.register(dto.getUsername(), dto.getEmail(),
                    dto.getPassword(), UserRole.TRAVELER);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            bindingResult.rejectValue("username", "error.user", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        Optional<User> userOpt = userService.login(username, password);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole().name());

            redirectAttributes.addFlashAttribute("success", "Welcome, " + user.getUsername() + "!");
            return "redirect:/"; // or /photo-trips
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "You have been logged out.");
        return "redirect:/auth/login";
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
}