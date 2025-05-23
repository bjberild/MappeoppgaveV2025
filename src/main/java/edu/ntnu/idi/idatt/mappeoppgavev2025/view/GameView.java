package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.PlayerController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.controls.ControlPanelView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.dice.DiceView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.players.PlayerView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.title.TitleBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Abstrakt hovedklasse for hovedvisningen av et brettspill.
 * 
 * Setter opp standard layout med tittel, spilleroversikt, terningvisning, kontrollpanel og brett.
 * Subklasser må selv implementere metode for å levere brettpanelet og tittelbilde.
 *
 * @author StianDolerud
 */
public abstract class GameView extends BackgroundPane {
    /** Kontrollobjekt for spillere og game flow. */
    protected final PlayerController ctrl;

    /**
     * Oppretter et nytt GameView med standard bakgrunn og layout.
     *
     * @param ctrl Controller for spill-logikk og spillere
     */
    public GameView(PlayerController ctrl) {
        super("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.png");
        this.ctrl = ctrl;
        setPadding(new Insets(10));
        layoutParts();
    }

    /**
     * Returnerer brettpanelet som skal vises i midten.
     *
     * @return Et JavaFX-node for selve brettet
     */
    protected abstract Node createBoardPane();

    /**
     * Returnerer filnavnet på tittelbildet (logo/topbanner).
     *
     * @return Sti til tittelbilde som vises øverst
     */
    protected abstract String getTitleImage();

    /**
     * Setter opp og legger til alle hovedkomponenter i GUI-et:
     * Tittel øverst, spillerliste venstre, terning høyre, brett i midten, kontrollpanel nederst.
     */
    private void layoutParts() {
        setTop(new TitleBar(getTitleImage()));
        setLeft(new PlayerView(ctrl.getPlayers()));
        setRight(new DiceView(ctrl));

        StackPane center = new StackPane(createBoardPane());
        center.setAlignment(Pos.CENTER);
        setCenter(center);
        BorderPane.setMargin(center, new Insets(0, 20, 0, 20));

        setBottom(new ControlPanelView(ctrl));
    }

    /**
     * Lager en JavaFX Scene basert på denne hovedvisningen.
     *
     * @param w Bredde på vinduet
     * @param h Høyde på vinduet
     * @return Scene-objekt som kan brukes i en Stage
     */
    public Scene toScene(int w, int h) {
        return new Scene(this, w, h);
    }
}