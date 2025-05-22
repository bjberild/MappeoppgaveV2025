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

public class ConnectFourGameView {
  private final int ROWS = 6;
  private final int COLUMNS = 7;
  private final GridPane grid;
  private final Button returnButton = new Button("Return to Main Menu");
  private final Label currentPlayerLabel = new Label("Current Player: Player 1");
  private final Button resetButton = new Button("Reset Game");
  private final Button switchStartButton = new Button("Switch Start Player");

  public ConnectFourGameView() {
    this.grid = new GridPane();
    resetButton.setDisable(true);
    initializeGrid();
    styleElements();
  }

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

  private void styleElements() {
    //returnButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;");
    currentPlayerLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000;");

    returnButton.setAlignment(Pos.CENTER);
    returnButton.setPadding(new Insets(10));

    currentPlayerLabel.setAlignment(Pos.CENTER);
    currentPlayerLabel.setPadding(new Insets(10));
  }

  public void setCurrentPlayerLabel(String player) {
    currentPlayerLabel.setText("Current Player: " + player);
  }

  public void setWinnerLabel(String winner) {
    currentPlayerLabel.setText("Winner: " + winner);
  }

  public void clearBoard() {
    for (int col = 0; col < COLUMNS; col++) {
      for (int row = 0; row < ROWS; row++) {
        Circle cell = (Circle) getNodeFromGridPane(grid, col, row+1); // Adjust for button row
        cell.setFill(Color.WHITE);
      }
    }
  }

  public void setDropButtonEventHandler(int column, EventHandler<ActionEvent> handler) {
    Button button = (Button) getNodeFromGridPane(grid, column, 0); // Buttons are in the first row
    if (button != null) {
      button.setOnAction(handler);
    }
  }

  public void setResetButtonHandler(EventHandler<ActionEvent> handler) {
    resetButton.setOnAction(handler);
  }

  public void setSwitchStartButtonEventHandler(EventHandler<ActionEvent> handler) {
    switchStartButton.setOnAction(handler);
  }

  public void setReturnButtonHandler(EventHandler<ActionEvent> handler) {
    returnButton.setOnAction(handler);
  }

  public void setDisableResetButton(Boolean bool) {
    resetButton.setDisable(bool);
  }

  public void setDisableSwitchStartButton(Boolean bool) {
    switchStartButton.setDisable(bool);
  }

  private Circle createCell() {
    Circle cell = new Circle(30);
    cell.setFill(Color.WHITE);
    cell.setStroke(Color.BLACK);
    return cell;
  }

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