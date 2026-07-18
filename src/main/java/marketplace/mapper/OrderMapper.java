package marketplace.mapper;

import marketplace.dto.response.OrderProductResponse;
import marketplace.dto.response.OrderResponse;
import marketplace.entity.Order;
import marketplace.entity.OrderProduct;

import java.util.List;

public final class OrderMapper {
    private OrderMapper() {}

    public static OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                UserMapper.toResponse(order.getUser()),
                order.getCreatedAt(),
                order.getItems().stream()
                        .map(OrderProductMapper::toResponse)
                        .toList(),
                order.getTotalPrice(),
                order.getStatus()
        );
    }

}
