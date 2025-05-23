package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourPiece;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.event.ActionEvent;


/**
 * ConnectFourGameView is a JavaFX class that represents the graphical user interface
 * for the Connect Four game. It provides methods to initialize the game grid, update
 * the current player label, and handle button events.
 *
 * @author bjberild
 */
public class ConnectFourGameView {
  private static final int ROWS = 6;
  private static final int COLUMNS = 7;
  private final GridPane grid;
  private final Button returnButton = new Button("Return to Main Menu");
  private final Label currentPlayerLabel = new Label("Current Player: Player 1");
  private final Button resetButton = new Button("Reset Game");
  private final Button switchStartButton = new Button("Switch Start Player");

  /**
   * Constructor for ConnectFourGameView.
   * Initializes the game view with a grid and buttons.
   * Sets the reset button to be disabled initially.
   *
   * @author bjberild
   */
  public ConnectFourGameView() {
    this.grid = new GridPane();
    resetButton.setDisable(true);
    initializeGrid();
    styleElements();
  }

  /**
   * Initializes the grid for the Connect Four game.
   *
   * @author bjberild
   */
  private void initializeGrid() {
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(5);
    grid.setVgap(5);

    HBox buttonRow = new HBox(5);
    buttonRow.setAlignment(Pos.CENTER);

    for (int col = 0; col < COLUMNS; col++) {
      Button dropButton = new Button("Drop");
      dropButton.setAlignment(Pos.CENTER);
      buttonRow.getChildren().add(dropButton);
      grid.add(dropButton, col, 0);
    }

    for (int col = 0; col < COLUMNS; col++) {
      for (int row = 0; row < ROWS; row++) {
        Circle cell = createCell();
        grid.add(cell, col, row+1);
      }
    }
  }

  /**
   * Styles the elements of the Connect Four game view.
   *
   * @author bjberild
   */
  private void styleElements() {
    currentPlayerLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000;");

    returnButton.setAlignment(Pos.CENTER);
    returnButton.setPadding(new Insets(10));

    currentPlayerLabel.setAlignment(Pos.CENTER);
    currentPlayerLabel.setPadding(new Insets(10));
  }

  /**
   * Sets the current player label to display the current player's name.
   *
   * @param player The name of the current player.
   * @author bjberild
   */
  public void setCurrentPlayerLabel(String player) {
    currentPlayerLabel.setText("Current Player: " + player);
  }

  /**
   * Sets the winner label to display the winner of the game.
   *
   * @param winner The name of the winning player.
   * @author bjberild
   */
  public void setWinnerLabel(String winner) {
    currentPlayerLabel.setText("Winner: " + winner);
  }

  /**
   * Clears the Connect Four board by resetting all cells to white.
   *
   * @author bjberild
   */
  public void clearBoard() {
    for (int col = 0; col < COLUMNS; col++) {
      for (int row = 0; row < ROWS; row++) {
        Circle cell = (Circle) getNodeFromGridPane(grid, col, row+1); // Adjust for button row
        cell.setFill(Color.WHITE);
      }
    }
  }

  /**
   * Sets the event handler for the drop button in a specific column.
   *
   * @param column The column index of the drop button.
   * @param handler The event handler to be set.
   * @author bjberild
   */
  public void setDropButtonEventHandler(int column, EventHandler<ActionEvent> handler) {
    Button button = (Button) getNodeFromGridPane(grid, column, 0); // Buttons are in the first row
    if (button != null) {
      button.setOnAction(handler);
    }
  }

  /**
   * Sets the event handler for the reset button.
   *
   * @param handler The event handler to be set.
   * @author bjberild
   */
  public void setResetButtonHandler(EventHandler<ActionEvent> handler) {
    resetButton.setOnAction(handler);
  }

  /**
   * Sets the event handler for the switch start button.
   *
   * @param handler The event handler to be set.
   * @author bjberild
   */
  public void setSwitchStartButtonEventHandler(EventHandler<ActionEvent> handler) {
    switchStartButton.setOnAction(handler);
  }

  /**
   * Sets the event handler for the return button.
   *
   * @param handler The event handler to be set.
   * @author bjberild
   */
  public void setReturnButtonHandler(EventHandler<ActionEvent> handler) {
    returnButton.setOnAction(handler);
  }

  public void setDisableResetButton(Boolean bool) {
    resetButton.setDisable(bool);
  }

  public void setDisableSwitchStartButton(Boolean bool) {
    switchStartButton.setDisable(bool);
  }

  /**
   * Creates a cell for the Connect Four grid.
   *
   * @return A Circle representing a cell in the grid.
   * @author bjberild
   */
  private Circle createCell() {
    Circle cell = new Circle(30);
    cell.setFill(Color.WHITE);
    cell.setStroke(Color.BLACK);
    return cell;
  }

  /**
   * Updates the cell at the specified row and column with the given piece.
   *
   * @param row The row index of the cell to update.
   * @param col The column index of the cell to update.
   * @param piece The ConnectFourPiece to place in the cell.
   * @author bjberild
   */
  public void updateCell(int row, int col, ConnectFourPiece piece) {
    Circle cell = (Circle) getNodeFromGridPane(grid, col, row+1); // Adjust for button row
    if (piece == ConnectFourPiece.PLAYER_1) {
      cell.setFill(Color.RED);
    } else if (piece == ConnectFourPiece.PLAYER_2) {
      cell.setFill(Color.YELLOW);
    }
  }

  private javafx.scene.Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
    for (javafx.scene.Node node : gridPane.getChildren()) {
      if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
        return node;
      }
    }
    return null;
  }

  /**
   * Returns the main layout of the game view.
   *
   * @return The main layout pane.
   * @author bjberild
   */
  public Pane getView() {
    VBox gridbox = new VBox(10, grid);
    gridbox.setAlignment(Pos.CENTER);

    BorderPane layout = new BorderPane();

    VBox topBox = new VBox(10, currentPlayerLabel);
    topBox.setAlignment(Pos.CENTER);
    HBox buttonBox = new HBox(10, resetButton, switchStartButton);
    buttonBox.setAlignment(Pos.CENTER);
    topBox.getChildren().add(buttonBox);
    layout.setTop(topBox);

    layout.setCenter(gridbox);

    VBox bottomBox = new VBox(10, returnButton);
    bottomBox.setAlignment(Pos.CENTER);
    bottomBox.setPadding(new Insets(10));
    layout.setBottom(bottomBox);
    layout.setStyle("-fx-background-color: #87CEEB;");
    return layout;
  }
}