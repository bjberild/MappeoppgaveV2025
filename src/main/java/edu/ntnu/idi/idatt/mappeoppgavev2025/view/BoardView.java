package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class BoardView extends Region {
    private static final int COLUMNS = 10;
    private static final int ROWS = 10;
    private final GridPane grid;

    public BoardView(Board board) {
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
            Label tileLabel = new Label(String.valueOf(current.getId()));
            tileLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            tileLabel.setStyle("-fx-border-color: black; -fx-background-color: lightgreen; -fx-alignment: center;");
            if (leftToRight) {
                grid.add(tileLabel, col, row);
            } else {
                grid.add(tileLabel, COLUMNS - 1 - col, row);
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
