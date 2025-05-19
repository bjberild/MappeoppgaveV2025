package edu.ntnu.idi.idatt.mappeoppgavev2025.controller;

import java.nio.file.Path;
import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.CsvPlayerPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;

public class PlayerController {
    private final BoardGame game;

    public PlayerController(BoardGame game) {
        this.game = game;
    }

    public void savePlayer(Path csvFile) throws PlayerPersistenceException {
        List<Player> players = game.getPlayers();
        new CsvPlayerPersistence().save(csvFile, players);
    }

    public void loadPlayers(Path csvFile) throws PlayerPersistenceException {
        List<Player> loaded = new CsvPlayerPersistence().load(csvFile);
        game.getPlayers().clear();
        for (Player p : loaded) {
            game.addPlayer(p);
        }
    }
}
