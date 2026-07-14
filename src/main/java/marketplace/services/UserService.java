package marketplace.services;

import lombok.RequiredArgsConstructor;
import marketplace.entity.User;
import marketplace.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public void registerUser(User user) {
        if (user != null){
            userRepository.save(user);
        }
    }

    public void deleteUser(User user) {
        if (user != null){
            userRepository.delete(user);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).
                orElseThrow(() -> new RuntimeException("User not existed!"));
    }


}
