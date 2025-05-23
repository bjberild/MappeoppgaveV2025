package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import java.io.IOException;
import java.nio.file.Path;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.PlayerController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.event.PlayerMovementListener;
import edu.ntnu.idi.idatt.mappeoppgavev2025.event.TileHighlighter;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;
import edu.ntnu.idi.idatt.mappeoppgavev2025.service.BoardLoader;
import edu.ntnu.idi.idatt.mappeoppgavev2025.service.PlayerLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BoardGameGUI {
    private SnakesAndLaddersView snakesview;


    public Scene getScene(Stage stage, Path boardPath, Path playersPath) {

        BoardGame game = new BoardGame();
        game.createBoard();
        game.createDice(2);

        /*
        Path boardPath = null;
        try {
            boardPath = BoardLoader.promtForBoard(stage);
        } catch (IOException e) {
            // Ignore "No file selected" (user cancelled). Show error for others.
            if (!"No file selected".equals(e.getMessage())) {
                showError("Error loading board:\n" + e.getMessage());
            }
        }

         */

        if (boardPath != null) {
            try {
                game.loadBoard(boardPath);
            } catch (IOException | IllegalArgumentException e) {
                showError("Error loading board:\n" + e.getMessage());
            }
        }

        try {
            if (playersPath != null) {
            game.loadPlayers(playersPath);
            } else {
                PlayerLoader.addDefaultPlayers(game);
            }
        } catch (PlayerPersistenceException e) {
            showError("Error loading players:\n" + e.getMessage());
            PlayerLoader.addDefaultPlayers(game);
        }
        
        PlayerController pc = new PlayerController(game);

        SnakesAndLaddersView view = new SnakesAndLaddersView(pc);
        this.snakesview = view;

        
        game.addEventListener(new TileHighlighter(view.getBoardView()));
        game.addEventListener(new PlayerMovementListener(view.getBoardView(), game.getPlayers()));

        Scene scene = view.toScene(1200, 1100);

        game.addEventListener(new edu.ntnu.idi.idatt.mappeoppgavev2025.event.TileHighlighter(view.getBoardView()));
        scene.getStylesheets().add(
            getClass().getResource("/edu/ntnu/idi/idatt/mappeoppgavev2025/styles/styles.css")
                   .toExternalForm()
        );
        return scene;
    }

    public SnakesAndLaddersView getSnakesAndLaddersView() {
        return snakesview;
    }


    private void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Load Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}


