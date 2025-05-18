package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.DefaultTileCellFactory;
import javafx.scene.Node;

public class SnakesAndLaddersView extends GameView {
    public SnakesAndLaddersView(BoardGame game) {
        super(game);
    }
    @Override
    protected Node createBoardPane() {
        return new BoardView(
            game.getBoard(),
            new DefaultTileCellFactory()
        );
    }

    @Override
    protected String getTitleImage() {
        return "Snakes-And-Ladders.png";
    }

    
}
