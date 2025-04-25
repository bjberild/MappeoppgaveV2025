package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BoardView extends Pane {
    private static final int COLUMNS = 10;
    private static final int ROWS = 10;

    public BoardView(Board board) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        Tile current = board.getStartTile();
        int row = ROWS - 1;
        int col = 0;
        boolean leftToRight = true;

        while (current != null && row >= 0) {
            Label tileLabel = new Label(String.valueOf(current.getId()));
            tileLabel.setMinSize(40, 40);
            tileLabel.setPrefSize(80, 80);
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
    
}
