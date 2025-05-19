package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistence;          
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException; 

public class CsvPlayerPersistence implements PlayerPersistence {

    private static final String SEP = ",";

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
