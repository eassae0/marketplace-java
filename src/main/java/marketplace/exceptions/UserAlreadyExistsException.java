package marketplace.exceptions;

public class UserAlreadyExistsException extends AlreadyExistsException {

    public UserAlreadyExistsException(String username) {
        super("User with username = " + username + " already exists");
    }
}
