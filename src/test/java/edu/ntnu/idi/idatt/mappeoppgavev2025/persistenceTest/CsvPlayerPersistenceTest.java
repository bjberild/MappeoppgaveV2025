package edu.ntnu.idi.idatt.mappeoppgavev2025.persistenceTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.CsvPlayerPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;


public class CsvPlayerPersistenceTest {
    @Test
    void loadSimpleCsv() throws Exception {
        Path p = Path.of("src/test/resources/players.csv");
        List<Player> list = new CsvPlayerPersistence().load(p);
        assertEquals("Alice", list.get(0).getName());
        assertEquals("Red", list.get(0).getToken());
        assertEquals("Bob", list.get(1).getName());
    }

    @Test
    public void testLoadEmptyFile() throws Exception {
        Path tmp = Files.createTempFile("empty", ".csv");      
        List<Player> players = new CsvPlayerPersistence().load(tmp);
        assertTrue(players.isEmpty());
    }

    @Test
    public void testMalformedLineThrows() throws Exception {
        Path tmp = Files.createTempFile("bad", ".csv");
        Files.writeString(tmp, "OnlyOneColumn\nAnother,Bad,TooMany");
        assertThrows(
            PlayerPersistenceException.class, 
            () -> new CsvPlayerPersistence().load(tmp)
        );
    }

    @Test
    public void testSavedAndLoadRoundTrip() throws Exception {
        Path tmp = Files.createTempFile("players", ".csv");
        var persistence = new CsvPlayerPersistence();
        List<Player> players = List.of(
            new Player("Alice", "Red"),
            new Player("Bob", "Blue")
        );
        persistence.save(tmp, players);
        List<Player> reloaded = persistence.load(tmp);

        assertEquals(2, reloaded.size());
        assertEquals("Alice", reloaded.get(0).getName());
        assertEquals("Red", reloaded.get(0).getToken());
        assertEquals("Bob", reloaded.get(1).getName());
    }
    
}
