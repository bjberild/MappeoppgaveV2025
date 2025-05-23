package edu.ntnu.idi.idatt.mappeoppgavev2025.event;

import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardView;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Listens for game events that represent portal (climb) or trap (fall) actions,
 * parses the event message to extract source and destination tile IDs,
 * and temporarily highlights the corresponding tiles on the board.
 * <p>
 * Recognizes messages containing "climbed" (portal) or "fell" (trap),
 * expecting the format "player X climbed from A to B" or "player X fell from A to B".
 * </p>
 * 
 * @author StianDolerud
 */
public class TileHighlighter implements GameEventListener {

    private final BoardView boardView;

    /**
     * Constructs a TileHighlighter that will apply highlight styles
     * to tiles on the given {@link BoardView} when portal or trap events occur.
     *
     * @param boardView the BoardView instance on which tiles will be highlighted
     */
    public TileHighlighter(BoardView boardView) {
        this.boardView = boardView;
    }

    /**
     * Processes a game event message. If the message indicates a portal ("climbed")
     * or trap ("fell"), parses the source and destination tile IDs,
     * looks up the corresponding nodes in the board view,
     * applies a CSS style class for highlighting, and removes it after 5 seconds.
     *
     * @param message the game event message to process
     */
    @Override
    public void onGameEvent(String message) {
        String lower = message.toLowerCase();
        boolean isPortal = lower.contains("climbed");
        boolean isTrap   = lower.contains("fell");
        if (!isPortal && !isTrap) {
            return;
        }

        // Expecting "player X climbed from A to B" or "player X fell from A to B"
        String[] parts = message.split(" ");
        int from = Integer.parseInt(parts[4]);
        int to   = Integer.parseInt(parts[6]);

        Node source = boardView.lookup("#tile-" + from);
        Node dest   = boardView.lookup("#tile-" + to);
        if (source == null || dest == null) {
            return;
        }

        String styleClass = isPortal ? "highlight-portal" : "highlight-trap";
        source.getStyleClass().add(styleClass);
        dest.getStyleClass().add(styleClass);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> {
            source.getStyleClass().remove(styleClass);
            dest.getStyleClass().remove(styleClass);
        });
        pause.play();
    }
}