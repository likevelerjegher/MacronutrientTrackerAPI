package com.likevel.kaloriinnhold.exception;

public class ObjectExistedException extends RuntimeException {
    public ObjectExistedException(final String mes) {
        super(mes);
    }
}