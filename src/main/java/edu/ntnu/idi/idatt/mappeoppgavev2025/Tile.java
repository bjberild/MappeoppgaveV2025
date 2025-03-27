package edu.ntnu.idi.idatt.mappeoppgavev2025;

public class Tile {

  private final int id;
  private Tile nextTile;
  private TileAction action = null;

  public Tile(int id) {
    this.id = id;
  }

  public void setAction(TileAction action) {
    this.action = action;
  }

  public int getId() {
    return id;
  }

  public Tile getNextTile() {
    return nextTile;
  }

  public void setNextTile(Tile nextTile) {
    this.nextTile = nextTile;
  }

  public boolean isFinalTile() {
    return nextTile == null;
  }
}