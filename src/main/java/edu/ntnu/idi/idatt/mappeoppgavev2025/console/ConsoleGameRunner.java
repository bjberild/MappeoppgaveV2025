package edu.ntnu.idi.idatt.mappeoppgavev2025.console;

import edu.ntnu.idi.idatt.mappeoppgavev2025.event.GameEventListener;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;

//This class makes it possible to check certiant aspects of the game without having to run/start the whole game
public class ConsoleGameRunner {
    public static void main(String[] args) {
        BoardGame game = new BoardGame();

        game.createBoard();
        game.createDice(2);

        game.addEventListener(new GameEventListener() {
            @Override 
            public void onGameEvent(String message) {
                System.out.println(message);
            }
        });

        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 2"));

        while (!game.isFinished()) {
            game.playOneRound();
        }
    }
}
