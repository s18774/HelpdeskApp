package pl.wroblewski.helpdeskapp.exceptions;

import java.lang.reflect.Type;

public class EntityNotExists extends Exception {
    public EntityNotExists(Type type) {
        super("Entity " + type.getTypeName() + " not exists");
    }
}
