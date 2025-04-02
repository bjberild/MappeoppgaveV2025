package edu.ntnu.idi.idatt.mappeoppgavev2025.window.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BoardGameGUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        Image gifImage = new Image(getClass().getResourceAsStream("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.gif"));
        ImageView gifView = new ImageView(gifImage);

        gifView.setFitWidth(800);
        gifView.setPreserveRatio(true);

        StackPane root = new StackPane();
        root.getChildren().add(gifView);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        primaryStage.setTitle("Board Game GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
  
  }
    
