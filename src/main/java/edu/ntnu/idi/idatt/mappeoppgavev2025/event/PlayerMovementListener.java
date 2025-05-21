package edu.ntnu.idi.idatt.mappeoppgavev2025.event;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.view.BoardView;

public class PlayerMovementListener implements GameEventListener {
    private static final Pattern MOVE_PATTERN =
            Pattern.compile("(?i)player\\s+(\\w+)\\s+(?:moved from tile|climbed from|fell from)\\s+(\\d+)\\s+to(?: tile)?\\s+(\\d+)");


    private final BoardView boardView;
    private final List<Player> players;

    public PlayerMovementListener(BoardView boardView, List<Player> players) {
        this.boardView = boardView;
        this.players   = players;
    }

    @Override
    public void onGameEvent(String message) {
        Matcher m = MOVE_PATTERN.matcher(message);
        if (!m.find()) return;

        String playerName = m.group(1);
        int fromTile = Integer.parseInt(m.group(2));
        int toTile = Integer.parseInt(m.group(3));

        players.stream()
                .filter(player -> player.getName().equals(playerName))
                .findFirst()
                .ifPresent(p -> {
                    boardView.refreshTile(fromTile);
                    boardView.refreshTile(toTile);
                });
    }
}
