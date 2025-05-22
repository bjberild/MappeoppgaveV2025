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

public class DefaultTileCellFactory implements TileCellFactory {
    @Override
    public Node createCell(Tile tile, double w, double h) {
        StackPane cell = new StackPane();
        cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        cell.getStyleClass().add("tile");

        TileAction act = tile.getAction();
        String iconPath = null; 
        if (act instanceof PortalAction) {
            iconPath = "/edu/ntnu/idi/idatt/mappeoppgavev2025/images/portal_2.png";
        } else if (act instanceof FallTrapAction) {
            iconPath = "/edu/ntnu/idi/idatt/mappeoppgavev2025/images/trap.gif";
        }

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

        Label label = new Label(String.valueOf(tile.getId()));
        label.setStyle("-fx-font-weight: bold;");
        cell.getChildren().add(label);

        return cell;
    }

}
