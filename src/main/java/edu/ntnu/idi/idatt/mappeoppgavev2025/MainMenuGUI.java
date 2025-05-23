package edu.ntnu.idi.idatt.mappeoppgavev2025;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.ConnectFourController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourBoard;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.ConnectFourGameView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.SnlSelectScreen;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * MainMenuGUI is the main menu for the board game hub application.
 * It allows users to select between different games, such as Snakes and Ladders and Connect Four.
 *
 * @author bjberild, StianDolerud
 */
public class MainMenuGUI extends Application {

  StackPane root = new StackPane();
  VBox menuLayout;
  ImageView backgroundView;
  SnlSelectScreen snlSelectScreen;
  Scene snlSelectScreenScene;

  /**
   * Initializes the main menu GUI for the board game hub.
   *
   * @param primaryStage the primary stage for this application
   * @author bjberild, StianDolerud
   */
  @Override
  public void start(Stage primaryStage) {
    root = new StackPane();
    Image gifImage = new Image(getClass().getResourceAsStream(
        "/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.gif"));
    backgroundView = new ImageView(gifImage);
    backgroundView.setFitWidth(1900);
    backgroundView.setFitHeight(1600);
    backgroundView.setPreserveRatio(true);

    Button snlSelectButton = getSelectButton(primaryStage);
    Button c4Button = getC4Button(primaryStage);

    menuLayout = new VBox(10, snlSelectButton, c4Button);

    snlSelectScreen = new SnlSelectScreen(primaryStage, menuLayout);
    menuLayout.setPadding(new Insets(20));
    menuLayout.setAlignment(Pos.CENTER);

    root.getChildren().addAll(backgroundView, menuLayout);

    Scene menuScene = new Scene(root);

    primaryStage.setTitle("Board Game Hub");
    primaryStage.setScene(menuScene);

    primaryStage.setWidth(900);
    primaryStage.setHeight(700);
    primaryStage.setMinWidth(800);
    primaryStage.setMinHeight(700);
    primaryStage.setResizable(true);

    primaryStage.show();
  }

  /**
   * Creates a button for the Snakes and Ladders game and sets its action.
   *
   * @return the Snakes and Ladders button
   * @author bjberild
   */
  private Button getSelectButton(Stage primaryStage) {
    Button snlSelectButton = new Button("Play Snakes and Ladders");
    snlSelectButton.setOnAction(e -> {
      snlSelectScreen = new SnlSelectScreen(primaryStage, menuLayout);
      snlSelectScreen.initialize();
      snlSelectScreen.setStage(primaryStage);
      snlSelectScreenScene = new Scene(snlSelectScreen.getView(), 800, 700);
      primaryStage.setScene(snlSelectScreenScene);
      primaryStage.sizeToScene();
      primaryStage.centerOnScreen();
    });

    return snlSelectButton;
  }

  /**
   * Creates a button for the Connect Four game and sets its action.
   *
   * @return the Connect Four button
   * @author bjberild
   */
  private Button getC4Button(Stage primaryStage) {
    Button c4Button = new Button("Play Connect Four");
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
