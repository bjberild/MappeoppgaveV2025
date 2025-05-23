package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Board;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.PipPlacementStrategy;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.TileCellFactory;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens.TokenIconFactory;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

/**
 * GUI-komponent for å vise brettet, brikker og spillerikoner.
 * 
 * Oppretter et GridPane-basert brett og håndterer grafisk plassering
 * av alle brikker og spillere på ruter. 
 * 
 * @author StianDolerud
 */
public class BoardView extends Region {
    
    private static final int COLUMNS = 10;
    private static final int ROWS = 10;

    private final GridPane grid;
    private final TileCellFactory cellFactory;
    private final TokenIconFactory tokenFactory;
    private final PipPlacementStrategy pipStrategy;
    private final List<Player> players;
    private final Map<Player, ImageView> tokenMap = new HashMap<>();
    private final Map<Integer, StackPane> tilePaneMap = new HashMap<>();

    /**
     * Lager et nytt BoardView med tilhørende GUI-objekter og plasserer ruter/spillere.
     *
     * @param board Brettet som skal vises
     * @param cellFactory Factory for visuelle celler
     * @param players Liste med alle spillere
     * @param tokenFactory Factory for spillerikoner/brikker
     * @param pipStrategy Plasseringsstrategi for flere brikker på samme rute
     */
    public BoardView(Board board, 
                     TileCellFactory cellFactory,
                     List<Player> players, 
                     TokenIconFactory tokenFactory,
                     PipPlacementStrategy pipStrategy) {
        this.cellFactory  = cellFactory;   
        this.tokenFactory = tokenFactory;
        this.pipStrategy  = pipStrategy;
        this.players      = players;
        grid              = new GridPane();

        // Sett opp grid
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

        // Plasser alle ruter/celler på gridet i slange-mønster
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

        // Opprett brikkeikoner for spillere
        for (Player p : players) {
            ImageView icon = tokenFactory.createIcon(p.getToken().trim());
            icon.setFitWidth(100);
            icon.setFitHeight(100);
            tokenMap.put(p, icon);
        }
        // Plasser alle spillere på brettet
        tilePaneMap.keySet().stream()
            .filter(this::hasPlayersOnTile)
            .forEach(this::refreshTile);
            
        getChildren().add(grid);
    }

    @Override
    protected void layoutChildren() {
        grid.resizeRelocate(0, 0, getWidth(), getHeight());
    }

    /**
     * Sjekker om det finnes en eller flere spillere på gitt rute.
     *
     * @param tileId ID for ruten
     * @return true hvis det er spillere der, ellers false
     */
    private boolean hasPlayersOnTile(int tileId) {
        return !getPlayersOnTile(tileId).isEmpty();
    }

    /**
     * Henter alle spillere som står på en gitt rute.
     *
     * @param tileId ID for ruten
     * @return Liste over spillere på ruten
     */
    private List<Player> getPlayersOnTile(int tileId) {
        return players.stream()
                      .filter(p -> p.getCurrentTile() != null)
                      .filter(p -> p.getCurrentTile().getId() == tileId)
                      .collect(Collectors.toList());
    }

    /**
     * Henter ImageView-objektet (ikonet) for en spiller.
     *
     * @param player Spilleren
     * @return ImageView-ikonet, eller null hvis ikke funnet
     */
    public ImageView getTokenIcon(Player player) {
        return tokenMap.get(player);
    }

    /**
     * Flytter en brikke til angitt rute på brettet visuelt.
     *
     * @param icon Ikonet for brikken som skal flyttes
     * @param tileId ID på ruten hvor brikken skal plasseres
     */
    public void playerTokenOnTile(ImageView icon, int tileId) {
        tilePaneMap.values().forEach(pane -> pane.getChildren().remove(icon));
        StackPane dest = tilePaneMap.get(tileId);
        if (dest != null) {
            dest.getChildren().add(icon);
            StackPane.setAlignment(icon, Pos.CENTER);
        }
    } 

    /**
     * Oppdaterer grafikken på en rute: fjerner gamle brikker og plasserer
     * alle spillere på riktig posisjon på ruten.
     *
     * @param tileId ID på ruten som skal oppdateres
     */
    public void refreshTile(int tileId) {
        StackPane pane = tilePaneMap.get(tileId);
        if (pane == null) return;

        pane.getChildren().removeIf(node -> node instanceof ImageView && tokenMap.containsValue(node));

        var onTile = getPlayersOnTile(tileId);
        var positions = pipStrategy.positionsFor(onTile.size());
        for (int i = 0; i < onTile.size(); i++) {
            Player p = onTile.get(i);
            ImageView icon = tokenMap.get(p);
            pane.getChildren().add(icon);
            StackPane.setAlignment(icon, positions[i]);
        }
    }
}