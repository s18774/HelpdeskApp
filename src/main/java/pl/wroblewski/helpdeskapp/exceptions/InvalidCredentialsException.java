package pl.wroblewski.helpdeskapp.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BaseHttpException {
    public InvalidCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid user credentials");
    }
}
