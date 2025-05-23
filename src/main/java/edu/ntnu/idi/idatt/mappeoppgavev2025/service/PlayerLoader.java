package edu.ntnu.idi.idatt.mappeoppgavev2025.service;

import java.io.File;
import java.nio.file.Path;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Utility class for loading player data from CSV and providing default players.
 * <p>
 * Prompts the user to select a CSV file defining players, or falls back to a hardcoded default.
 * </p>
 *
 * @author StianDolerd
 */
public class PlayerLoader {
    private static final ExtensionFilter CSV_FILTER =
            new ExtensionFilter("CSV files (*.csv)", "*.csv");

    /**
     * Opens a FileChooser dialog for selecting a CSV file containing player definitions.
     *
     * @param owner the owner stage of the dialog
     * @return the {@link Path} to the selected CSV file
     * @throws PlayerPersistenceException if the user cancels without selecting a file
     * @author StianDolerd
     */
    
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
    
     /**
     * Adds two default players (Alice and Bob) to the specified game instance.
     * <p>
     * These defaults are used when no external CSV file was provided.
     * </p>
     *
     * @param game the {@link BoardGame} instance to populate with default players
     * @author StianDolerd
     */
    

    public static void addDefaultPlayers(BoardGame game) {
        game.addPlayer(new Player("Alice", "Red"));
        game.addPlayer(new Player("Bob", "Bule"));
    }
}
