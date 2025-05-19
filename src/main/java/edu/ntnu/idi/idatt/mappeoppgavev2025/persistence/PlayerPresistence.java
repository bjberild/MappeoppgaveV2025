package edu.ntnu.idi.idatt.mappeoppgavev2025.persistence;

import java.nio.file.Path;
import java.util.List;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;

public interface PlayerPresistence {

    List<Player> load(Path cvsFile) throws PlayerPersistenceException;

    void ssave(Path cvsFile, List<Player> players) throws PlayerPersistenceException;
    
}
