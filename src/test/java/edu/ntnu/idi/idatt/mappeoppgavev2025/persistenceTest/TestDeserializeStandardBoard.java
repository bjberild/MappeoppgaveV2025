package edu.ntnu.idi.idatt.mappeoppgavev2025.persistenceTest;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;


public class TestDeserializeStandardBoard {

    @Test
    public void testDeserializeStandardBoard() throws Exception {
        String text = Files.readString(Path.of("src/test/resources/board90.json"));
        JsonObject json = JsonParser.parseString(text).getAsJsonObject();
        Board board = new GsonBoardPersistence().deserialize(json);
        assertEquals(1, board.getStartTile().getId());
        assertEquals(90, board.getTileAfter(board.getStartTile(), 89).getId());
    }
    
}
