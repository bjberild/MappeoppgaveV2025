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

public class SnakesAndLaddersView extends GameView {
    private BoardView boardView;
    private final Button returnButton = new Button("← Back to Main Menu");

    public SnakesAndLaddersView(PlayerController ctrl) {
        super(ctrl);
        Node oldBottom = getBottom();
        var bottomBox = new VBox(10, oldBottom, returnButton);
        bottomBox.setPadding(new Insets(10));
        setBottom(bottomBox);
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

    public void setOnReturn(EventHandler<ActionEvent> handler) {
        returnButton.setOnAction(handler);
    }
}
