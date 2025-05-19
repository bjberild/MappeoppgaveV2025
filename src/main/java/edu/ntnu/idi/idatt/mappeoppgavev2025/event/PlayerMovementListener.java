package edu.ntnu.idi.idatt.mappeoppgavev2025.event;

import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardView;

public class PlayerMovementListener implements GameEventListener {
    private final BoardView boardView;
    private final List<Player> players;

    public PlayerMovementListener(BoardView boardView, List<Player> players) {
        this.boardView = boardView;
        this.players   = players;
    }

    @Override
    public void onGameEvent(String message) {
        if (message.startsWith("Player ") && message.contains(" moved from tile ")) {
            String[] parts = message.split(" ");
            String playerName = parts[1];
            int from = Integer.parseInt(parts[5]);
            int to   = Integer.parseInt(parts[7]);

            players.stream()
                   .filter(p -> p.getName().equals(playerName))
                   .findFirst()
                   .ifPresent(p -> boardView.movePlayerToken(p, from, to));

            
        }
    }
    
}
