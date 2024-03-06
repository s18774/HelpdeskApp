package pl.wroblewski.helpdeskapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.lang.reflect.Type;

public class EntityNotExists extends BaseHttpException {
    public EntityNotExists(Type type) {
        super(HttpStatus.NOT_FOUND, "Entity " + type.getTypeName() + " not exists");
    }
}
