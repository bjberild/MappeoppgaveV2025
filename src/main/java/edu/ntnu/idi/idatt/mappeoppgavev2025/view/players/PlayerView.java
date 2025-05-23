package edu.ntnu.idi.idatt.mappeoppgavev2025.view.players;

import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Viser oversikt over alle spillere i venstre del av GUI.
 * 
 * @author StianDolerud
 */
public class PlayerView extends VBox {
    /**
     * Oppretter spilleroversikten basert på en liste med spillere.
     *
     * @param players liste av spillere som skal vises
     */
    public PlayerView(List<Player> players) {
        setPadding(new Insets(20));
        setSpacing(20);
        Label title = new Label("Players: ");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        getChildren().add(title);
        players.forEach(p -> getChildren().add(new Label(p.getName())));
    }
}