package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;
import edu.ntnu.idi.idatt.mappeoppgavev2025.service.BoardLoader;
import edu.ntnu.idi.idatt.mappeoppgavev2025.service.PlayerLoader;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.nio.file.Path;
import javafx.stage.Stage;
import java.util.logging.Logger;

/*
 * SnlSelectScreen.java
 * This class represents the screen where the user can select a board and players for the game.
 * It provides buttons to load a board and players, as well as to create new players.
 * The selected board and players are stored in Path variables.
 *
 * @author bjberild
 */
public class SnlSelectScreen {
  private static final Logger logger = Logger.getLogger(SnlSelectScreen.class.getName());
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

  /**
   * Initializes the Snakes and Ladders selection screen.
   * This method sets up the layout, buttons, and event handlers for the screen.
   *
   * @author bjberild
   */
  public void initialize() {
    view.getChildren().clear();
    // Initialize the view with buttons and other UI elements
    VBox boardBox = getBoardBox();
    boardBox.setAlignment(Pos.CENTER);
    boardBox.setSpacing(10);
    VBox playersBox = getPlayersBox();
    playersBox.setAlignment(Pos.CENTER);
    playersBox.setSpacing(10);

    startButton = getSnlButton(stage);

    view.getChildren().addAll(new Label("Press 'Continue' to start the game"),
        startButton, boardBox, playersBox);
    view.setSpacing(10);
    view.setAlignment(Pos.CENTER);
  }

  private VBox getBoardBox() {
    Button loadBoardButton = new Button("Load Board");
    boardLabel = new Label("No board selected. Random board will be used.");

    loadBoardButton.setOnAction(e -> {
      try {
        boardPath = BoardLoader.promtForBoard(stage);
        boardLabel.setText(boardPath.toString());
      } catch (IOException ex) {
        // Handle exception (e.g., show an error message)
        logger.warning("Error loading board: " + ex.getMessage());
      }
    });
    VBox boardBox = new VBox();
    boardBox.getChildren().addAll(boardLabel, loadBoardButton);
    return boardBox;
  }

  private VBox getPlayersBox() {
    Button loadPlayersButton = new Button("Load Players");
    playersLabel = new Label("No players selected. Two default players will be used.");

    loadPlayersButton.setOnAction(e -> {
      try {
        playersPath = PlayerLoader.promtForPlayers(stage);
        playersLabel.setText(playersPath.toString());
      } catch (PlayerPersistenceException ex) {
        // Handle exception (e.g., show an error message)
        logger.warning("Error loading players: " + ex.getMessage());
      }
    });

    Button newPlayerButton = new Button("Create New Players");
    newPlayerButton.setOnAction(e -> {
      PlayerCreationDialog dialog = new PlayerCreationDialog(stage);
      dialog.showAndWait();
      Path createdFile = dialog.getSavedFilePath();
      if (createdFile != null) {
        playersPath = createdFile;
        playersLabel.setText(playersPath.toString());
      }
    });
    HBox playersButtonBox = new HBox();
    playersButtonBox.getChildren().addAll(loadPlayersButton, newPlayerButton);
    playersButtonBox.setAlignment(Pos.CENTER);
    playersButtonBox.setSpacing(10);

    VBox playersBox = new VBox();
    playersBox.getChildren().addAll(playersLabel, playersButtonBox);
    return playersBox;
  }

  private Button getSnlButton(Stage primaryStage) {
    Button snlButton = new Button("Continue");
    snlButton.setOnAction(e -> {

      BoardGameGUI launcher = new BoardGameGUI();
      Scene snlScene = launcher.getScene(primaryStage, boardPath , playersPath);

      SnakesAndLaddersView snlView = launcher.getSnakesAndLaddersView();
      snlView.setOnReturn(evt -> {
        primaryStage.setScene(menuLayout.getScene());
        primaryStage.setWidth(900);
        primaryStage.setHeight(700);
        primaryStage.centerOnScreen();
      });

      primaryStage.setScene(snlScene);

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

  /**
   * Sets the start button for the Snakes and Ladders selection screen.
   * This method allows the caller to provide a custom button for starting the game.
   *
   * @param startButton The button to be used for starting the game.
   * @author bjberild
   */
  public void setStartButton(Button startButton) {
    this.startButton = startButton;
    view.getChildren().remove(startButton);
    view.getChildren().add(startButton);
  }
}
