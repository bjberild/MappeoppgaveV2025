package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.controls.ControlPanelView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.dice.DiceView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.players.PlayerView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.title.TitleBar;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;




public abstract class GameView extends BackgroundPane {
    protected  final BoardGame game;

    public GameView(BoardGame game) {
        super("/edu/ntnu/idi/idatt/mappeoppgavev2025/images/Jungle-Background.png");
        this.game = game;
        layoutParts();
    }
    protected abstract Node createBoardPane();
    protected abstract String getTitleImage();

    private void layoutParts() {

        setTop(new TitleBar(getTitleImage()));

        setLeft(new PlayerView(game.getPlayers()));

        setRight(new DiceView());

        StackPane center = new StackPane(createBoardPane());
        center.setAlignment(Pos.CENTER);
        setCenter(center);

        setBottom(new ControlPanelView(game));
    }

    public Scene toScene(int w, int h) {
        return new Scene(this, w, h);
    }
}
