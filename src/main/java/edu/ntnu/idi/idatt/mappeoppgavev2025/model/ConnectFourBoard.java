package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Connect Four board with a specified number of rows and columns.
 * The board is represented as a list of columns, where each column is a list of pieces.
 *
 * @author bjberild
 */
public class ConnectFourBoard {
  private final List<List<ConnectFourPiece>> columns;
  private final int rows;

  /**
   * Constructor for a Connect Four board with a specified number of rows and columns.
   *
   * @param rows    The number of rows
   * @param columns The number of columns
   * @author bjberild
   */
  public ConnectFourBoard(int rows, int columns) {
    this.rows = rows;
    this.columns = new ArrayList<>();

    for (int i = 0; i < columns; i++) {
      this.columns.add(new ArrayList<>());
    }
  }

  /**
   * Default constructor for a Connect Four board with 6 rows and 7 columns.
   *
   * @author bjberild
   */
  public ConnectFourBoard() {
    this.rows = 6;
    this.columns = new ArrayList<>();

    for (int i = 0; i < 7; i++) {
      this.columns.add(new ArrayList<>());
    }
  }

  /**
   * Get the piece in the given cell
   *
   * @param x The x-coordinate
   * @param y The y-coordinate
   * @return The piece in the cell, or null if the cell is empty
   * @throws IllegalArgumentException if the coordinates are invalid
   *
   * @author bjberild
   */
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
    return checkWin(x, column.size() - 1, player);
  }

  /**
   * Check if the player has won by checking a line of 4 pieces in a given direction.
   *
   * @param x1     The starting x-coordinate
   * @param y1     The starting y-coordinate
   * @param xDiff  The x-difference for the line direction
   * @param yDiff  The y-difference for the line direction
   * @param player The player to check for
   * @return true if the player has a line of 4 pieces, false otherwise
   * @author bjberild
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

  /**
   *    * Check if the player has won by checking all possible lines
   * (vertical, horizontal, leading diagonal, trailing diagonal)
   *
   * @param x The x-coordinate of the last piece placed
   * @param y The y-coordinate of the last piece placed
   * @param player The player to check for
   * @return true if the player has won, false otherwise
   * @author bjberild
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

  /**
   * Get the current row of a given column
   *
   * @param column The column index
   * @return The current row in the given column
   * @throws IllegalArgumentException if the column index is invalid
   *
   * @author bjberild
   */
  public int getCurrentRow(int column) {
    if (column < 0 || column >= getColumns()) {
      throw new IllegalArgumentException("Invalid column index: " + column);
    }
    return columns.get(column).size();
  }

  /**
   * Reset the board to its initial state
   *
   * @author bjberild
   */
  public void reset() {
    for (List<ConnectFourPiece> column : columns) {
      column.clear();
    }
  }
}
