package az.code.unisubribtion.exceptions;

public class LoginException extends RuntimeException {
    public LoginException() {
        super("Username or password is incorrect.");
    }
}
