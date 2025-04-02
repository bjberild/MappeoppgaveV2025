package edu.ntnu.idi.idatt.mappeoppgavev2025.view;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;

import java.util.Scanner;

public class BoardGameApp {

  public static void main(String[] args) {
    BoardGame game = new BoardGame();
    game.addEventListener(message -> System.out.println(message));

    game.createBoard();
    game.createDice(2);
    game.addPlayer(new Player("Alice"));
    game.addPlayer(new Player("Bob"));

    Scanner scanner = new Scanner(System.in);
    while (!game.isFinished()) {
      game.startNextTurn();
      System.out.println("Press Enter to roll the dice...");
      scanner.nextLine();
      game.rollDice();
      System.out.println();
    }
    System.out.println("The winner is " + game.getWinner().getName());
  }
}