package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.Optional;

/**
 * Defines a behavior that can be executed when a {@link Player} lands on a {@link Tile}.
 * <p>
 * Implementations encapsulate different effects (e.g., teleportation, traps) by
 * moving the player to another tile and optionally returning a descriptive message.
 * </p>
 * 
 * @author StianDolerud
 */
public interface TileAction {

    /**
     * Executes the action on the given player.
     * <p>
     * Implementations should update the player's state (for example, change their current tile)
     * and may return an informational message about what occurred.
     * </p>
     *
     * @param player the {@link Player} upon whom the action is performed
     * @return an {@link Optional} containing a message describing the result, or empty if no message
     * @author StianDolerud
     */
    Optional<String> execute(Player player);
}