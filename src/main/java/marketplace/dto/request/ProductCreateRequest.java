package marketplace.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreateRequest(

        @NotBlank
        @Size(min = 2, max = 70)
        String title,

        @NotNull
        @Positive
        @Digits(integer = 10, fraction = 2)
        BigDecimal price,

        @NotNull
        @PositiveOrZero
        Long quantity,

        @NotNull
        @Positive
        Long categoryId
) {
}
