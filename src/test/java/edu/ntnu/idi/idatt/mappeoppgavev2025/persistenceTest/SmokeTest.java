package edu.ntnu.idi.idatt.mappeoppgavev2025.persistenceTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.CsvPlayerPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;


public class SmokeTest {

    @Test
    void loadPlayersAndBoardSmoke() throws Exception {
        Path csv = Path.of("src/test/resources/players.csv");
        List<Player> players = new CsvPlayerPersistence().load(csv);
        assertFalse(players.isEmpty(), "Should have loaded at least one player");

        String jsonText = Files.readString(Path.of("src/test/resources/board.json"));
        JsonObject json = JsonParser.parseString(jsonText).getAsJsonObject();
        Board board = new GsonBoardPersistence().deserialize(json);
        assertNotNull(board.getStartTile(), "Board should have a start tile");
        assertEquals(90, board.getTileAfter(board.getStartTile(), 89).getId());
    }
    
}
