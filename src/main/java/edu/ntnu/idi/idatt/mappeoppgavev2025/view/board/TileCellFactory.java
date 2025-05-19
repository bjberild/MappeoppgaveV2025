package edu.ntnu.idi.idatt.mappeoppgavev2025.view.board;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import javafx.scene.Node;


public interface TileCellFactory {
    Node createCell(Tile tile, double cellWidth, double cellHeight);
}
