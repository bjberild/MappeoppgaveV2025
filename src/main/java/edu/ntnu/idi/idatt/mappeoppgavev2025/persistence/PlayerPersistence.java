package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import java.nio.file.Path;
import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;

public interface PlayerPersistence {

    List<Player> load(Path cvsFile) throws PlayerPersistenceException;

    void save(Path cvsFile, List<Player> players) throws PlayerPersistenceException;
    
}
