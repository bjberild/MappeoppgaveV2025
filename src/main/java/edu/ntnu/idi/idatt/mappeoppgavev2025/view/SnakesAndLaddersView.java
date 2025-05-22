package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.controller.PlayerController;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.DefaultPipPlacementStrategy;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.board.DefaultTileCellFactory;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens.ResourceTokenIconFactory;
import javafx.scene.Node;

public class SnakesAndLaddersView extends GameView {
    private BoardView boardView;

    public SnakesAndLaddersView(PlayerController ctrl) {
        super(ctrl);
    }
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

    @Override
    protected String getTitleImage() {
        return "Snakes-And-Ladders.png";
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
