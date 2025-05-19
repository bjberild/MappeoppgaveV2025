package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.idi.idatt.mappeoppgavev2025.event.TileHighlighter;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardView;

import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;




public class BoardGameGUI {

    public Scene getScene(Stage stage) {

        BoardGame game = new BoardGame();

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
        File file = chooser.showOpenDialog(stage);

        if (file != null) {
            try {
              String jsonText = Files.readString(file.toPath());
              JsonObject boardJson = JsonParser.parseString(jsonText).getAsJsonObject();
              Board loaded = new GsonBoardPersistence().deserialize(boardJson);
              game = new BoardGame();
              game.setBoard(loaded);  
            } catch (IOException e) {
              e.printStackTrace();

              game.createBoard();
            }
        } else {
            game.createBoard();
        }
                
        game.createDice(2);
        game.addPlayer(new Player("Alice"));
        game.addPlayer(new Player("Bob"));

        SnakesAndLaddersView view = new SnakesAndLaddersView(game);

        Scene scene = view.toScene(800, 600);
        game.addEventListener(new TileHighlighter(view.getBoardView()));

        scene.getStylesheets().add(
            getClass().getResource("/edu/ntnu/idi/idatt/mappeoppgavev2025/styles/styles.css").toExternalForm()
            );

        return scene;

    }
}


