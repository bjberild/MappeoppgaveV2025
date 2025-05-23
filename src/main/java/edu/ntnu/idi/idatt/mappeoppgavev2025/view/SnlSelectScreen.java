package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;
import edu.ntnu.idi.idatt.mappeoppgavev2025.service.BoardLoader;
import edu.ntnu.idi.idatt.mappeoppgavev2025.service.PlayerLoader;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.nio.file.Path;
import javafx.stage.Stage;

public class SnlSelectScreen {
  private VBox view;
  private Path boardPath;
  private Label boardLabel;
  private Path playersPath;
  private Label playersLabel;
  private Stage stage;
  private Button startButton;
  private Pane menuLayout;

  public SnlSelectScreen() {
    this.view = new VBox();
    this.boardPath = null;
    this.playersPath = null;
  }

  public SnlSelectScreen(Stage stage, Pane menuLayout){
    this();
    this.stage = stage;
    this.menuLayout = menuLayout;
  }

  public void initialize() {
    // Initialize the view with buttons and other UI elements
    Button loadBoardButton = new Button("Load Board");
    boardLabel = new Label("No board selected");
    Button loadPlayersButton = new Button("Load Players");
    playersLabel = new Label("No players selected");

    loadBoardButton.setOnAction(e -> {
      try {
        boardPath = BoardLoader.promtForBoard(stage);
        boardLabel.setText(boardPath.toString());
      } catch (IOException ex) {
        // Handle exception (e.g., show an error message)
        System.err.println("Error loading board: " + ex.getMessage());
      }
    });

    loadPlayersButton.setOnAction(e -> {
      try {
        playersPath = PlayerLoader.promtForPlayers(stage);
        playersLabel.setText(playersPath.toString());
      } catch (PlayerPersistenceException ex) {
        // Handle exception (e.g., show an error message)
        System.err.println("Error loading players: " + ex.getMessage());
      }
    });

    startButton = getSnlButton(stage);

    view.getChildren().addAll(boardLabel, loadBoardButton, playersLabel,
        loadPlayersButton, new Label("Press 'Continue' to start the game"),startButton);
    view.setSpacing(10);
    view.setAlignment(Pos.CENTER);
  }

  private Button getSnlButton(Stage primaryStage) {
    Button snlButton = new Button("Continue");
    snlButton.setOnAction(e -> {

      BoardGameGUI launcher = new BoardGameGUI();
      Scene snlScene = launcher.getScene(primaryStage, boardPath , playersPath);

      SnakesAndLaddersView snlView = launcher.getSnakesAndLaddersView();
      snlView.setOnReturn(evt -> {
        primaryStage.setScene(menuLayout.getScene());
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
      });

      primaryStage.setScene(snlScene);
      primaryStage.sizeToScene();
      primaryStage.centerOnScreen();
    });
    return snlButton;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public VBox getView() {
    return view;
  }

  public Path getBoardPath() {
    return boardPath;
  }

  public Path getPlayersPath() {
    return playersPath;
  }

  public void setStartButton(Button startButton) {
    this.startButton = startButton;
    view.getChildren().remove(startButton);
    view.getChildren().add(startButton);
  }
}
