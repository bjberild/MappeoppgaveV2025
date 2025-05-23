package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.PlayerController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.DefaultPipPlacementStrategy;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.DefaultTileCellFactory;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens.ResourceTokenIconFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * GUI-visning for hovedspillet "Snakes and Ladders".
 * 
 * Utvider GameView og setter opp spesifikke komponenter for dette spillet,
 * inkludert et tilpasset BoardView og en returknapp for å gå tilbake til hovedmenyen.
 *
 * @author StianDolerud
 */
public class SnakesAndLaddersView extends GameView {
    /** Hovedpanelet for visning av spillebrettet. */
    private BoardView boardView;
    /** Knapp for å gå tilbake til hovedmenyen. */
    private final Button returnButton = new Button("← Back to Main Menu");

    /**
     * Oppretter et nytt visningspanel for "Snakes and Ladders".
     *
     * @param ctrl Kontrollobjekt for spillere og spill-logikk
     */
    public SnakesAndLaddersView(PlayerController ctrl) {
        super(ctrl);
        Node oldBottom = getBottom();
        var bottomBox = new VBox(10, oldBottom, returnButton);
        bottomBox.setPadding(new Insets(10));
        setBottom(bottomBox);
    }

    /**
     * Oppretter og returnerer panelet for spillebrettet, med riktige fabrikker for celler, tokens og pip-strategi.
     *
     * @return Node som representerer spillebrettet
     */
    @Override
    protected Node createBoardPane() {
        var game = ctrl.getGame();
        boardView = new BoardView(
            game.getBoard(),
            new DefaultTileCellFactory(),
            game.getPlayers(),
            new ResourceTokenIconFactory(),
            new DefaultPipPlacementStrategy()
        );
        return boardView;
    }

    /**
     * Returnerer filnavnet på tittelbildet til visningen.
     *
     * @return Sti til tittelbilde
     */
    @Override
    protected String getTitleImage() {
        return "Snakes-And-Ladders.png";
    }

    /**
     * Henter hovedpanelet for spillebrettet.
     *
     * @return BoardView-objektet som viser brettet
     */
    public BoardView getBoardView() {
        return boardView;
    }

    /**
     * Setter en event-handler på returknappen ("Back to Main Menu").
     *
     * @param handler Handler for ActionEvent når brukeren trykker på returknappen
     */
    public void setOnReturn(EventHandler<ActionEvent> handler) {
        returnButton.setOnAction(handler);
    }
}