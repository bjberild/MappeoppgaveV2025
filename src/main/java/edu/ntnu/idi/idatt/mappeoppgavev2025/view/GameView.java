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




public abstract class GameView extends BackgroundPane {
    protected final PlayerController ctrl;

    public GameView(PlayerController ctrl) {
        super("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.png");
        this.ctrl = ctrl;
        setPadding(new Insets(10));
        layoutParts();
    }
    protected abstract Node createBoardPane();
    protected abstract String getTitleImage();

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

    public Scene toScene(int w, int h) {
        return new Scene(this, w, h);
    }
}
