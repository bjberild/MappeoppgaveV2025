package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class Board {

  private String name;
  private List<Tile> tiles;
  private final Random rand = new Random();

// --- name property ---------------------------------

  public void setName(String n) { 
    name = n; 
  }

  public String getName() {
    return name; 
  }

  // --- fill-list and lookup methods ----------------

  /**
   * Returns an unmodifiable list of all tiles on the board, in invenor order.
   */

  public List<Tile> getAllTiles() {
    return Collections.unmodifiableList(tiles);
  }
  /**
   * Finds a tile by its ID.
   */

  public Optional<Tile> getTileById(int id) {
    return tiles.stream().filter(t -> t.getId() == id).findFirst();
  }

  // --- incremental building for JSON deserialization ---

  /**
   * Prepares an empty board with capacity for {@code size} tiles.
   * After calling this, you can add tiles using {@link #addTile(Tile)} to populate.
   */
  public void createEmpty(int size) {
    tiles = new ArrayList<>(size);
  }
  /**
   * Adds a tile to the board. The tile is added to the end of the list.
   * If the list is empty, it will be created.
   * @param tile The tile to add.
   */
  public void addTile(Tile tile) {
    if (tiles == null) {
      tiles = new ArrayList<>();
    }
    if (!tiles.isEmpty()) {
      tiles.get(tiles.size() - 1).setNextTile(tile);
    }
    tiles.add(tile);
  }

// --- standard board creation -----------------
  /**
   * Creates a standard board with a given number of tiles.
   * The tiles are numbered from 1 to {@code numTiles}.
   * The first tile is the start tile, and the last tile is the end tile.
   * @param numTiles The number of tiles to create.
   */
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


  // --- navigation methods -----------------

  /**
   * Returns the tile that is the start tile of the board.
   * The start tile is the first tile in the list.
   * @return The start tile, or null if the list is empty.
   */
  public Tile getStartTile() {
    if (tiles != null && !tiles.isEmpty()) {
      return tiles.get(0);
    }
    return null;
  }

  /**
   * Returns the tile that is the end tile of the board.
   * The end tile is the last tile in the list.
   * @return The end tile, or null if the list is empty.
   */
  public Tile getTileAfter(Tile current, int steps) {
    int currentIndex = tiles.indexOf(current);
    int destinationIndex = currentIndex + steps;
    if (destinationIndex >= tiles.size()) {
      destinationIndex = tiles.size() - 1;
    }
    return tiles.get(destinationIndex);
  }

  public void addPortalActionTiles(int numPortals) {
    Random rand = new Random();
    for (int i = 0; i < numPortals; i++) {
      int from = rand.nextInt(tiles.size() - 2) + 1;
      int to;
      do {
        to = rand.nextInt(tiles.size() - 1) + 1;
      } while (to == from);
      tiles.get(from).setAction(new PortalAction(tiles.get(to)));
    }
  }


  public void addFallTrapActionTiles(int numTraps) {
    Random rand = new Random();
    for (int i = 0; i < numTraps; i++) {
      int from = rand.nextInt(tiles.size() - 2) + 1;
      int to = rand.nextInt(tiles.size() - 1) + 1;
      while (to >= from) {
        to = rand.nextInt(tiles.size() - 1) + 1;
      }
      tiles.get(from).setAction(new FallTrapAction(tiles.get(to)));
    }
  }
}

