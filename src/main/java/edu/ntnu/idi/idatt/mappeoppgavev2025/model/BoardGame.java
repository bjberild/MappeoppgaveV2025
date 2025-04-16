package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idi.idatt.mappeoppgavev2025.event.GameEventListener;

public class BoardGame {

  private final List<Player> players = new ArrayList<>();
  private final List<GameEventListener> listeners = new ArrayList<>();
  private Board board;
  private Dice dice;
  private Player winner;
  private int currentPlayerIndex = 0;

  public void addEventListener(GameEventListener listener) {
    listeners.add(listener);
  }

  public void createBoard() {
    board = new Board();
    board.initializeStandardBoard(90);
    board.addLadderActionTiles(7);
  }

  public void createDice(int numberOfDice) {
    dice = new Dice(numberOfDice);
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

    Player currentPlayer = players.get(currentPlayerIndex);
    int rollResult = dice.roll();
    notifyEvent("Player " + currentPlayer.getName() + " rolled " + rollResult);

    Tile currentTile = currentPlayer.getCurrentTile();
    Tile newTile = board.getTileAfter(currentTile, rollResult);
    currentPlayer.setCurrentTile(newTile);
    notifyEvent("Player " + currentPlayer.getName() + " moved to tile " + newTile.getId());

    if (newTile.isFinalTile()) {
      winner = currentPlayer;
      notifyEvent("Player " + currentPlayer.getName() + " won the game!");
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

}