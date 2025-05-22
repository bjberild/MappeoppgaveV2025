package edu.ntnu.idi.idatt.mappeoppgavev2025.controller;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourBoard;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourPiece;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.ConnectFourGameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ConnectFourController {
  private final ConnectFourGameView view;
  private final ConnectFourBoard board;
  private ConnectFourPiece currentPlayer;
  private boolean winCon = false;
  private boolean finished = false;

  public ConnectFourController(ConnectFourGameView view, ConnectFourBoard board) {
    this.view = view;
    this.board = board;
    this.currentPlayer = ConnectFourPiece.PLAYER_1;

    // Register event handlers for each button
    for (int col = 0; col < board.getColumns(); col++) {
      int column = col; // Capture column index for lambda
      view.setDropButtonEventHandler(column, e -> {
        handleMove(column);
        if (!finished) {
          view.setDisableResetButton(false);
          view.setDisableSwitchStartButton(true);
        }
      });
    }

    view.setResetButtonHandler(e -> {
      resetGame();
    });

    view.setSwitchStartButtonEventHandler(e -> {
      currentPlayer = (currentPlayer == ConnectFourPiece.PLAYER_1) ? ConnectFourPiece.PLAYER_2 : ConnectFourPiece.PLAYER_1;
      view.setCurrentPlayerLabel(currentPlayer.getPlayerName());
    });
  }

  public void addReturnButtonHandler(EventHandler<ActionEvent> handler) {
    view.setReturnButtonHandler(handler);
  }

  public void handleMove(int col) {
    if (finished) {
      System.out.println("Game is already finished. No more moves allowed.");
      return;
    }
    try {
      winCon = board.move(col, currentPlayer);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return; // Exit if the move is invalid
    }
    view.updateCell(board.getRows()-board.getCurrentRow(col), col, currentPlayer);
    if (winCon) {
      // Handle win condition
      finished = true;
      view.setWinnerLabel(currentPlayer.getPlayerName());
      view.setDisableResetButton(false);
      System.out.println("Player " + currentPlayer + " wins!");
    }
    else {
      switchPlayer();
    }
  }

  public void resetGame() {
      board.reset();
      view.clearBoard();
      currentPlayer = ConnectFourPiece.PLAYER_1;
      view.setCurrentPlayerLabel(currentPlayer.getPlayerName());
      view.setDisableResetButton(true);
      view.setDisableSwitchStartButton(false);
      finished = false;
  }

  private void switchPlayer() {
    currentPlayer = (currentPlayer == ConnectFourPiece.PLAYER_1) ? ConnectFourPiece.PLAYER_2 : ConnectFourPiece.PLAYER_1;
    view.setCurrentPlayerLabel(currentPlayer.getPlayerName());
  }

  public int getRows() {
    return board.getRows();
  }

  public int getColumns() {
    return board.getColumns();
  }

  public Scene getScene() {
    Pane viewPane = view.getView();
    return new Scene(viewPane, 800, 600);
  }

  public Pane getView() {
    return view.getView();
  }
}