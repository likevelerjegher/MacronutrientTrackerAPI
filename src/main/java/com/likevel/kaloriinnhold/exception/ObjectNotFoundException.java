package com.likevel.kaloriinnhold.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(final String msg) {
        super(msg);
    }
}