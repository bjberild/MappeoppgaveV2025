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



public class BoardGame {

  private final List<Player> players = new ArrayList<>();
  private final List<GameEventListener> listeners = new ArrayList<>();
  private Board board;
  private Dice dice;
  private Player winner;
  private int currentPlayerIndex = 0;

  public void loadBoard(Path jsonFile) throws IOException {
    String text = Files.readString(jsonFile);
    JsonObject json = JsonParser.parseString(text).getAsJsonObject();
    Board loaded = new GsonBoardPersistence().deserialize(json);
    this.board = loaded;
  }

  public void loadPlayers(Path csvFile) throws PlayerPersistenceException {
    var loaded = new CsvPlayerPersistence().load(csvFile);
    players.clear();
    for (Player p : loaded) {
      addPlayer(p);
    }
  }
  


  public void addEventListener(GameEventListener listener) {
    listeners.add(listener);
  }

  public void createBoard() {
    board = new Board();
    board.initializeStandardBoard(90);
    board.addFallTrapActionTiles(3);
    board.addPortalActionTiles(3);
  }
  

  public void createDice(int numberOfDice) {
    dice = new Dice(numberOfDice);
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public void addPlayer(Player player) {
    player.setCurrentTile(board.getStartTile());
    players.add(player);
  }

  public void startNextTurn() {
    if (isFinished()) {
      notifyEvent("The game is already finished. The winner is " + winner.getName());
      return;
    }

    Player currentPlayer = players.get(currentPlayerIndex);
    notifyEvent("Player " + currentPlayer.getName() + "'s turn. Please roll the dice.");
  }

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

      notifyEvent("Players " + current.getName() + " is now on tile " + current.getCurrentTile().getId());

      if (current.getCurrentTile().isFinalTile()) {
        winner = current;
        notifyEvent("Player " + current.getName() + " won the game!");
      } else {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        startNextTurn();
      }
  }

  public boolean isFinished() {
    return winner != null;
  }

  public Player getWinner() {
    return winner;
  }

  private void notifyEvent(String message) {
    for (GameEventListener listener : listeners) {
      listener.onGameEvent(message);
    }
  }

  public void playOneRound() {
    startNextTurn();
    rollDice();
  }

  public Board getBoard() {
    return board;
  }

  public List<Player> getPlayers() {
    return players;
  }

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

}