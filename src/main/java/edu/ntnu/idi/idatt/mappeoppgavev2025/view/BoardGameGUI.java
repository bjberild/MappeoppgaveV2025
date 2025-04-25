package edu.ntnu.idi.idatt.mappeoppgavev2025.view;


import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import javafx.scene.Scene;

public class BoardGameGUI {

    public Scene getScene() {

        BoardGame game = new BoardGame();
        game.createBoard();
        game.createDice(2);
        game.addPlayer(new Player("Alice"));
        game.addPlayer(new Player("Bob"));

        SnakesAndLaddersView view = new SnakesAndLaddersView(game);

        Scene scene = view.toScene(800, 600);


        scene.getStylesheets().add(
            getClass().getResource("/edu/ntnu/idi/idatt/mappeoppgavev2025/styles/styles.css").toExternalForm()
            );

        return scene;
 
    }

}

    
