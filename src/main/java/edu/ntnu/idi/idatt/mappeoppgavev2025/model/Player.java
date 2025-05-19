package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

public class Player {

  private final String name;
  private final String token;
  private Tile currentTile;

  public Player(String name, String token) {
    this.name = name;
    this.token = token;
  }

 

  public Player(String name) {
    this(name, "DefaultToken");
  }

  public String getToken() {
    return token;
  }

  public void moveToTile(Tile tile) {
    currentTile = tile;
    currentTile.triggerAction(this);
  }

  public String getName() {
    return name;
  }

  public Tile getCurrentTile() {
    return currentTile;
  }

  public void setCurrentTile(Tile currentTile) {
    this.currentTile = currentTile;
  }

}
