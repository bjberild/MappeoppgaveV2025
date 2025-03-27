package edu.ntnu.idi.idatt.mappeoppgavev2025;

public class LadderAction implements TileAction {

  private final Tile destination;

  public LadderAction(Tile destination) {
    this.destination = destination;
  }

  @Override
  public void execute(Player player) {
    player.setCurrentTile(destination);
  }
}
