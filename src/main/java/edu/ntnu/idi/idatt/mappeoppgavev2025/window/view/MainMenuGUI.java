package edu.ntnu.idi.idatt.mappeoppgavev2025.window.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//snlB (snakesAndLaddersButton)
//sogB (someOtherGameButton)

public class MainMenuGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button snlB = new Button("Snakes and Ladders");
        Button sogB = new Button("Some Other Game");

        snlB.setOnAction(e -> {
            BoardGameGUI boardGameGUI = new BoardGameGUI();
            try {
                boardGameGUI.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        sogB.setDisable(true);

        VBox menuLayout = new VBox(10, snlB, sogB);
        Scene menuScene = new Scene(menuLayout, 400, 300);

        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
