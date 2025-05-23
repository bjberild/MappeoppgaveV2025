package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;

/**
 * Interface for Board persistence.
 * This interface defines methods for serializing and deserializing Board objects.
 */
public interface BoardPersistence {
    com.google.gson.JsonObject serialize(Board board);
    Board                      deserialize(com.google.gson.JsonObject json);
}