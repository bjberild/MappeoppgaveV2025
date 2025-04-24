package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class LadderBoardGameView {

    public static GridPane createBoardGrid(Board board) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        int columns = 10;
        int rows = 10;

        Tile current = board.getStartTile();
        int row = rows - 1;
        int col = 0;
        boolean leftToRight = true;

        while (current != null && row >= 0) {
            Label tileLabel = new Label(String.valueOf(current.getId()));
            tileLabel.setMinSize(40, 40);
            tileLabel.setStyle("-fx-border-color: black; -fx-background-color: lightgreen; -fx-alignment: center;");

            if (leftToRight) {
                grid.add(tileLabel, col, row);
            } else {
                grid.add(tileLabel, columns - 1 - col, row);
            }

            col++;
            if (col >= columns) {
                col = 0;
                row--;
                leftToRight = !leftToRight;
            }

            current = current.getNextTile();
        }
        return grid;
    }

}
