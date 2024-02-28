package pl.wroblewski.helpdeskapp.exceptions;

public class PermissionsException extends Exception {
    public PermissionsException() {
        super("Invalid permissions");
    }
}
