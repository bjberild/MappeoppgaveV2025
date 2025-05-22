package edu.ntnu.idi.idatt.mappeoppgavev2025;

import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardGameGUI;
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

        Image gifImage = new Image(getClass().getResourceAsStream("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.gif"));
        backgroundView = new ImageView(gifImage);
        backgroundView.setFitWidth(2000);
        backgroundView.setFitHeight(1750);
        backgroundView.setPreserveRatio(true);

        Button snlB = new Button("Snakes and Ladders");

        snlB.setOnAction(e -> {
           BoardGameGUI launcher = new BoardGameGUI();
           Scene gameScene = launcher.getScene(primaryStage);
           primaryStage.setScene(gameScene);
           primaryStage.sizeToScene();
           primaryStage.centerOnScreen();
        });

        Button c4MenuBtn = getC4Button();

        menuLayout = new VBox(10, snlB, c4MenuBtn);
        menuLayout.setPadding(new Insets(20));

        root.getChildren().addAll(backgroundView, menuLayout);

        Scene menuScene = new Scene(root);

        primaryStage.setTitle("Game Game");
        primaryStage.setScene(menuScene);

        primaryStage.setWidth(900);
        primaryStage.setHeight(700);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(true);

        primaryStage.show();
    }

    private Button getC4Button() {
        Button c4MenuBtn = new Button("Connect Four");

        c4MenuBtn.setOnAction(e -> {
            ConnectFourBoard c4board = new ConnectFourBoard();
            ConnectFourGameView c4view = new ConnectFourGameView();
            ConnectFourController c4controller = new ConnectFourController(c4view, c4board);
            Pane c4layout = c4controller.getView();
            c4controller.addReturnButtonHandler(event -> {
                root.getChildren().remove(c4layout);
                root.getChildren().addAll(backgroundView,menuLayout);
            });
            root.getChildren().removeAll(backgroundView,menuLayout,c4layout);
            root.getChildren().add(c4layout);
        });
        return c4MenuBtn;
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
