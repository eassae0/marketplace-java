package marketplace.dto.response;

import marketplace.entity.enums.Role;

public record UserResponse(
        Long id,
        String username,
        Role role
) {}
