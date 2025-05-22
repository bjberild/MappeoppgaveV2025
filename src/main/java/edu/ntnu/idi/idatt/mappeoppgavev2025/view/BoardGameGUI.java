package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import java.io.IOException;
import java.nio.file.Path;

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

    public Scene getScene(Stage stage) {

        BoardGame game = new BoardGame();
        game.createBoard();
        game.createDice(2);

        Path boardPath = null;
        try {
            boardPath = BoardLoader.promtForBoard(stage);
        } catch (IOException e) { 
        }
        if (boardPath != null) {
            try {
                game.loadBoard(boardPath);
            } catch (IOException | IllegalArgumentException e) {
                showError("Error loading board:\n" + e.getMessage());
            }
        }

        try {
            Path playersPath = PlayerLoader.promtForPlayers(stage);
            game.loadPlayers(playersPath);
        } catch (PlayerPersistenceException e) {
            showError("Error loading players:\n" + e.getMessage());
            PlayerLoader.addDefaultPlayers(game);
        }

        SnakesAndLaddersView view = new SnakesAndLaddersView(game);
        game.addEventListener(new TileHighlighter(view.getBoardView()));
        game.addEventListener(new PlayerMovementListener(view.getBoardView(), game.getPlayers()));

        Scene scene = view.toScene(800, 600);

        game.addEventListener(new edu.ntnu.idi.idatt.mappeoppgavev2025.event.TileHighlighter(view.getBoardView()));
        scene.getStylesheets().add(
            getClass().getResource("/edu/ntnu/idi/idatt/mappeoppgavev2025/styles/styles.css")
                   .toExternalForm()
        );
        return scene;
    }

    private void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Load Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}


