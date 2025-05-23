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

/**
 * Controller responsible for loading, saving, and orchestrating
 * player and board data for a {@link BoardGame} instance.
 * <p>
 * Delegates persistence operations to {@link CsvPlayerPersistence}
 * and {@link GsonBoardPersistence}, and proxies user actions
 * to the underlying {@link BoardGame}.
 * </p>
 * 
 * @author StianDolerud
 */
public class PlayerController {
    private final BoardGame game;

    /**
   * Constructs a new PlayerController for the given game.
   *
   * @param game the {@link BoardGame} to control
   */

    public PlayerController(BoardGame game) {
        this.game = game;
    }

    /**
   * Loads players from a CSV file and adds them to the game,
   * clearing any existing players first.
   *
   * @param csvFile path to the CSV file containing "name,token" lines
   * @throws PlayerPersistenceException if loading fails or file is malformed
   */

    public void loadPlayers(Path csvFile) throws PlayerPersistenceException {
        var loaded = new CsvPlayerPersistence().load(csvFile);
        game.getPlayers().clear();
       loaded.forEach(game::addPlayer);
    }

     /**
   * Saves the current list of players in the game to a CSV file.
   *
   * @param csvFile path to write the players CSV
   * @throws PlayerPersistenceException if writing to file fails
   */
    public void savePlayers(Path csvFile) throws PlayerPersistenceException {
        new CsvPlayerPersistence().save(csvFile, game.getPlayers());
    }

     /**
   * Serializes the current board state to JSON and writes it to a file.
   *
   * @param jsonFile path where the board JSON will be saved
   * @throws IOException if an I/O error occurs during writing
   */
    public void saveBoardAsJson(Path jsonFile) throws IOException {
        JsonObject boardJson = new GsonBoardPersistence().serialize(game.getBoard());
        String jsonText = new Gson().toJson(boardJson);
        Files.writeString(jsonFile, jsonText);
    }

      /**
   * Retrieves the list of players currently registered in the game.
   *
   * @return an unmodifiable {@link List} of {@link Player}
   */
    public List<Player> getPlayers() {
        return game.getPlayers();
    }

    /**
   * Returns the underlying {@link BoardGame} instance.
   *
   * @return the {@link BoardGame} controlled by this controller
   */
    public BoardGame getGame() {
        return game;
    }

   /**
    * Advances the game by playing one full round:
    * <ol>
    *   <li>Starts the next turn</li>
    *   <li>Rolls the dice</li>
    * </ol>
    */

    public void playOneRound() {
        game.playOneRound();
    }

  /**
   * Resets the game to its initial state, placing all players
   * back on the start tile and clearing any winner.
   */
    public void resetGame() {
        game.reset();
    }

  /**
   * Resets the game to its initial state, placing all players
   * back on the start tile and clearing any winner.
   */
    public void rollDice() {
        game.rollDice();
    }
   
}
