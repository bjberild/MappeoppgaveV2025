package edu.ntnu.idi.idatt.mappeoppgavev2025.controller;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourBoard;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourPiece;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.ConnectFourGameView;
import javafx.scene.Scene;

public class ConnectFourController {
  private final ConnectFourGameView view;
  private final ConnectFourBoard board;
  private ConnectFourPiece currentPlayer;
  boolean winCon = false;

  public ConnectFourController(ConnectFourGameView view, ConnectFourBoard board) {
    this.view = view;
    this.board = board;
    this.currentPlayer = ConnectFourPiece.PLAYER_1;

    // Register event handlers for each button
    for (int col = 0; col < board.getColumns(); col++) {
      int column = col; // Capture column index for lambda
      view.setDropButtonEventHandler(column, e -> handleMove(column));
    }
  }

  public void handleMove(int col) {
    try {
      winCon = board.move(col, currentPlayer);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return; // Exit if the move is invalid
    }
    view.updateCell(board.getRows()-board.getCurrentRow(col), col, currentPlayer);
    if (winCon) {
      // Handle win condition
      System.out.println("Player " + currentPlayer + " wins!");
    }
    else {
      switchPlayer();
    }
  }
  /*
  public void resetGame() {
    board.reset();
    view.clearBoard();
    currentPlayer = ConnectFourPiece.PLAYER_1;
  }
  */

  private void switchPlayer() {
    currentPlayer = (currentPlayer == ConnectFourPiece.PLAYER_1) ? ConnectFourPiece.PLAYER_2 : ConnectFourPiece.PLAYER_1;
  }

  public int getRows() {
    return board.getRows();
  }

  public int getColumns() {
    return board.getColumns();
  }

  public Scene getScene() {
    return view.getScene();
  }
}