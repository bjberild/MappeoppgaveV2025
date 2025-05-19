package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.Optional;

public class PortalAction implements TileAction {

    private final Tile destination;
    public PortalAction(Tile destination) {
        this.destination = destination;
    }

    public Tile getDestinationTile() {
        return destination;
    }

    @Override
    public Optional<String> execute(Player player) {
        int from = player.getCurrentTile().getId();
        player.setCurrentTile(destination);
        return Optional.of(
            String.format("player %s climbed from %d to %d",
                player.getName(), from, destination.getId()));
    }
}
