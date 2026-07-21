package marketplace.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.UserCreateRequest;
import marketplace.dto.request.UserUpdateRequest;
import marketplace.dto.response.UserResponse;
import marketplace.entity.User;
import marketplace.mapper.UserMapper;
import marketplace.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        User savedUser = userService.create(request);

        return new ResponseEntity<>(UserMapper.toResponse(savedUser), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        List<User> users = userService.getAll();

        List<UserResponse> responseUsers = users.stream()
                .map(UserMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseUsers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                       @Valid @RequestBody UserUpdateRequest request) {
        User user = userService.update(id, request);
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

}
