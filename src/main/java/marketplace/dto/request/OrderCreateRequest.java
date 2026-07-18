package marketplace.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderCreateRequest(

        @NotNull
        @Positive
        Long userId,

        @NotEmpty
        List<OrderProductRequest> products

) {
}
