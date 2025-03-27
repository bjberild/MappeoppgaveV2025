package edu.ntnu.idi.idatt.mappeoppgavev2025;

import java.util.List;

public class Dice {
  private List<Die> dice;

  public Dice(int numberOfDice) {
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(new Die());
    }

  }

}
