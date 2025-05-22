package edu.ntnu.idi.idatt.mappeoppgavev2025.service;

import java.io.File;
import java.nio.file.Path;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class PlayerLoader {
    private static final ExtensionFilter CSV_FILTER =
            new ExtensionFilter("CSV files (*.csv)", "*.csv");
    
    public static Path promtForPlayers(Stage owner) throws PlayerPersistenceException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load Players");
        chooser.getExtensionFilters().add(CSV_FILTER);
        File file = chooser.showOpenDialog(owner);
        if (file == null) {
            throw new PlayerPersistenceException("No file selected");
        }
        return file.toPath();
    }
    

    public static void addDefaultPlayers(BoardGame game) {
        game.addPlayer(new Player("Alice", "Red"));
        game.addPlayer(new Player("Bob", "Bule"));
    }
}
