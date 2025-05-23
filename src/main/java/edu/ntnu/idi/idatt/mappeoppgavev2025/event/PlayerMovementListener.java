package edu.ntnu.idi.idatt.mappeoppgavev2025.event;

import java.util.List;
import java.util.regex.Pattern;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens.TokenAnimator;
import javafx.util.Duration;

/**
 * Listens for textual game events indicating player movement,
 * parses the messages to detect either step-by-step moves or teleport moves (portals/traps),
 * and delegates to a {@link TokenAnimator} to animate the corresponding movement on the board.
 * <p>
 * Recognizes two patterns:
 * <ul>
 *   <li>Step moves like "Player X moved from tile A to tile B".</li>
 *   <li>Teleport moves like "Player X climbed from A to B" or "Player X fell from A to B".</li>
 * </ul>
 * </p>
 * 
 * @author StianDolerud
 */
public class PlayerMovementListener implements GameEventListener {

    /**
     * Regex to match a normal move message.
     * Groups:
     * <ol>
     *   <li>Player name</li>
     *   <li>Origin tile id</li>
     *   <li>Destination tile id</li>
     * </ol>
     */
    private static final Pattern STEP_MOVE =
        Pattern.compile("(?i)player\\s+(.+?)\\s+moved from tile\\s+(\\d+)\\s+to(?: tile)?\\s+(\\d+)");

    /**
     * Regex to match a teleport move (portal or trap) message.
     * Groups:
     * <ol>
     *   <li>Player name</li>
     *   <li>Origin tile id</li>
     *   <li>Destination tile id</li>
     * </ol>
     */
    private static final Pattern TELEPORT_MOVE =
        Pattern.compile("(?i)player\\s+(.+?)\\s+(?:climbed|fell) from\\s+(\\d+)\\s+to\\s+(\\d+)");

    private final TokenAnimator animator;
    private final List<Player> players;
    private final BoardView boardView;

    /**
     * Constructs a listener that will animate player movements on the given board view.
     *
     * @param boardView the {@link BoardView} displaying the game board
     * @param players the list of players whose movements will be animated
     */
    public PlayerMovementListener(BoardView boardView, List<Player> players) {
        this.boardView = boardView;
        this.players = players;
        this.animator = new TokenAnimator(boardView, Duration.millis(150));
    }

    /**
     * Called for each game event message.
     * <p>
     * If the message matches a teleport pattern, performs a teleport animation;
     * otherwise, if it matches a step move pattern, performs a step-by-step walk animation.
     * Messages that do not match either pattern are ignored.
     * </p>
     *
     * @param message the raw event message describing a player's movement
     */
    @Override
    public void onGameEvent(String message) {
        var tpMatcher = TELEPORT_MOVE.matcher(message);
        if (tpMatcher.find()) {
            String playerName = tpMatcher.group(1);
            int fromTile = Integer.parseInt(tpMatcher.group(2));
            int toTile = Integer.parseInt(tpMatcher.group(3));
            players.stream()
                   .filter(p -> p.getName().equals(playerName))
                   .findFirst()
                   .ifPresent(p -> animator.animateTeleport(p, fromTile, toTile));
            return;
        }

        var stepMatcher = STEP_MOVE.matcher(message);
        if (stepMatcher.find()) {
            String playerName = stepMatcher.group(1);
            int fromTile = Integer.parseInt(stepMatcher.group(2));
            int toTile = Integer.parseInt(stepMatcher.group(3));
            players.stream()
                   .filter(p -> p.getName().equals(playerName))
                   .findFirst()
                   .ifPresent(p -> animator.animateWalk(p, fromTile, toTile));
        }
    }
}