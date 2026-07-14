package marketplace.dto.request;

public record UserUpdateRequest(
        String username,
        String password
) {
}
