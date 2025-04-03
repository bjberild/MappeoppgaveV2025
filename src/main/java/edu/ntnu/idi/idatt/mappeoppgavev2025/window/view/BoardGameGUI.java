package edu.ntnu.idi.idatt.mappeoppgavev2025.window.view;


import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;



public class BoardGameGUI {

    private BoardGame game;
    private TextArea eventArea; 
    private Button nextRoundButton;

    public Scene getScene() {
        
        Image gifImage = new Image(getClass().getResourceAsStream("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.png"));
        ImageView gifView = new ImageView(gifImage);
        gifView.setFitWidth(800);
        gifView.setPreserveRatio(true);

        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(gifView);

        game = new BoardGame();
        game.createBoard();
        game.createDice(2);
        game.addPlayer(new Player("Alice"));
        game.addPlayer(new Player("Bob"));
        game.addEventListener(message -> Platform.runLater(() -> eventArea.appendText(message + "\n")));

        GridPane boardGrid = LadderBoardGameView.createBoardGrid(game.getBoard());
        centerPane.getChildren().add(boardGrid);

        eventArea = new TextArea();
        eventArea.setEditable(false);
        eventArea.setPrefHeight(150);

        nextRoundButton = new Button("Play next round");
        nextRoundButton.setOnAction(e -> playNextRound());

        VBox controlBox = new VBox(10, eventArea, nextRoundButton);
        controlBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(centerPane);
        root.setBottom(controlBox);

        Scene scene = new Scene(root, 800, 600);
        return scene;
    }

    private void playNextRound() {
        if (!game.isFinished()) {
            game.playOneRound();
            if (game.isFinished()) {
                nextRoundButton.setDisable(true);
                eventArea.appendText("The winner is " + game.getWinner().getName() + "\n");
            }
        } 
    }
}

    
