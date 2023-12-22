package pl.wroblewski.helpdeskapp.exceptions;

public class UserNotExistsException extends Exception {
    public UserNotExistsException() {
        super("User not exists");
    }
}
