package edu.ntnu.idi.idatt.mappeoppgavev2025.event;

import java.util.List;
import java.util.regex.Pattern;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardView;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.tokens.TokenAnimator;
import javafx.util.Duration;


public class PlayerMovementListener implements GameEventListener {
    private static final Pattern STEP_MOVE =
            Pattern.compile("(?i)player\\s+(.+?)\\s+moved from tile\\s+(\\d+)\\s+to(?: tile)?\\s+(\\d+)");
    
    private static final Pattern TELEPORT_MOVE = 
            Pattern.compile("(?i)player\\s+(.+?)\\s+(?:climbed|fell) from\\s+(\\d+)\\s+to\\s+(\\d+)");

    private final TokenAnimator animator;
    private final List<Player> players;
    private final BoardView boardView;

    public PlayerMovementListener(BoardView boardView, List<Player> players) {
        this.players   = players;
        this.boardView = boardView;
        this.animator = new TokenAnimator(boardView, Duration.millis(150));
    }

   
    @Override
public void onGameEvent(String message) {

    var tpMatcher = TELEPORT_MOVE.matcher(message);
    if (tpMatcher.find()) {
        String playerName = tpMatcher.group(1);
        int fromTile      = Integer.parseInt(tpMatcher.group(2));
        int toTile       = Integer.parseInt(tpMatcher.group(3));

        players.stream()
               .filter(p -> p.getName().equals(playerName))
               .findFirst()
               .ifPresent(p -> animator.animateTeleport(p, fromTile, toTile));
        return;
    }


    var stepMatcher = STEP_MOVE.matcher(message);
    if (stepMatcher.find()) {
        String playerName = stepMatcher.group(1);
        int fromTile      = Integer.parseInt(stepMatcher.group(2));
        int toTile        = Integer.parseInt(stepMatcher.group(3));

        players.stream()
               .filter(p -> p.getName().equals(playerName))
               .findFirst()
               .ifPresent(p -> animator.animateWalk(p, fromTile, toTile));


        }

  
    }

}