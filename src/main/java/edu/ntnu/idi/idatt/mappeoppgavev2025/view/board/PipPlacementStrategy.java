package edu.ntnu.idi.idatt.mappeoppgavev2025.view.board;

import javafx.geometry.Pos;

public interface PipPlacementStrategy {
    Pos[] positionsFor(int numTokens);
    
}
