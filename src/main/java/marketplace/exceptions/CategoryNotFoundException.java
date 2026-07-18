package marketplace.exceptions;

public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException(Long id) {
        super("Category with id = " + id + " not found");
    }
}
