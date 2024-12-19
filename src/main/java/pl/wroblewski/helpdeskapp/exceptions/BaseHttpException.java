package pl.wroblewski.helpdeskapp.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class BaseHttpException extends Exception {
    private final HttpStatusCode httpStatusCode;

    public BaseHttpException(HttpStatusCode httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
