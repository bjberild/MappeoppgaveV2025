package edu.ntnu.idi.idatt.mappeoppgavev2025.view;


import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.TileCellFactory;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

public class BoardView extends Region {
    private static final int COLUMNS = 10;
    private static final int ROWS = 10;

    private final GridPane grid;
    private final TileCellFactory cellFactory;
    private final Map<Player, ImageView> tokenMap = new HashMap<>();


    public BoardView(Board board, TileCellFactory cellFactory, List<Player> players) {
        this.cellFactory = cellFactory;
        grid             = new GridPane();

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
            cell.getStyleClass().add("tile");
            cell.setId("tile-" + current.getId());

            int gridCol = leftToRight ? col : COLUMNS - 1 - col;
            grid.add(cell, gridCol, row);

            col++;
            if (col == COLUMNS) {
                col = 0;
                row--;
                leftToRight = !leftToRight;
            }
            current = current.getNextTile();
        }

        for (Player p : players) {
            String tokenName = p.getToken().trim();
            String path      = "/edu/ntnu/idi/idatt/mappeoppgavev2025/images/tokens/" + tokenName + ".png";
            InputStream in   = getClass().getResourceAsStream(path);
            if (in == null) {
                throw new IllegalArgumentException("Token image not found: " + path);
            }
            ImageView tokenIcon = new ImageView(new Image(in));
            tokenIcon.setFitWidth(30);
            tokenIcon.setFitHeight(30);
            tokenMap.put(p, tokenIcon);

            StackPane startPane = lookupCellPane(p.getCurrentTile().getId());
            startPane.getChildren().add(tokenIcon);

        }
        getChildren().add(grid);
    }

    @Override
    protected void layoutChildren() {
        grid.resizeRelocate(0, 0, getWidth(), getHeight());
    }

    private StackPane lookupCellPane(int tileId) {
        Node n = grid.lookup("#tile-" + tileId);
        if (!(n instanceof StackPane)) {
            throw new IllegalStateException("Tile cell is not a StackPane: " + n);
        }
        return (StackPane) n;
    }

    public void movePlayerToken(Player p, int fromId, int toID) {
        ImageView token = tokenMap.get(p);
        StackPane from  = lookupCellPane(fromId);
        StackPane to    = lookupCellPane(toID);
        if (token != null) {
            from.getChildren().remove(token);
            to.getChildren().add(token);
        }
    }
    
}
