package pl.wroblewski.helpdeskapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.wroblewski.helpdeskapp.dto.BaseResponse;
import pl.wroblewski.helpdeskapp.exceptions.*;
import pl.wroblewski.helpdeskapp.models.User;

public class BaseController {
    @ExceptionHandler({EntityNotExists.class, UserNotExistsException.class,
            PermissionsException.class, InvalidRoleException.class,
            DeviceAlreadyAttachedException.class})
    public ResponseEntity<BaseResponse> handleException(Exception ex) {
        BaseResponse response = BaseResponse
                .builder()
                .success(false)
                .message(ex.getMessage())
                .build();

        if (ex instanceof BaseHttpException e) {
            return new ResponseEntity<>(response, e.getHttpStatusCode());
        }

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
