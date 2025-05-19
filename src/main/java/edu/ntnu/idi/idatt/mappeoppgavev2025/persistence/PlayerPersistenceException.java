package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

public class PlayerPersistenceException extends Exception {
    public PlayerPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerPersistenceException(String message) {
        super(message);
    }
}
