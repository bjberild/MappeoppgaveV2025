package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.ArrayList;
import java.util.List;

public class Dice {

  private final List<Die> dice;

  public Dice(int numberOfDice) {
    dice = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(new Die());
    }
  }

  public List<Integer> rollAll() {
    List<Integer> results = new ArrayList<>();
    for (Die d : dice) {
      results.add(d.roll());
    }
    return results;
  }

  public int roll() {
    return rollAll().stream().mapToInt(i -> i).sum();
  }
}