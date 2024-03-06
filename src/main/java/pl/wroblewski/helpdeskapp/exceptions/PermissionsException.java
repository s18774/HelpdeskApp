package pl.wroblewski.helpdeskapp.exceptions;

import org.springframework.http.HttpStatus;

public class PermissionsException extends BaseHttpException {
    public PermissionsException() {
        super(HttpStatus.FORBIDDEN, "Invalid permissions");
    }
}
