package marketplace.exceptions;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(Long id) {
        super("Order with ID: " + id + " not found!");
    }
}
