package az.code.unisubribtion.exceptions;

public class UserDoesNotExists extends RuntimeException {
    public UserDoesNotExists() {
        super("User does not exists.");
    }
}
