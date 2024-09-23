package pl.wroblewski.helpdeskapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class InvalidRoleException extends BaseHttpException {
    public InvalidRoleException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
