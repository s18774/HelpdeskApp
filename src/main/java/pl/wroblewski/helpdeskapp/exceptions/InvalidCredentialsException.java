package pl.wroblewski.helpdeskapp.exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Invalid user credentials");
    }
}
