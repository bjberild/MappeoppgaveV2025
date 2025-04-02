package edu.ntnu.idi.idatt.mappeoppgavev2025;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;


public class GameFactory {
    
    private static final Map<String, Supplier<BoardGame>> gameRegistry = new HashMap<>();

    static {
        gameRegistry.put("LadderGame", BoardGame::new);
    }

    public static BoardGame createGame(String gameKey) {
        Supplier<BoardGame> gameSupplier = gameRegistry.get(gameKey);
        if (gameSupplier != null) {
            return gameSupplier.get();
    }
    throw new IllegalArgumentException("No game registered " + gameKey);
    }
}
