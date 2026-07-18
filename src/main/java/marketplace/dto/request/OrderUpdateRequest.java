package marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import marketplace.entity.enums.Role;

import java.util.List;

public record OrderUpdateRequest(

        @NotEmpty
        List<OrderProductRequest> products,

        @NotBlank
        Role role

) {
}
