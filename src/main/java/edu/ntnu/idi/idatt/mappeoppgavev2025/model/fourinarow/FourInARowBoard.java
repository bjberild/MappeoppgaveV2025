package edu.ntnu.idi.idatt.mappeoppgavev2025.model.fourinarow;

public class FourInARowBoard {
  private final int columns = 7;
  private final int rows = 6;
  private int[][] board;

  public FourInARowBoard() {
    board = new int[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        board[i][j] = 0;
      }
    }
  }

  public boolean dropPiece(int column, int player) {
    if (column < 0 || column >= columns) {
      return false;
    }
    for (int i = rows - 1; i >= 0; i--) {
      if (board[i][column] == 0) {
        board[i][column] = player;
        return true;
      }
    }
    return false;
  }
}
