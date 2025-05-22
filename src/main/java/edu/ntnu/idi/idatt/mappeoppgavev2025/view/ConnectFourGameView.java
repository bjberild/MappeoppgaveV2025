package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.ConnectFourController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourPiece;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.event.ActionEvent;

public class ConnectFourGameView {
  private final GridPane grid;
  private final int ROWS = 6;
  private final int COLUMNS = 7;

  public ConnectFourGameView() {
    this.grid = new GridPane();
    initializeGrid();
  }

  private void initializeGrid() {
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(5);
    grid.setVgap(5);

    HBox buttonRow = new HBox(5);
    buttonRow.setAlignment(Pos.CENTER);

    for (int col = 0; col < COLUMNS; col++) {
      Button dropButton = new Button("Drop");
      dropButton.setId("dropButton" + Integer.toString(col));
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

  public void setButtonEventHandler(int column, EventHandler<ActionEvent> handler) {
    Button button = (Button) getNodeFromGridPane(grid, column, 0); // Buttons are in the first row
    if (button != null) {
      button.setOnAction(handler);
    }
  }

  private Circle createCell() {
    Circle cell = new Circle(30);
    cell.setFill(Color.WHITE);
    cell.setStroke(Color.BLACK);
    return cell;
  }

  public void updateCell(int row, int col, ConnectFourPiece piece) {
    Circle cell = (Circle) getNodeFromGridPane(grid, col, row);
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

  public Scene getScene() {
    VBox layout = new VBox(10, grid);
    layout.setAlignment(Pos.CENTER);

    return new Scene(layout, 800, 600);
  }
}