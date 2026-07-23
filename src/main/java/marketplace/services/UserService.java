package marketplace.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.UserCreateRequest;
import marketplace.dto.request.UserUpdateRequest;
import marketplace.entity.User;
import marketplace.entity.enums.Role;
import marketplace.exceptions.UserAlreadyExistsException;
import marketplace.exceptions.UserNotFoundException;
import marketplace.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException(request.username());
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
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
            throw new UserAlreadyExistsException(request.username());
        }
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        return user;
    }
}
