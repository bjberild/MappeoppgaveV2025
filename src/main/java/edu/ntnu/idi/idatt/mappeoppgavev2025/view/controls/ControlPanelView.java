package edu.ntnu.idi.idatt.mappeoppgavev2025.view.controls;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.PlayerController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;



public class ControlPanelView extends VBox {
    private final TextArea eventArea = new TextArea();
    private final Button savePlayers = new Button("Save Players");
    private final Button restart      = new Button("Restart Game");

    private final PlayerController playerCtrl;
    
    public ControlPanelView(PlayerController playerCtrl) {
        this.playerCtrl = playerCtrl;
        setPadding(new Insets(20));
        setSpacing(5);


        eventArea.setEditable(false);
        eventArea.setPrefHeight(100);

        savePlayers.setOnAction(e -> onSavePlayers(playerCtrl.getPlayers()));
        restart.setOnAction(e -> {
            playerCtrl.resetGame();
            eventArea.clear();
            restart.setDisable(true);
        });

   
    
        restart.setDisable(true);
        getChildren().addAll(eventArea, savePlayers, restart);

        playerCtrl.getGame().addEventListener(msg -> {
            eventArea.appendText(msg + "\n");
            if (msg.contains("won the game")) {
                restart.setDisable(false);
            }
        });
    }

    private void onSavePlayers(List<Player> players) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File file = chooser.showSaveDialog(getScene().getWindow());
        if (file == null) return;
        
        try {
            playerCtrl.savePlayers(Path.of(file.toURI()));
            showInfo("Player saved to " + file.getName());
        } catch (PlayerPersistenceException ex) {
            showError("Error saving players:\n" + ex.getMessage());
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Save Error"); 
        a.setHeaderText(null);
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
