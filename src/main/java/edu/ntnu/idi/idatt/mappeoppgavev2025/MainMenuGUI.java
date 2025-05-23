package edu.ntnu.idi.idatt.mappeoppgavev2025;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.ConnectFourController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourBoard;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardGameGUI;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.ConnectFourGameView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.SnakesAndLaddersView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.SnlSelectScreen;
import java.nio.file.Path;
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
    SnlSelectScreen snlSelectScreen;


    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();
        Image gifImage = new Image(getClass().getResourceAsStream("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.gif"));
        backgroundView = new ImageView(gifImage);
        backgroundView.setFitWidth(2000);
        backgroundView.setFitHeight(1750);
        backgroundView.setPreserveRatio(true);

        Button snlSelectButton = getSelectButton(primaryStage);

        Button c4Button = getC4Button(primaryStage);

        menuLayout = new VBox(10, snlSelectButton, c4Button);
        snlSelectScreen = new SnlSelectScreen(primaryStage, menuLayout);
    menuLayout.setPadding(new Insets(20));

    root.getChildren().addAll(backgroundView, menuLayout);

    Scene scene = new Scene(root, 900, 700);
    primaryStage.setTitle("Main Menu");
    primaryStage.setScene(scene);
    primaryStage.setMinWidth(800);
    primaryStage.setMinHeight(700);
    primaryStage.show();
    }

    private Button getSelectButton(Stage primaryStage) {
        Button snlSelectButton = new Button("Select Board and Players");
        snlSelectButton.setOnAction(e -> {
            snlSelectScreen.initialize();
            snlSelectScreen.setStage(primaryStage);
            Scene selectScene = new Scene(snlSelectScreen.getView(), 800, 700);
            primaryStage.setScene(selectScene);
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
        });

        return snlSelectButton;
    }

    private Button getC4Button(Stage primaryStage) {
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
        return c4Button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}