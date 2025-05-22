package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.DefaultPipPlacementStrategy;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.DefaultTileCellFactory;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens.ResourceTokenIconFactory;
import javafx.scene.Node;

public class SnakesAndLaddersView extends GameView {
    private BoardView boardView;

    public SnakesAndLaddersView(BoardGame game) {
        super(game);
    }
    @Override
    protected Node createBoardPane() {
        boardView = new BoardView(
            game.getBoard(),
            new DefaultTileCellFactory(),
            game.getPlayers(),
            new ResourceTokenIconFactory(),
            new DefaultPipPlacementStrategy()
        );
        return boardView;
    }

    @Override
    protected String getTitleImage() {
        return "Snakes-And-Ladders.png";
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
