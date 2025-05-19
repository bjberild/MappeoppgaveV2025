package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.CsvPlayerPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class BoardGameGUI {

    public Scene getScene(Stage stage) {

        BoardGame game = new BoardGame();
        FileChooser boardChooser = new FileChooser();
        boardChooser.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
        boardChooser.setTitle("Load Board Definition");
        File boardFile = boardChooser.showOpenDialog(stage);
        if (boardFile != null) {
            try {
              String jsonText = java.nio.file.Files.readString(boardFile.toPath());
              JsonObject boardJson = JsonParser.parseString(jsonText).getAsJsonObject();
              Board loaded = new GsonBoardPersistence().deserialize(boardJson);
              game.setBoard(loaded);  
            } catch (IOException | IllegalArgumentException e) {
                showError("Error, failed to load board from JSON:\n" + e.getMessage());
                addDefaultPlayersAndBoard(game);
            }
        } else {
            addDefaultPlayersAndBoard(game);
        }
                
        game.createDice(2);

        FileChooser csvChooser = new FileChooser();
        csvChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
        File csvFile = csvChooser.showOpenDialog(stage);
        if (csvFile != null) {
            try {
                CsvPlayerPersistence csvPers = new CsvPlayerPersistence();
                List<Player> players = csvPers.load(csvFile.toPath());
                players.forEach(game::addPlayer);
            } catch (PlayerPersistenceException e) {
                showError("Error, failed to load players from CSV:\n" + e.getMessage());
                addDefaultPlayersAndBoard(game);
            }
        }



        SnakesAndLaddersView view = new SnakesAndLaddersView(game);
        Scene scene = view.toScene(800, 600);

        game.addEventListener(new edu.ntnu.idi.idatt.mappeoppgavev2025.event.TileHighlighter(view.getBoardView()));
        scene.getStylesheets().add(
            getClass().getResource("/edu/ntnu/idi/idatt/mappeoppgavev2025/styles/styles.css")
                   .toExternalForm()
        );
   
        return scene;
    }

    private void addDefaultPlayersAndBoard(BoardGame game) {
        game.addPlayer(new Player("Alice"));
        game.addPlayer(new Player("Bob"));
    }

    private void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Load Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}


