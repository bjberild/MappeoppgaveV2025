package edu.ntnu.idi.idatt.mappeoppgavev2025.PortalsAndTraps;


import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;



public class PortalAndTrapActionsTest {

    @Test
    public void testPortalAndTrapActions() throws Exception {
        String text = Files.readString(Path.of("src/test/resources/board90.json"));
        JsonObject  json = JsonParser.parseString(text).getAsJsonObject();
        Board board = new GsonBoardPersistence().deserialize(json);

        Player p1 = new Player("Test");
        p1.setCurrentTile(board.getTileById(5).get());
        board.getTileById(5).get().triggerAction(p1);
        assertEquals(15, p1.getCurrentTile().getId(), "Portal should move you from 5 to 15");

        Player p2 = new Player("Test2");
        p2.setCurrentTile(board.getTileById(20).get());
        board.getTileById(20).get().triggerAction(p2);
        assertEquals(2, p2.getCurrentTile().getId(), "Trap should move you from 20 to 2");
    }
    
}
