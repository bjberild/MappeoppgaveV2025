package edu.ntnu.idi.idatt.mappeoppgavev2025.event;

import java.util.List;
import java.util.regex.Pattern;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens.TokenAnimator;
import javafx.util.Duration;


public class PlayerMovementListener implements GameEventListener {
    private static final Pattern STEP_MOVE =
            Pattern.compile("(?i)player\\s+(\\w+)\\s+(?:moved from tile|climbed from|fell from)\\s+(\\d+)\\s+to(?: tile)?\\s+(\\d+)");
    
    private static final Pattern TELEPORT_MOVE = 
            Pattern.compile("(?i)player\\s+(\\w+)\\s+(?:climbed from|fell from)\\s+(\\d+)\\s+to\\s+(\\d+)");

    private final TokenAnimator animator;
    private final List<Player> players;

    public PlayerMovementListener(BoardView boardView, List<Player> players) {
        this.players   = players;
        this.animator = new TokenAnimator(boardView, Duration.millis(150));
    }

    @Override
    public void onGameEvent(String message) {
        var t = TELEPORT_MOVE.matcher(message);
        if (t.find()) {
            String name = t.group(1);
            int dest = Integer.parseInt(t.group(3));
            players.stream()
                  .filter(p -> p.getName().equals(name))
                  .findFirst()
                  .ifPresent(p -> animator.animateTeleport(p, dest));
            return;
        }

        var s = STEP_MOVE.matcher(message);
        if (!s.find()) return;

        String playerName = s.group(1);
        int fromTile = Integer.parseInt(s.group(2));
        int toTile   = Integer.parseInt(s.group(3));

        players.stream()
                .filter(player -> player.getName().equals(playerName))
                .findFirst()
                .ifPresent(p -> animator.animateWalk(p, fromTile, toTile));
    }
}
    