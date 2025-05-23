package edu.ntnu.idi.idatt.mappeoppgavev2025;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.ConnectFourController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourBoard;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardGameGUI;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.ConnectFourGameView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.SnakesAndLaddersView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//snlB (snakesAndLaddersButton)
//sogB (someOtherGameButton)

public class MainMenuGUI extends Application {
    StackPane root = new StackPane();
    Pane menuLayout = new Pane();
    ImageView backgroundView;

    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();

        Image gifImage = new Image(getClass().getResourceAsStream("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.gif"));
        backgroundView = new ImageView(gifImage);
        backgroundView.setFitWidth(2000);
        backgroundView.setFitHeight(1750);
        backgroundView.setPreserveRatio(true);

           Button snlButton = new Button("Snakes and Ladders");
        snlButton.setOnAction(e -> {
        
            BoardGameGUI launcher = new BoardGameGUI();
            Scene snlScene = launcher.getScene(primaryStage);
        
            SnakesAndLaddersView view = launcher.getSnakesAndLaddersView();
            view.setOnReturn(evt -> {
                primaryStage.setScene(menuLayout.getScene());
                primaryStage.sizeToScene();
                primaryStage.centerOnScreen();
            });

            primaryStage.setScene(snlScene);
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
        });


        Button c4Button = new Button("Connect Four");

        c4Button.setOnAction(e -> {
            ConnectFourBoard c4board = new ConnectFourBoard();
            ConnectFourGameView c4view = new ConnectFourGameView();
            ConnectFourController c4ctrl = new ConnectFourController(c4view, c4board);

            c4ctrl.addReturnButtonHandler(evt -> {
                primaryStage.setScene(menuLayout.getScene());
                primaryStage.sizeToScene();
                primaryStage.centerOnScreen();
            });

            Scene c4Scene = c4ctrl.getScene();
            primaryStage.setScene(c4Scene);
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
        });


    menuLayout = new VBox(10, snlButton, c4Button);
    menuLayout.setPadding(new Insets(20));

    root.getChildren().addAll(backgroundView, menuLayout);

    Scene scene = new Scene(root, 900, 700);
    primaryStage.setTitle("Main Menu");
    primaryStage.setScene(scene);
    primaryStage.setMinWidth(800);
    primaryStage.setMinHeight(700);
    primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}