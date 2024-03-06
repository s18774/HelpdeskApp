package pl.wroblewski.helpdeskapp.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotExistsException extends BaseHttpException {
    public UserNotExistsException() {
        super(HttpStatus.NOT_FOUND, "User not exists");
    }
}
