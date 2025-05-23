package edu.ntnu.idi.idatt.mappeoppgavev2025.controller;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourBoard;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.ConnectFourPiece;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.ConnectFourGameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.logging.Logger;

/**
 * ConnectFourController handles the game logic and user interactions for the Connect Four game.
 * It manages the game state, player turns, and updates the view accordingly.
 *
 * @author bjberild
 */
public class ConnectFourController {
  private static final Logger logger = Logger.getLogger(ConnectFourController.class.getName());

  private final ConnectFourGameView view;
  private final ConnectFourBoard board;
  private ConnectFourPiece currentPlayer;
  private boolean finished = false;


  /**
   * Constructor for ConnectFourController.
   * Initializes the controller with the game view and board.
   * Sets up event handlers for the game buttons.
   *
   * @param view  The view associated with the game.
   * @param board The game board.
   * @author bjberild
   */
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

    view.setResetButtonHandler(e -> resetGame());

    view.setSwitchStartButtonEventHandler(e -> {
      currentPlayer = (currentPlayer == ConnectFourPiece.PLAYER_1) ? ConnectFourPiece.PLAYER_2 : ConnectFourPiece.PLAYER_1;
      view.setCurrentPlayerLabel(currentPlayer.getPlayerName());
    });
  }

  /**
   * Adds an event handler for the return button.
   *
   * @param handler The event handler to be added.
   */
  public void addReturnButtonHandler(EventHandler<ActionEvent> handler) {
    view.setReturnButtonHandler(handler);
  }

  /**
   * Handles the move made by the player.
   * Updates the board and view accordingly.
   *
   * @param col The column where the player wants to drop their piece.
   * @author bjberild
   */
  public void handleMove(int col) {
    boolean winCon;
    if (finished) {
      logger.info("Game is already finished. No more moves allowed.");
      return;
    }
    try {
      winCon = board.move(col, currentPlayer);
    } catch (IllegalArgumentException e) {
      logger.warning(e.getMessage());
      return; // Exit if the move is invalid
    }
    view.updateCell(board.getRows()-board.getCurrentRow(col), col, currentPlayer);
    if (winCon) {
      // Handle win condition
      finished = true;
      view.setWinnerLabel(currentPlayer.getPlayerName());
      view.setDisableResetButton(false);
      logger.info("Player " + currentPlayer.getPlayerName() + " wins!");
    }
    else {
      switchPlayer();
    }
  }

  /**
   * Resets the game to its initial state.
   * Clears the board and view, and resets the current player.
   *
   * @author bjberild
   */
  public void resetGame() {
      board.reset();
      view.clearBoard();
      currentPlayer = ConnectFourPiece.PLAYER_1;
      view.setCurrentPlayerLabel(currentPlayer.getPlayerName());
      view.setDisableResetButton(true);
      view.setDisableSwitchStartButton(false);
      finished = false;
  }

  /**
   * Switches the current player.
   * Updates the view to reflect the current player's name.
   *
   * @author bjberild
   */
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