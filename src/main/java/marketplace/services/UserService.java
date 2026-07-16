package marketplace.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.UserUpdateRequest;
import marketplace.entity.User;
import marketplace.exceptions.UserAlreadyExistsException;
import marketplace.exceptions.UserNotFoundException;
import marketplace.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(
                    "User with username " + user.getUsername() + " already exists!");
        }
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID: " + id + " not found!"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(getById(id));
    }

    @Transactional
    public User update(Long id, UserUpdateRequest request) {
        User user = getById(id);
        if (!request.username().equals(user.getUsername())
                && userRepository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException("Username " + request.username() + " is already taken");
        }
        user.setUsername(request.username());
        user.setPassword(request.password());
        return user;
    }
}
