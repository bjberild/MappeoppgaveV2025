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

  public void move(int x, ConnectFourPiece player) {
    assert(x >= 0 && x < getColumns());

    List<ConnectFourPiece> column = columns.get(x);

    if (column.size() >= this.rows) {
      throw new IllegalArgumentException("That column is full");
    }

    column.add(player);
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns.size();
  }
}
