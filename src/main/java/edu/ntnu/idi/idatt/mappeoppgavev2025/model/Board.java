package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

  private List<Tile> tiles;
  private final Random rand = new Random();

  //Creates a standard board with a given number of tiles
  public void initializeStandardBoard(int numTiles) {
    tiles = new ArrayList<>();
    for (int i = 1; i <= numTiles; i++) {
      Tile tile = new Tile(i);
      tiles.add(tile);
      if (i > 1) {
        tiles.get(i - 2).setNextTile(tile);
      }
    }
  }

  //Creates a certain number of random LadderAction Tiles to the board
  public void addLadderActionTiles(int numTiles) {
    for (int i = 0; i < numTiles; i++) {
      int startTileIndex = (rand.nextInt(tiles.size() - 2)) + 1;
      int endTileIndex = (rand.nextInt(tiles.size() - 2)) + 1;
      while (startTileIndex == endTileIndex) {
        endTileIndex = (rand.nextInt(tiles.size() - 2)) + 1;
      }
      Tile startTile = tiles.get(startTileIndex);
      TileAction action = new LadderAction(tiles.get(endTileIndex));
      startTile.setAction(action);
    }
  }

  //This makes sure that the player starts at the first tile
  public Tile getStartTile() {
    if (tiles != null && !tiles.isEmpty()) {
      return tiles.getFirst();
    }
    return null;
  }


  //This method returns the tile that is a given amount of steps away from the current tile
  public Tile getTileAfter(Tile current, int steps) {
    int currentIndex = tiles.indexOf(current);
    int destinationIndex = currentIndex + steps;
    if (destinationIndex >= tiles.size()) {
      destinationIndex = tiles.size() - 1;
    }
    return tiles.get(destinationIndex);
  }


}
