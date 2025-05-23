package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player; 

/**
 * Load and saces Player definitons to and from a CSV file.
 * 
 * <p>Each line in the CSV file should contain a player name and a token, separated by a comma.
 * Missing token or extra colums will produca a PlayerPersistenceException.
 * 
 * @author StianDolerud
 */

public class CsvPlayerPersistence implements PlayerPersistence {

    private static final String SEP = ",";

   /**
   * Reads players from a CSV file.
   *
   * @param csvFile the path to the CSV file containing player definitions
   * @return a list of {@link Player} objects
   * @throws PlayerPersistenceException if the file cannot be read or if any line is malformed
   */

    @Override
    public List<Player> load (Path csvFile) throws PlayerPersistenceException {
        try {
            List<Player> players = new ArrayList<>();
            for (String line : Files.readAllLines(csvFile)) {
                String[] parts = line.split(SEP, 2);
                if (parts.length < 2) {
                    throw new PlayerPersistenceException("malformed CSV line: " + line);
                }
                String name = parts[0].trim();
                String token = parts[1].trim();
                players.add(new Player(name, token));
            }
            return players;
        } catch (IOException e) {
            throw new PlayerPersistenceException("Error reading CSV file", e);
        }
    }


  /**
   * Writes a list of players to a CSV file.
   *
   * @param csvFile the path to write the CSV to
   * @param players the players to serialize
   * @throws PlayerPersistenceException if an I/O error occurs during writing
   */

    @Override
    public void save(Path csvFile, List<Player> players) throws PlayerPersistenceException {
        try (BufferedWriter w = Files.newBufferedWriter(csvFile)) {
            for (Player p : players) {
                w.write(p.getName() + SEP + p.getToken());
                w.newLine();
            }
        } catch (IOException e) {
            throw new PlayerPersistenceException("Error writing CSV file", e);
        }
    }
    
}
