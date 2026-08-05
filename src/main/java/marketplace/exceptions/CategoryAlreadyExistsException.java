package marketplace.exceptions;

public class CategoryAlreadyExistsException extends AlreadyExistsException {
    public CategoryAlreadyExistsException(String name) {
        super("Category with name = " + name + " already exists");
    }
}
