package marketplace.dto.request;

public record UserCreateRequest(
        String username,
        String password
) {}
