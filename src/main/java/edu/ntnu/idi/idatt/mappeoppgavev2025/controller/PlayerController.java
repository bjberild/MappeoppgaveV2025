package edu.ntnu.idi.idatt.mappeoppgavev2025.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.CsvPlayerPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;

public class PlayerController {
    private final BoardGame game;

    public PlayerController(BoardGame game) {
        this.game = game;
    }

    public void loadPlayers(Path csvFile) throws PlayerPersistenceException {
        var loaded = new CsvPlayerPersistence().load(csvFile);
        game.getPlayers().clear();
       loaded.forEach(game::addPlayer);
    }

    public void savePlayers(Path csvFile) throws PlayerPersistenceException {
        new CsvPlayerPersistence().save(csvFile, game.getPlayers());
    }

    public void saveBoardAsJson(Path jsonFile) throws IOException {
        JsonObject boardJson = new GsonBoardPersistence().serialize(game.getBoard());
        String jsonText = new Gson().toJson(boardJson);
        Files.writeString(jsonFile, jsonText);
    }

    public List<Player> getPlayers() {
        return game.getPlayers();
    }

    public BoardGame getGame() {
        return game;
    }

    public void playOneRound() {
        game.playOneRound();
    }

    public void resetGame() {
        game.reset();
    }

    public void rollDice() {
        game.rollDice();
    }
   
}
