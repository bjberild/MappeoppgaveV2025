package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.ArrayList;
import java.util.List;

public class ConnectFourBoard {
  private final List<List<ConnectFourPiece>> columns;
  private final int rows;

  public ConnectFourBoard(int rows, int columns) {
    this.rows = rows;
    this.columns = new ArrayList<>();

    for (int i = 0; i < columns; i++) {
      this.columns.add(new ArrayList<>());
    }
  }

  public ConnectFourBoard() {
    this.rows = 6;
    this.columns = new ArrayList<>();

    for (int i = 0; i < 7; i++) {
      this.columns.add(new ArrayList<>());
    }
  }

  public ConnectFourPiece getCell(int x, int y) {
    if ((x < 0 || x >= getColumns())) {
      throw new IllegalArgumentException("Invalid X-ordinate: " + x);
    }
    if ((y < 0 || y >= getRows())) {
      throw new IllegalArgumentException("Invalid Y-ordinate: " + y);
    }

    List<ConnectFourPiece> column = columns.get(x);

    if (column.size() > y) {
      return column.get(y);
    } else {
      return null;
    }
  }
  /*
    * Add a piece to the board in the given column index
    *
    * @param x The column index
    * @return true if the player has won, false otherwise
   */
  public boolean move(int x, ConnectFourPiece player) {
    if ((x < 0 || x >= getColumns())) {
      throw new IllegalArgumentException("Invalid X-ordinate: " + x);
    }

    List<ConnectFourPiece> column = columns.get(x);

    if (column.size() >= this.rows) {
      throw new IllegalArgumentException("That column is full");
    }

    column.add(player);

    System.out.println("Added " + player + " to column " + (x+1) + ", row " + (column.size()));

    return checkWin(x, column.size() - 1, player);
  }

  /* Check if the player has won by checking a line of 4 pieces
   * in a given direction (xDiff, yDiff)
   */
  private boolean checkLine(int x1, int y1, int xDiff, int yDiff, ConnectFourPiece player) {
    for (int i = 0; i < 4; ++i) {
      int x = x1 + (xDiff * i);
      int y = y1 + (yDiff * i);

      if (x < 0 || x > columns.size() - 1) {
        return false;
      }

      if (y < 0 || y > rows - 1) {
        return false;
      }

      if (player != getCell(x, y)) {
        return false;
      }
    }

    return true;
  }
  /*   * Check if the player has won by checking all possible lines
   * (vertical, horizontal, leading diagonal, trailing diagonal)
   */
  private boolean checkWin(int x, int y, ConnectFourPiece player) {
    // Vertical line
    if (checkLine(x, y, 0, -1, player)) {
      return true;
    }

    for (int offset = 0; offset < 4; ++offset) {
      // Horizontal line
      if (checkLine(x - 3 + offset, y, 1, 0, player)) {
        return true;
      }

      // Leading diagonal
      if (checkLine(x - 3 + offset, y + 3 - offset, 1, -1, player)) {
        return true;
      }

      // Trailing diagonal
      if (checkLine(x - 3 + offset, y - 3 + offset, 1, 1, player)) {
        return true;
      }
    }

    return false;
  }


  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns.size();
  }

  public int getCurrentRow (int column) {
    if (column < 0 || column >= getColumns()) {
      throw new IllegalArgumentException("Invalid column index: " + column);
    }
    return columns.get(column).size();
  }

  public void reset() {
    for (List<ConnectFourPiece> column : columns) {
      column.clear();
    }
  }
}
