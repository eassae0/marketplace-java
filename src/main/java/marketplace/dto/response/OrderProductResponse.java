package marketplace.dto.response;

import java.math.BigDecimal;

public record OrderProductResponse(
        Long productId,
        String title,
        BigDecimal purchasePrice,
        Long quantity
) {
}
