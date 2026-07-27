package marketplace.exceptions;

import marketplace.entity.enums.OrderStatusType;

public class IllegalOrderStateException extends RuntimeException {
    public IllegalOrderStateException(OrderStatusType status) {
        super("Cannot cancel order with status: " + status);
    }
}
