package edu.ntnu.idi.idatt.mappeoppgavev2025.view.board;

import javafx.geometry.Pos;


/**
 * Default implementation of {@link PipPlacementStrategy} that provides
 * predefined positions for up to five tokens on a single tile.
 * <p>
 * Uses a fixed lookup table of {@link Pos} values to evenly distribute
 * token icons within the tile area.
 * </p>
 * 
 * @author StianDolerud
 */
public class DefaultPipPlacementStrategy implements PipPlacementStrategy {
     private static final Pos[] PIPS = {
        Pos.CENTER,
        Pos.CENTER_LEFT, Pos.CENTER_RIGHT,
        Pos.TOP_LEFT, Pos.TOP_RIGHT, Pos.BOTTOM_CENTER,
        Pos.TOP_LEFT, Pos.TOP_RIGHT, Pos.BOTTOM_LEFT, Pos.BOTTOM_RIGHT,
        Pos.TOP_LEFT, Pos.TOP_RIGHT, Pos.BOTTOM_LEFT, Pos.BOTTOM_RIGHT, Pos.CENTER
    };

       /**
     * Computes an array of positions for rendering {@code numTokens} icons
     * on a tile. Supports between 1 and 5 tokens.
     *
     * @param numTokens the number of tokens to place on the tile
     * @return an array of {@link Pos} values indicating where each token
     *         should be drawn
     * @throws IllegalArgumentException if {@code numTokens} is less than 1
     *                                  or greater than 5
     */


    @Override
    public Pos[] positionsFor(int numTokens) {
        Pos[] out = new Pos[numTokens];
        for (int i = 0; i < numTokens; i++) {
            out[i] = PIPS[switch (numTokens) {
                case 1 -> 0;
                case 2 -> 1 + i;
                case 3 -> i;
                case 4 -> 3 + i;
                case 5 -> 7 + i;
                default -> throw new IllegalArgumentException("This amount of tokens is invalid: " + numTokens);
            }];
        }
        return out;
    }
}