package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

/**
 * Represents a player in the board game.
 * <p>
 * A Player has a {@link #name}, a {@link #token} (e.g., color or icon identifier),
 * and occupies a {@link Tile} on the game board.  Players can be moved from tile
 * to tile, triggering any associated {@link TileAction}s upon arrival.
 * </p>
 * 
 * @author StianDolerud
 */
public class Player {

    private final String name;
    private final String token;
    private Tile currentTile;

    /**
     * Constructs a Player with the given name and token.
     *
     * @param name  the display name of the player
     * @param token the token identifier (e.g., color or icon) for this player
     * @author StianDolerud
     */
    public Player(String name, String token) {
        this.name = name;
        this.token = token;
    }

    /**
     * Constructs a Player with the given name and a default token.
     *
     * @param name the display name of the player
     * @author StianDolerud
     */
    public Player(String name) {
        this(name, "DefaultToken");
    }

    /**
     * Returns the token identifier for this player.
     *
     * @return the player's token
     * @author StianDolerud
     */
    public String getToken() {
        return token;
    }

    /**
     * Moves this player to the specified tile and triggers any action
     * associated with that tile.
     *
     * @param tile the Tile to move the player to
     * @see Tile#triggerAction(Player)
     * @author StianDolerud
     */
    public void moveToTile(Tile tile) {
        currentTile = tile;
        currentTile.triggerAction(this);
    }

    /**
     * Returns the display name of this player.
     *
     * @return the player's name
     * @author StianDolerud
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current tile occupied by this player.
     *
     * @return the current {@link Tile}, or <code>null</code> if not set
     * @author StianDolerud
     */
    public Tile getCurrentTile() {
        return currentTile;
    }

    /**
     * Sets the current tile of this player without triggering any action.
     * <p>
     * Use {@link #moveToTile(Tile)} to both set the tile and execute its action.
     * </p>
     *
     * @param currentTile the Tile to set as the player's current position
     * @author StianDolerud
     */
    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }
}