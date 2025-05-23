package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.Optional;

/**
 * Represents a trap action on a tile that sends the player backward to a specified destination tile.
 * <p>
 * When executed, the player “falls” from their current tile to the <code>destination</code> tile,
 * and an optional descriptive message is returned.
 * </p>
 * 
 * @author StianDolerud
 */
public class FallTrapAction implements TileAction {

    private final Tile destination;

    /**
     * Constructs a FallTrapAction that moves a player to the given destination.
     *
     * @param destination the Tile the player will land on when triggered
     * @author StianDolerud
     */
    public FallTrapAction(Tile destination) {
        this.destination = destination;
    }

    /**
     * Returns the tile to which this trap will send the player.
     *
     * @return the destination Tile of this trap
     * @author StianDolerud
     */
    public Tile getDestinationTile() {
        return destination;
    }

    /**
     * Executes the trap action on the specified player, moving them to {@link #getDestinationTile()}.
     * Returns an Optional containing a message describing the fall, e.g.:
     * <pre>{@code "player Alice fell from 20 to 2"}</pre>
     *
     * @param player the Player to apply the trap to
     * @return an Optional with a descriptive message if the action occurred, or empty if no action
     * @author StianDolerud
     */
    @Override
    public Optional<String> execute(Player player) {
        int from = player.getCurrentTile().getId();
        player.setCurrentTile(destination);
        return Optional.of(
            String.format("player %s fell from %d to %d",
                player.getName(), from, destination.getId()));
    }
}