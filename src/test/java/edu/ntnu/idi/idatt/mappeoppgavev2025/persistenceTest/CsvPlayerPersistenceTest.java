package edu.ntnu.idi.idatt.mappeoppgavev2025.persistenceTest;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.CsvPlayerPersistence;


public class CsvPlayerPersistenceTest {
    @Test
    void loadSimpleCsv() throws Exception {
        Path p = Path.of("src/test/resources/players.csv");
        List<Player> list = new CsvPlayerPersistence().load(p);
        assertEquals("Alice", list.get(0).getName());
        assertEquals("Red", list.get(0).getToken());
        assertEquals("Bob", list.get(1).getName());
    }
    
}
