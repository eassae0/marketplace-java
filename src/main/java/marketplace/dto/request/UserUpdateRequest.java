package marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @NotBlank
        @Size(min = 2, max = 30)
        String username,

        @NotBlank
        @Size(min = 8, max = 30)
        String password
) {
}
