package edu.ntnu.idi.idatt.mappeoppgavev2025;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class BoardTest {

    
    @Test
    public void testInitializeStandardBoard() {
        Board board = new Board();
        board.initializeStandardBoard(90);


        Tile current = board.getStartTile();
        int count = 0;
        while (current != null) {
            count++;
            current = current.getNextTile();
        }
        assertEquals(90, count, "The board should have 90 tiles");

        // This checks that the last tile has the ID 90
        Tile last = board.getStartTile();
        while (last.getNextTile() != null) {
            last = last.getNextTile();
        }
        assertEquals(90, last.getId(), "The last tile should have id 90");
    }

    @Test
    public void testGetTileAfter() {
        Board board = new Board();
        board.initializeStandardBoard(90);
        Tile starterTile = board.getStartTile();

        Tile tileAfter5 = board.getTileAfter(starterTile, 5);
        assertEquals(6, tileAfter5.getId(), "The tile after 5 steps should have id 6");

        Tile tile85 = board.getTileAfter(starterTile, 85);
        Tile overshoot = board.getTileAfter(tile85, 10);
        assertEquals(90, overshoot.getId(), "Overshooting should return the final tile (ID 90)");

    }

    
}
