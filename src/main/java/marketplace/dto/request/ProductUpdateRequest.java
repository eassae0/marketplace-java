package marketplace.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductUpdateRequest(

        @NotBlank
        @Size(min = 2, max = 40)
        String title,

        @PositiveOrZero
        @Digits(integer = 10, fraction = 2)
        BigDecimal price,

        @PositiveOrZero
        Long quantity,

        @Positive
        Long categoryId
) {
}
