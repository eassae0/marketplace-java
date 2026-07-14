package marketplace.mapper;

import marketplace.dto.request.UserCreateRequest;
import marketplace.dto.request.UserUpdateRequest;
import marketplace.dto.response.UserResponse;
import marketplace.entity.User;
import marketplace.entity.enums.Role;

public final class UserMapper {
    private UserMapper() {}

    public static User toEntity(UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setRole(Role.USER);
        return user;
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
