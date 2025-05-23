package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.ntnu.idi.idatt.mappeoppgavev2025.event.GameEventListener;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.CsvPlayerPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.GsonBoardPersistence;
import edu.ntnu.idi.idatt.mappeoppgavev2025.persistence.PlayerPersistenceException;

/**
 * Manages a turn‐based board game session, including loading boards and players,
 * handling turns, rolling dice, applying tile actions, and notifying listeners of events.
 * <p>
 * Supports both automatic board creation (with traps and portals) and loading from JSON,
 * as well as loading and saving players via CSV. Listeners can be registered to receive
 * textual game event notifications.
 * </p>
 * 
 * @author StianDolerud
 */
public class BoardGame {

  private final List<Player> players = new ArrayList<>();
  private final List<GameEventListener> listeners = new ArrayList<>();
  private Board board;
  private Dice dice;
  private Player winner;
  private int currentPlayerIndex = 0;

  /**
   * Loads a board definition from the specified JSON file and replaces the current board.
   * 
   * @param jsonFile the path to the JSON file describing the board
   * @throws IOException              if an I/O error occurs reading the file
   * @throws IllegalArgumentException if the JSON is invalid
   */
  public void loadBoard(Path jsonFile) throws IOException {
    String text = Files.readString(jsonFile);
    JsonObject json = JsonParser.parseString(text).getAsJsonObject();
    Board loaded = new GsonBoardPersistence().deserialize(json);
    this.board = loaded;
  }

  /**
   * Loads players from the specified CSV file, clearing any existing players first.
   * 
   * @param csvFile the path to the CSV file containing player name and token pairs
   * @throws PlayerPersistenceException if loading or parsing the CSV fails
   */
  public void loadPlayers(Path csvFile) throws PlayerPersistenceException {
    var loaded = new CsvPlayerPersistence().load(csvFile);
    players.clear();
    for (Player p : loaded) {
      addPlayer(p);
    }
  }

  /**
   * Registers a listener that will receive textual notifications of game events.
   * 
   * @param listener the listener to notify on game events
   */
  public void addEventListener(GameEventListener listener) {
    listeners.add(listener);
  }

  /**
   * Creates a default standard board of 90 tiles, then randomly adds 3 traps and 3 portals.
   */
  public void createBoard() {
    board = new Board();
    board.initializeStandardBoard(90);
    board.addFallTrapActionTiles(3);
    board.addPortalActionTiles(3);
  }

  /**
   * Creates the specified number of dice for the game.
   * 
   * @param numberOfDice how many dice to roll each turn
   */
  public void createDice(int numberOfDice) {
    dice = new Dice(numberOfDice);
  }

  /**
   * Replaces the current board with the provided instance.
   * 
   * @param board the Board instance to use
   */
  public void setBoard(Board board) {
    this.board = board;
  }

  /**
   * Adds a player to the game and places them on the start tile.
   * 
   * @param player the Player to add
   */
  public void addPlayer(Player player) {
    player.setCurrentTile(board.getStartTile());
    players.add(player);
  }

  /**
   * Begins the next player's turn by notifying listeners to prompt for a dice roll.
   * If the game is already finished, the winner is announced instead.
   */
  public void startNextTurn() {
    if (isFinished()) {
      notifyEvent("The game is already finished. The winner is " + winner.getName());
      return;
    }

    Player currentPlayer = players.get(currentPlayerIndex);
    notifyEvent("Player " + currentPlayer.getName() + "'s turn. Please roll the dice.");
  }

  /**
   * Rolls the dice for the current player, moves them along the board,
   * triggers any tile action, checks for a win condition, and notifies listeners
   * at each step. Advances to the next player unless the game ends.
   */
  public void rollDice() {
    if (isFinished()) {
      notifyEvent("The game is already finished. The winner is " + winner.getName());
      return;
    }

    Player current = players.get(currentPlayerIndex);
    var faces = dice.rollAll();
    notifyEvent(String.format("Player %s rolled %d and %d", 
        current.getName(), faces.get(0), faces.get(1)));

    int total = faces.stream().mapToInt(i -> i).sum();
    notifyEvent("Total roll: " + total);

    Tile oldTile = current.getCurrentTile();
    Tile newTile = board.getTileAfter(oldTile, total);
    current.setCurrentTile(newTile);
    notifyEvent(String.format(
      "Player %s moved from tile %d to tile %d",
      current.getName(), oldTile.getId(), newTile.getId()));

    Optional<String> actionMsg = newTile.triggerAction(current);
    actionMsg.ifPresent(this::notifyEvent);

    notifyEvent("Player " + current.getName() + " is now on tile " + current.getCurrentTile().getId());

    if (current.getCurrentTile().isFinalTile()) {
      winner = current;
      notifyEvent("Player " + current.getName() + " won the game!");
    } else {
      currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
      startNextTurn();
    }
  }

  /**
   * Returns whether the game has been won by a player.
   * 
   * @return true if a winner has been determined, false otherwise
   */
  public boolean isFinished() {
    return winner != null;
  }

  /**
   * Gets the player who won the game, or null if the game is still in progress.
   * 
   * @return the winning Player, or null
   */
  public Player getWinner() {
    return winner;
  }

  /**
   * Plays a single round (turn) automatically: prompts, rolls dice, and processes movement.
   */
  public void playOneRound() {
    startNextTurn();
    rollDice();
  }

  /**
   * Returns the underlying board in use.
   * 
   * @return the current Board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Returns an unmodifiable list of players in turn order.
   * 
   * @return the list of Players
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * Resets the game state: moves all players back to the start tile, clears the winner
   * and turn index, notifies listeners of the reset, and begins the first turn.
   */
  public void reset() {
    Tile start = board.getStartTile();
    for (var p : players) {
      p.setCurrentTile(start);
    }
    winner = null;
    currentPlayerIndex = 0;
    notifyEvent("Game has been reset. Players are back at the start tile.");
    startNextTurn();
  }

  /**
   * Notifies all registered listeners with the given message.
   * 
   * @param message the event description to broadcast
   */
  private void notifyEvent(String message) {
    for (GameEventListener listener : listeners) {
      listener.onGameEvent(message);
    }
  }
}