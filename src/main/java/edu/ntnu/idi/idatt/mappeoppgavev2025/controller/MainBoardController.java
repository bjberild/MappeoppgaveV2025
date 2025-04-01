package edu.ntnu.idi.idatt.mappeoppgavev2025.controller;

import java.util.Scanner;

import edu.ntnu.idi.idatt.mappeoppgavev2025.GameFactory;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.BoardGame;
import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Player;


public class MainBoardController {

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a game:");
        System.out.println("1. LadderGame");
        System.out.println("Your choise: ");
        int choise = scanner.nextInt();
        scanner.nextLine();

        String gameKey = (choise == 1) ? "LadderGame" : "LadderGame";

        BoardGame game = GameFactory.createGame(gameKey);

        game.addEventListener(message -> System.out.println(message));
        game.createBoard();
        game.createDice(2);

        System.out.println("Enter name for player 1: ");
        String name1 = scanner.nextLine();
        game.addPlayer(new Player(name1));

        System.out.println("Enter name for player 2: ");
        String name2 = scanner.nextLine();
        game.addPlayer(new Player(name2));

        System.out.println("Starting game...");
        int roundNumber = 1;
        while (!game.isFinished()) {
            System.out.println("Round number " + roundNumber++);
            game.playOneRound();
            System.out.println();
        }
        System.out.println("The winner is " + game.getWinner().getName());
    }

    public static void main(String[] args) {
        new MainBoardController().startGame();
    }

}
    
