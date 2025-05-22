package edu.ntnu.idi.idatt.mappeoppgavev2025.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class BoardLoader {
    private static final ExtensionFilter JSON_FILTER = 
            new ExtensionFilter("JSON files", "*.json");

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
