package marketplace.dto.response;

import marketplace.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(

        Long id,
        UserResponse user,
        LocalDateTime createdAt,
        List<OrderProductResponse> items,
        BigDecimal totalPrice,
        OrderStatus status
) {
}
