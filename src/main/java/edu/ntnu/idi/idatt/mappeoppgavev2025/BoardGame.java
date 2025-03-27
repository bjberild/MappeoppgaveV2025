package edu.ntnu.idi.idatt.mappeoppgavev2025;

import java.util.ArrayList;
import java.util.List;
public class BoardGame {
    private final List<Player> players = new ArrayList<>();
    private final List<GameEventListener> listeners = new ArrayList<>();
    private Board board;
    private Dice dice;
    private Player winner;
    


    public void addEventListener(GameEventListener listener) {
        listeners.add(listener);
    }
    
    public void createBoard() {
        board = new Board();
        board.initializeStandardBoard(90);
    }


    public void createDice(int numberOfDice) {
        dice = new Dice(numberOfDice);
    }

    public void addPlayer(Player player) {
        player.setCurrentTile(board.getStartTile());
        players.add(player);
    }

    public void playOneRound() {
        for (Player player : players) {
            int rollResult = dice.roll();
            notifyEvent("Player " + player.getName() + " rolled " + rollResult);

            Tile currentTile = player.getCurrentTile();
            Tile newTile = board.getTileAfter(currentTile, rollResult);
            player.setCurrentTile(newTile);
            notifyEvent("Player " + player.getName() + " moved to tile " + newTile.getId());

            if (newTile.isFinalTile()) {
                winner = player;
                notifyEvent("Player " + player.getName() + " won the game!");
                break;
            }
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
    

}
