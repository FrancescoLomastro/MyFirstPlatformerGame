package org.example.Exceptions;

public class SwordNotFoundException extends RuntimeException {
    public SwordNotFoundException(String message) {
        super(message);
    }
}
