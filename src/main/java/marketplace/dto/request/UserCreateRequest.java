package marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank
        @Size(min = 2, max = 30)
        String username,

        @NotBlank
        @Size(min = 6, max = 30)
        String password
) {}
