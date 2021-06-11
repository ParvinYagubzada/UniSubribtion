package az.code.unisubribtion.exceptions;

public class UsernameAlreadyExists extends RuntimeException {
    public UsernameAlreadyExists() {
        super("Username already exists.");
    }
}
