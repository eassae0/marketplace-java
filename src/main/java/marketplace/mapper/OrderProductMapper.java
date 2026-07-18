package marketplace.mapper;

import marketplace.dto.response.OrderProductResponse;
import marketplace.entity.OrderProduct;

public final class OrderProductMapper {
    private OrderProductMapper() {}

    public static OrderProductResponse toResponse(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getProduct().getId(),
                orderProduct.getProduct().getTitle(),
                orderProduct.getPurchasePrice(),
                orderProduct.getQuantity()
        );
    }
}
