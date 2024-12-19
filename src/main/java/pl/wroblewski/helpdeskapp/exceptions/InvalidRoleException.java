package pl.wroblewski.helpdeskapp.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidRoleException extends BaseHttpException {
    public InvalidRoleException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
