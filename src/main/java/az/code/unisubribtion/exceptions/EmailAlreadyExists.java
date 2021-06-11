package az.code.unisubribtion.exceptions;

public class EmailAlreadyExists extends RuntimeException {
    public EmailAlreadyExists() {
        super("Email already exists.");
    }
}
