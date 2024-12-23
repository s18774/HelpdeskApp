package pl.wroblewski.helpdeskapp.exceptions;

import org.springframework.http.HttpStatus;

public class PdfException extends BaseHttpException {
    public PdfException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
