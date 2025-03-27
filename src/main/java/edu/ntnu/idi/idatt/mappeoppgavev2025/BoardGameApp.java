package edu.ntnu.idi.idatt.mappeoppgavev2025;

public class BoardGameApp {
    public static void main(String[] args) {
        BoardGame game = new BoardGame();
        game.addEventListener(message -> System.out.println(message));

        game.createBoard();
        game.createDice(2);
        game.addPlayer(new Player("Alice"));
        game.addPlayer(new Player("Bob"));
        
        int roundNumber = 1;
        while (!game.isFinished()) {
            System.out.println("Round number " + roundNumber++);
            game.playOneRound();
            System.out.println();
        }
        System.out.println("The winner is " + game.getWinner().getName());
    }
}