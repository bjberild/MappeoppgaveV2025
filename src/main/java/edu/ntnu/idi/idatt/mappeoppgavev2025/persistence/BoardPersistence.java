package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import com.google.gson.JsonObject;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;


public interface BoardPersistence {
    JsonObject serialize(Board board);
    Board        deserialize(JsonObject json);
}
