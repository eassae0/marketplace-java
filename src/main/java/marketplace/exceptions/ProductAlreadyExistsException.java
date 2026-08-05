package marketplace.exceptions;

public class ProductAlreadyExistsException extends AlreadyExistsException {
    public ProductAlreadyExistsException(String title) {
        super("Product with title = " + title + " already exists");
    }
}
