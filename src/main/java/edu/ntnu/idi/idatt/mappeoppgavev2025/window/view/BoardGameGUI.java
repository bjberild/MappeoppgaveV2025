package edu.ntnu.idi.idatt.mappeoppgavev2025.window.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BoardGameGUI extends Application {

    private BoardGame game;
    private TextArea eventArea; 
    private Button nextRoundButton;

    @Override
    public void start(Stage primaryStage) {
        
        Image gifImage = new Image(getClass().getResourceAsStream("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.gif"));
        ImageView gifView = new ImageView(gifImage);
        gifView.setFitWidth(800);
        gifView.setPreserveRatio(true);

        eventArea = new TextArea();
        eventArea.setEditable(false);
        eventArea.setPrefHeight(150);

        nextRoundButton = new Button("Play next round");
        nextRoundButton.setOnAction(e -> playNextRound());

        BorderPane root = new BorderPane();
        root.setCenter(gifView);

        VBox controlBox = new VBox(10, eventArea, nextRoundButton);
        controlBox.setPadding(new Insets(10));
        root.setBottom(controlBox);
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("Board Game GUI");
        primaryStage.setScene(scene);
        primaryStage.show();

        game = new BoardGame();
        game.createBoard();
        game.createDice(2);
        game.addPlayer(new Player("Alice"));
        game.addPlayer(new Player("Bob"));

        game.addEventListener(message -> Platform.runLater(() -> eventArea.appendText(message + "\n")));
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

    public static void main(String[] args) {
        launch(args);
    }
  
  }
    
