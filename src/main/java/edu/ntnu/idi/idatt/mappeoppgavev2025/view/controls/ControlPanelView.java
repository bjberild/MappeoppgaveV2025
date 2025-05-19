package edu.ntnu.idi.idatt.mappeoppgavev2025.view.controls;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.CsvPlayerPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;



public class ControlPanelView extends VBox {
    private final TextArea eventArea = new TextArea();
    private final Button nextRoundButton = new Button("Next Round");
    private final Button savePlayersButton = new Button("Save Players");
    
    public ControlPanelView(BoardGame game) {
        setPadding(new Insets(20));
        setSpacing(5);
        eventArea.setEditable(false);
        eventArea.setPrefHeight(100);

        nextRoundButton.setOnAction(e -> game.playOneRound());
        savePlayersButton.setOnAction(e -> savePlayers(game.getPlayers()));

        nextRoundButton.setDisable(game.getPlayers().isEmpty());
        savePlayersButton.setDisable(game.getPlayers().isEmpty());

        getChildren().addAll(eventArea, nextRoundButton, savePlayersButton);
        
        game.addEventListener(message -> {eventArea.appendText(message + "\n");
        });

    }

    private void savePlayers(List<Player> players) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File file = chooser.showSaveDialog(getScene().getWindow());
        if (file != null) {
            try {
                new CsvPlayerPersistence().save(Path.of(file.toURI()), players);
                showInfo("Players saved to: " + file.getName());
            } catch (PlayerPersistenceException ex) {
                showError("Failed to save CSV:\n " + ex.getMessage());
            }
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(AlertType.ERROR);
        a.setTitle("Save Error"); a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.initOwner(getScene().getWindow());
        a.setTitle("Info"); 
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }


    
}
