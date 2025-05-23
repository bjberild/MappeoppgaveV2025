package edu.ntnu.idi.idatt.mappeoppgavev2025.view.board;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import javafx.scene.Node;

/**
 * Grensesnitt for fabrikk som lager visuelle representasjoner (celler) av {@link Tile}-objekter.
 * <p>
 * Implementasjoner av dette grensesnittet skal returnere en {@link Node} som representerer en celle
 * for en gitt brikke (tile) med spesifisert bredde og høyde.
 *
 * @author StianDolerud
 */
public interface TileCellFactory {

    /**
     * Lager en visuell celle for gitt {@link Tile}-objekt.
     *
     * @param tile      brikken som skal representeres
     * @param cellWidth ønsket bredde på cellen
     * @param cellHeight ønsket høyde på cellen
     * @return en {@link Node} som representerer cellen for brikken
     * @author StianDolerud
     */
    Node createCell(Tile tile, double cellWidth, double cellHeight);
}