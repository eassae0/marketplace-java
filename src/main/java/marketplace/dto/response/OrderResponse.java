package marketplace.dto.response;

import marketplace.entity.enums.OrderStatusType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(

        Long id,
        UserResponse user,
        LocalDateTime createdAt,
        List<OrderProductResponse> items,
        BigDecimal totalPrice,
        OrderStatusType status
) {
}
