package edu.ntnu.idi.idatt.mappeoppgavev2025.view.board;

import javafx.geometry.Pos;

public class DefaultPipPlacementStrategy implements PipPlacementStrategy {
     private static final Pos[] PIPS = {
        Pos.CENTER,
        Pos.CENTER_LEFT, Pos.CENTER_RIGHT,
        Pos.TOP_LEFT, Pos.TOP_RIGHT, Pos.BOTTOM_CENTER,
        Pos.TOP_LEFT, Pos.TOP_RIGHT, Pos.BOTTOM_LEFT, Pos.BOTTOM_RIGHT,
        Pos.TOP_LEFT, Pos.TOP_RIGHT, Pos.BOTTOM_LEFT, Pos.BOTTOM_RIGHT, Pos.CENTER
    };


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