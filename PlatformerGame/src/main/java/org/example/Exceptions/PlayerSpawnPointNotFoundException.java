package org.example.Exceptions;

public class PlayerSpawnPointNotFoundException extends RuntimeException {
    public PlayerSpawnPointNotFoundException(String message) {
        super(message);
    }
}
