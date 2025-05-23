package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.Optional;

/**
 * Represents a single tile on the game board.  Tiles can be linked in sequence
 * and may have an associated {@link TileAction} that triggers when a player lands on it.
 * <p>
 * Each tile has a unique identifier and optionally knows its next tile in the sequence.
 * </p>
 * 
 * @author StianDolerud
 */
public class Tile {

    private final int id;
    private Tile nextTile;
    private TileAction action = null;

    /**
     * Constructs a new Tile with the given identifier.
     *
     * @param id the unique identifier for this tile
     * @author StianDolerud
     */
    public Tile(int id) {
        this.id = id;
    }

    /**
     * Triggers the action associated with this tile, if any.
     *
     * @param player the {@link Player} landing on this tile
     * @return an {@link Optional} containing the action message if executed, or empty if no action
     * @author StianDolerud
     */
    public Optional<String> triggerAction(Player player) {
        if (action != null) {
            return action.execute(player);
        }
        return Optional.empty();
    }

    /**
     * Sets the action to be performed when a player lands on this tile.
     *
     * @param action the {@link TileAction} to associate with this tile
     * @author StianDolerud
     */
    public void setAction(TileAction action) {
        this.action = action;
    }

    /**
     * Returns the unique identifier of this tile.
     *
     * @return the tile's id
     * @author StianDolerud
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the next tile in the sequence.
     *
     * @return the next {@link Tile}, or null if this is the final tile
     * @author StianDolerud
     */
    public Tile getNextTile() {
        return nextTile;
    }

    /**
     * Sets the next tile in the sequence.
     *
     * @param nextTile the {@link Tile} to follow this one
     * @author StianDolerud
     */
    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }

    /**
     * Checks if this tile is the final one (i.e., has no next tile).
     *
     * @return true if this is the last tile on the board, false otherwise
     * @author StianDolerud
     */
    public boolean isFinalTile() {
        return nextTile == null;
    }

    /**
     * Returns the action currently assigned to this tile.
     *
     * @return the {@link TileAction}, or null if none is set
     * @author StianDolerud
     */
    public TileAction getAction() {
        return action;
    }
}