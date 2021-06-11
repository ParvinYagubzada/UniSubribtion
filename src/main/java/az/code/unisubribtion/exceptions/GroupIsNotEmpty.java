package az.code.unisubribtion.exceptions;

public class GroupIsNotEmpty extends RuntimeException {
    public GroupIsNotEmpty() {
        super("Email already exists.");
    }
}
