package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.TileCellFactory;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class BoardView extends Region {
    private static final int COLUMNS = 10;
    private static final int ROWS = 10;

    private final GridPane grid;
    private final TileCellFactory cellFactory;


    public BoardView(Board board, TileCellFactory cellFactory) {
        this.cellFactory = cellFactory;
        grid  = new GridPane();

        for (int i = 0; i < COLUMNS; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / COLUMNS);
            grid.getColumnConstraints().add(cc);
        }
        for (int i = 0; i < ROWS; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0 / ROWS);
            grid.getRowConstraints().add(rc);
        }
        
        Tile current = board.getStartTile();
        int row = ROWS - 1;
        int col = 0;
        boolean leftToRight = true;

        while (current != null && row >= 0) {
        Node cell = cellFactory.createCell(current, 0, 0);
            if (leftToRight) {
                grid.add(cell, col, row);
            } else {
                grid.add(cell, COLUMNS - 1 - col, row);
            }
            col++;
            if (col >= COLUMNS) {
                col = 0;
                row--;
                leftToRight = !leftToRight;
            }
            current = current.getNextTile();
        }
        getChildren().add(grid);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        grid.resizeRelocate(0, 0, getWidth(), getHeight());
    }
    
}
