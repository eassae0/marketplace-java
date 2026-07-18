package marketplace.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderProductRequest(

        @NotNull
        @Positive
        Long productId,

        @NotNull
        @Positive
        Long quantity
) {
}
