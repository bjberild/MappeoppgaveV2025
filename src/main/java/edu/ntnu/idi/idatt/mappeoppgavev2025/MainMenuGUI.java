package edu.ntnu.idi.idatt.mappeoppgavev2025;

import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardGameGUI;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//snlB (snakesAndLaddersButton)
//sogB (someOtherGameButton)

public class MainMenuGUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        Image gifImage = new Image(getClass().getResourceAsStream("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.gif"));
        ImageView backgroundView = new ImageView(gifImage);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(700);
        backgroundView.setPreserveRatio(true);

        Button snlB = new Button("Snakes and Ladders");
        Button sogB = new Button("Some Other Game");

        snlB.setOnAction(e -> {
           BoardGameGUI launcher = new BoardGameGUI();
           Scene gameScene = launcher.getScene(primaryStage);
           primaryStage.setScene(gameScene);
           primaryStage.setTitle("Snakes and Ladders");
           primaryStage.sizeToScene();
           primaryStage.centerOnScreen();


        });

        sogB.setDisable(true);

        VBox menuLayout = new VBox(10, snlB, sogB);
        menuLayout.setPadding(new Insets(20));

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, menuLayout);

        Scene menuScene = new Scene(root, 400, 300);

        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(menuScene);

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(true);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
