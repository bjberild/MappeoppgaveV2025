package edu.ntnu.idi.idatt.mappeoppgavev2025;

import java.util.ArrayList;

public class Dice {
  private final ArrayList<Die> diceList = new ArrayList<>();

  public Dice(int numberOfDice) {
    for (int i = 0; i < numberOfDice; i++) {
      diceList.add(new Die());
    }
  }

  public int rollAllDice() {
    int sum = 0;
    for (Die die : diceList) {
      sum += die.roll();
    }
    return sum;
  }

  public int getDieValue(int dieIndex) {
    return diceList.get(dieIndex).getValue();
  }

}
