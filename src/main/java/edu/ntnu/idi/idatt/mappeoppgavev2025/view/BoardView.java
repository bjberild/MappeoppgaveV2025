package edu.ntnu.idi.idatt.mappeoppgavev2025.view;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.TileCellFactory;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens.TokenIconFactory;
import javafx.geometry.Pos;
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
    private final TokenIconFactory tokenFactory;
    private final List<Player> players;
    private final Map<Player, ImageView> tokenMap = new HashMap<>();
    private final Map<Integer, StackPane> tilePaneMap = new HashMap<>();

    private static final Pos[] PIP_POSITIONS = {
        Pos.CENTER,
        Pos.CENTER_LEFT, Pos.CENTER_RIGHT,
        Pos.TOP_LEFT, Pos.TOP_RIGHT, Pos.BOTTOM_CENTER,
        Pos.TOP_LEFT, Pos.TOP_RIGHT, Pos.BOTTOM_LEFT, Pos.BOTTOM_RIGHT,
        Pos.TOP_LEFT, Pos.TOP_RIGHT, Pos.BOTTOM_LEFT, Pos.BOTTOM_RIGHT, Pos.CENTER
    };
    
    public BoardView(Board board, 
                     TileCellFactory cellFactory,
                     List<Player> players, 
                     TokenIconFactory tokenFactory) {
        this.cellFactory  = cellFactory;   
        this.tokenFactory = tokenFactory;
        this.players      = players;
        grid              = new GridPane();

        

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
        
        Tile current        = board.getStartTile();
        int row             = ROWS - 1;
        int col             = 0;
        boolean leftToRight = true;

        while (current != null && row >= 0) {
            StackPane cellPane = (StackPane) cellFactory.createCell(current, 0, 0);
            cellPane.getStyleClass().add("tile");
            cellPane.setId("tile-" + current.getId());
            tilePaneMap.put(current.getId(), cellPane);

            int gridCol = leftToRight ? col : COLUMNS - 1 - col;
            grid.add(cellPane, gridCol, row);
            
            col++;
            if (col == COLUMNS) {
                col = 0;
                row--;
                leftToRight = !leftToRight;
            }
            current = current.getNextTile();
        }

        for (Player p : players) {
            ImageView icon = tokenFactory.createIcon(p.getToken().trim());
            icon.setFitWidth(100);
            icon.setFitHeight(100);
            tokenMap.put(p, icon);
        }

        tilePaneMap.keySet().stream()
            .filter(tileId -> !getPlayersOnTile(tileId).isEmpty())
            .forEach(this::refreshTile);
            
        getChildren().add(grid);
       
    }

    @Override
    protected void layoutChildren() {
        grid.resizeRelocate(0, 0, getWidth(), getHeight());
    }

    private List<Player> getPlayersOnTile(int tileId) {
        return players.stream()
                      .filter(p -> p.getCurrentTile() != null)
                      .filter(p -> p.getCurrentTile().getId() == tileId)
                      .collect(Collectors.toList());
    }

    public void refreshTile(int tileId) {
        StackPane pane = tilePaneMap.get(tileId);
        if (pane == null) return;

        pane.getChildren().removeIf(node -> node instanceof ImageView && tokenMap.containsValue(node));

        var onTile = getPlayersOnTile(tileId);
        for (int i = 0; i < onTile.size(); i++) {
            Player p = onTile.get(i);
            ImageView icon = tokenMap.get(p);
            pane.getChildren().add(icon);
            StackPane.setAlignment(icon, PIP_POSITIONS[getPipIndex(onTile.size(), i)]);
        }
    }


    private int getPipIndex(int numTokens, int idx) {
        switch (numTokens) {
            case 1: return 0;
            case 2: return idx == 0 ? 1 : 2;
            case 3: return idx;
            case 4: return idx + 3;
            case 5: return idx + 7;
            default: throw new IllegalArgumentException("This amount of tokens is invalid: " + numTokens);
        }
    } 
}
