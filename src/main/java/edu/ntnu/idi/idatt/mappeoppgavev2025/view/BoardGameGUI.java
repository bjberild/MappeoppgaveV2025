package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import java.io.File;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.PlayerController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;
import javafx.scene.Scene;
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
            } catch (IOException e) {
              e.printStackTrace();
              game.createBoard();
            }
        } else {
            game.createBoard();
        }
                
        game.createDice(2);

        PlayerController pc = new PlayerController(game);
        FileChooser playerChooser = new FileChooser();
        playerChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
        playerChooser.setTitle("Load Players");
        File playerFile = playerChooser.showOpenDialog(stage);

        if (playerFile != null) {
            try {
                pc.loadPlayers(playerFile.toPath());
            } catch (PlayerPersistenceException ex) {
                ex.printStackTrace();
                //fallback to default players
                game.addPlayer(new Player("Alice"));
                game.addPlayer(new Player("Bob"));
            }
        } else {
            //if no file is selected, create default players
            game.addPlayer(new Player("Alice"));
            game.addPlayer(new Player("Bob"));
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
}


