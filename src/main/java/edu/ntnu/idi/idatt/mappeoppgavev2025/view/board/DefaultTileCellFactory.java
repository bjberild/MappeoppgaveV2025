package edu.ntnu.idi.idatt.mappeoppgavev2025.view.board;

import java.io.InputStream;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.FallTrapAction;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.PortalAction;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Tile;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.TileAction;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Default implementation of {@link TileCellFactory} that renders a {@link Tile}
 * into a styled {@link StackPane}.  If the tile has a {@link PortalAction} or
 * {@link FallTrapAction}, the corresponding icon is loaded and displayed beneath
 * the tile number.
 * <p>
 * The returned node will:
 * <ul>
 *   <li>fill its layout bounds</li>
 *   <li>have the CSS style class "tile"</li>
 *   <li>optionally include an "action-icon" graphic</li>
 *   <li>display the tile's ID in bold text</li>
 * </ul>
 * </p>
 * 
 * @author StianDolerud
 */
public class DefaultTileCellFactory implements TileCellFactory {
     /**
   * Creates a JavaFX {@link Node} representing the given {@code tile}, sized to
   * fit within the allotted width and height.
   * <p>
   * The cell will include:
   * <ul>
   *   <li>a background styled via the "tile" CSS class</li>
   *   <li>an optional icon for portals or traps, loaded from resources</li>
   *   <li>a bold label showing the tile's numeric ID</li>
   * </ul>
   * </p>
   *
   * @param tile the {@link Tile} to render
   * @param width the width available for the cell (ignored in this implementation)
   * @param height the height available for the cell (ignored in this implementation)
   * @return a {@link StackPane} node representing the tile
   * @throws IllegalStateException if the action icon resource cannot be loaded
   */
    @Override
    public Node createCell(Tile tile, double w, double h) {
        StackPane cell = new StackPane();
        cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        cell.getStyleClass().add("tile");
        
        // Determine if we need to show a portal or trap icon
        TileAction act = tile.getAction();
        String iconPath = null; 
        if (act instanceof PortalAction) {
            iconPath = "/edu/ntnu/idi/idatt/mappeoppgavev2025/images/portal_2.png";
        } else if (act instanceof FallTrapAction) {
            iconPath = "/edu/ntnu/idi/idatt/mappeoppgavev2025/images/trap.gif";
        }

        // Load and add the icon if present
        if (iconPath != null) {
            InputStream in = getClass().getResourceAsStream(iconPath);
            if (in == null) {
                throw new IllegalStateException("Could not load resource: " + iconPath);
            }
            ImageView icon = new ImageView(new Image(in));
            icon.getStyleClass().add("action-icon");
            icon.setPreserveRatio(true);
            icon.fitWidthProperty().bind(cell.widthProperty().multiply(0.6));
            icon.fitHeightProperty().bind(cell.heightProperty().multiply(0.6));
            cell.getChildren().add(icon);
        }

        // Display the tile ID
        Label label = new Label(String.valueOf(tile.getId()));
        label.setStyle("-fx-font-weight: bold;");
        cell.getChildren().add(label);

        return cell;
    }

}
