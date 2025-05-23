package edu.ntnu.idi.idatt.mappeoppgavev2025.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Utility class for prompting the user to select a board definition file.
 * <p>
 * Displays a file chooser dialog filtered to JSON files and returns the
 * selected file as a Path.
 * </p>
 *
 * @author StianDolerud
 */
public class BoardLoader {
    /**
     * File extension filter for JSON files.
     */
    private static final ExtensionFilter JSON_FILTER = 
            new ExtensionFilter("JSON files", "*.json");

     /**
     * Opens a file chooser dialog for selecting a board definition JSON file.
     * <p>
     * The dialog is modal to the given owner Stage. Only files matching
     * the {@code *.json} extension are shown.
     * </p>
     *
     * @param owner the Stage to which the file chooser dialog will be modal
     * @return the Path to the selected JSON file
     * @throws IOException if the user cancels or no file is selected
     */

    public static Path promtForBoard(Stage owner) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load Board Definition");
        chooser.getExtensionFilters().add(JSON_FILTER);
        File file = chooser.showOpenDialog(owner);
        if (file == null) {
            throw new IOException("No file selected");
        }
        return file.toPath();
    }
    
}
