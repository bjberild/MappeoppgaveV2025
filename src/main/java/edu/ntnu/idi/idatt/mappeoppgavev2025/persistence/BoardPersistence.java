package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;

public interface BoardPersistence {
    com.google.gson.JsonObject serialize(Board board);
    Board                      deserialize(com.google.gson.JsonObject json);
}