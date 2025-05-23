package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConnectFourBoardTest {
  ConnectFourBoard board;

  @BeforeEach
  void setUp() {
    board = new ConnectFourBoard(6, 7);
  }

  @Test
  void getCell_emptyAndFilled() {
    assertNull(board.getCell(0, 0));
    board.move(0, ConnectFourPiece.PLAYER_1);
    assertEquals(ConnectFourPiece.PLAYER_1, board.getCell(0, 0));
    assertNull(board.getCell(0, 1));
  }

  @Test
  void getCell_invalidCoordinates() {
    assertThrows(IllegalArgumentException.class, () -> board.getCell(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> board.getCell(0, -1));
    assertThrows(IllegalArgumentException.class, () -> board.getCell(7, 0));
    assertThrows(IllegalArgumentException.class, () -> board.getCell(0, 6));
  }
  @Test
  void move_addPieceAndFullColumn() {
    // First three moves: no win
    for (int i = 0; i < 3; i++) {
      assertFalse(board.move(0, ConnectFourPiece.PLAYER_1));
    }
    // Fourth move: should win
    assertTrue(board.move(0, ConnectFourPiece.PLAYER_1));
    // Fill up the rest of the column (no more wins)
    for (int i = 0; i < 2; i++) {
      board.move(0, ConnectFourPiece.PLAYER_1);
    }
    // Now the column is full, should throw
    assertThrows(IllegalArgumentException.class, () -> board.move(0, ConnectFourPiece.PLAYER_2));
  }

  @Test
  void move_invalidColumn() {
    assertThrows(IllegalArgumentException.class, () -> board.move(-1, ConnectFourPiece.PLAYER_1));
    assertThrows(IllegalArgumentException.class, () -> board.move(7, ConnectFourPiece.PLAYER_1));
  }

  @Test
  void getRowsAndColumns() {
    assertEquals(6, board.getRows());
    assertEquals(7, board.getColumns());
  }

  @Test
  void getCurrentRow() {
    assertEquals(0, board.getCurrentRow(0));
    board.move(0, ConnectFourPiece.PLAYER_1);
    assertEquals(1, board.getCurrentRow(0));
    board.move(0, ConnectFourPiece.PLAYER_2);
    assertEquals(2, board.getCurrentRow(0));
    assertThrows(IllegalArgumentException.class, () -> board.getCurrentRow(-1));
    assertThrows(IllegalArgumentException.class, () -> board.getCurrentRow(7));
  }

  @Test
  void reset() {
    board.move(0, ConnectFourPiece.PLAYER_1);
    board.move(1, ConnectFourPiece.PLAYER_2);
    board.reset();
    for (int col = 0; col < board.getColumns(); col++) {
      for (int row = 0; row < board.getRows(); row++) {
        assertNull(board.getCell(col, row));
      }
    }
  }

  @Test
  void winCondition_vertical() {
    for (int i = 0; i < 3; i++) {
      assertFalse(board.move(0, ConnectFourPiece.PLAYER_1));
    }
    assertTrue(board.move(0, ConnectFourPiece.PLAYER_1));
  }

  @Test
  void winCondition_horizontal() {
    for (int i = 0; i < 3; i++) {
      board.move(i, ConnectFourPiece.PLAYER_1);
    }
    assertTrue(board.move(3, ConnectFourPiece.PLAYER_1));
  }

  @Test
  void winCondition_diagonal() {
    // Set up a diagonal from (0,0) to (3,3)
    board.move(0, ConnectFourPiece.PLAYER_1); // (0,0)
    board.move(1, ConnectFourPiece.PLAYER_2);
    board.move(1, ConnectFourPiece.PLAYER_1); // (1,1)
    board.move(2, ConnectFourPiece.PLAYER_2);
    board.move(2, ConnectFourPiece.PLAYER_2);
    board.move(2, ConnectFourPiece.PLAYER_1); // (2,2)
    board.move(3, ConnectFourPiece.PLAYER_2);
    board.move(3, ConnectFourPiece.PLAYER_2);
    board.move(3, ConnectFourPiece.PLAYER_2);
    assertTrue(board.move(3, ConnectFourPiece.PLAYER_1)); // (3,3)
  }
}