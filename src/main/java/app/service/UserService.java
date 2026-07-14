package app.service;

import app.model.entity.User;
import app.model.enums.UserRole;
import app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String email, String rawPassword, UserRole role) {
        if (userRepository.findByUsername(username).isPresent() ||
                userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Username or email already exists");
        }

        User user = new User(username, email, passwordEncoder.encode(rawPassword), role);
        return userRepository.save(user);
    }

    public Optional<User> login(String username, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }
}