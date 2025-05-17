package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

public class LadderAction implements TileAction {

  private final Tile destination;

  public LadderAction(Tile destination) {
    this.destination = destination;
  }

  public Tile getDestinationTile() {
    return destination;
  }

  @Override
  public void execute(Player player) {
    player.setCurrentTile(destination);
  }
}
