package edu.ntnu.idi.idatt.mappeoppgavev2025.view.controls;

import java.io.File;
import java.io.IOException;
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

/**
 * Kontrollpanel for spillet med funksjonalitet for å vise hendelser,
 * lagre spillere, lagre brett og starte på nytt.
 * <p>
 * Panelet inneholder knapper for å styre spillet og en logg over hendelser.
 *
 * @author StianDolerud
 */
public class ControlPanelView extends VBox {
    private final TextArea eventArea = new TextArea();
    private final Button nextRound = new Button("Next Round");
    private final Button savePlayers = new Button("Save Players");
    private final Button restart = new Button("Restart Game");
    private final Button saveBoardJson = new Button("Save Board");
    private final PlayerController playerCtrl;

    /**
     * Oppretter et nytt kontrollpanel.
     *
     * @param playerCtrl kontroller for spillere og spill-logikk
     */
    public ControlPanelView(PlayerController playerCtrl) {
        this.playerCtrl = playerCtrl;
        setPadding(new Insets(20));
        setSpacing(5);

        eventArea.setEditable(false);
        eventArea.setPrefHeight(100);

        nextRound.setOnAction(e -> playerCtrl.playOneRound());
        savePlayers.setOnAction(e -> onSavePlayers(playerCtrl.getPlayers()));
        restart.setOnAction(e -> {
            playerCtrl.resetGame();
            eventArea.clear();
            nextRound.setDisable(false);
            restart.setDisable(true);
        });

        saveBoardJson.setOnAction(e -> onSaveBoardJson());

        restart.setDisable(true);
        getChildren().addAll(eventArea, saveBoardJson, savePlayers, restart);

        playerCtrl.getGame().addEventListener(msg -> {
            eventArea.appendText(msg + "\n");
            if (msg.contains("won the game")) {
                nextRound.setDisable(true);
                restart.setDisable(false);
            }
        });
    }

    /**
     * Lagrer spillerlisten til valgt fil via dialogboks.
     *
     * @param players listen over spillere som skal lagres
     */
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

    /**
     * Viser en feilmelding i et dialogvindu.
     *
     * @param msg feilmeldingen som skal vises
     */
    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Save Error");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    /**
     * Viser en informasjonsmelding i et dialogvindu.
     *
     * @param msg meldingen som skal vises
     */
    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.initOwner(getScene().getWindow());
        a.setTitle("Info");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }

    /**
     * Lagrer spillbrettet som JSON via dialogboks.
     */
    private void onSaveBoardJson() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File file = chooser.showSaveDialog(getScene().getWindow());
        if (file == null) return;
        try {
            playerCtrl.saveBoardAsJson(Path.of(file.toURI()));
            showInfo("Board saved to " + file.getName());
        } catch (IOException ex) {
            showError("Error saving board:\n" + ex.getMessage());
        }
    }
}