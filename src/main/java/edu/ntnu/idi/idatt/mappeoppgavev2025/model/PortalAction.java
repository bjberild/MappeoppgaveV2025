package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.Optional;

/**
 * A {@link TileAction} that teleports a {@link Player} from their current tile
 * to a designated destination tile.
 * <p>
 * When executed, the player's position is updated to the destination, and an
 * informational message is returned indicating the move.
 * </p>
 * 
 * @author StianDolerud
 */
public class PortalAction implements TileAction {

    private final Tile destination;

    /**
     * Constructs a new PortalAction that will move players to the given destination tile.
     *
     * @param destination the tile to which players will be teleported
     * @author StianDolerud
     */
    public PortalAction(Tile destination) {
        this.destination = destination;
    }

    /**
     * Returns the destination tile of this portal action.
     *
     * @return the {@link Tile} that players will be moved to
     * @author StianDolerud
     */
    public Tile getDestinationTile() {
        return destination;
    }

    /**
     * Executes the portal action on the specified player.
     * <p>
     * The player is moved from their current tile to the destination tile.  An
     * {@link Optional} containing a descriptive message of the move is returned.
     * </p>
     *
     * @param player the {@link Player} to teleport
     * @return an {@link Optional} of a descriptive move message, never empty
     * @author StianDolerud
     */
    @Override
    public Optional<String> execute(Player player) {
        int from = player.getCurrentTile().getId();
        player.setCurrentTile(destination);
        return Optional.of(
            String.format(
                "player %s climbed from %d to %d",
                player.getName(),
                from,
                destination.getId()
            )
        );
    }
}