package edu.ntnu.idi.idatt.mappeoppgavev2025.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a collection of dice. The Dice class allows rolling multiple dice at once
 * and getting the sum of their values.
 *
 * @author bjberild, StianDolerud
 */
public class Dice {

  private final List<Die> dice;

  /**
   * Constructor for the Dice class.
   *
   * @param numberOfDice the number of dice to create
   * @author bjberild
   */
  public Dice(int numberOfDice) {
    dice = new ArrayList<>();
    for (int i = 0; i < numberOfDice; i++) {
      dice.add(new Die());
    }
  }

  /**
   * Rolls all the dice in the collection and returns a list of their values.
   *
   * @return a list of integers representing the values of the rolled dice
   * @author bjberild, StianDolerud
   */
  public List<Integer> rollAll() {
    List<Integer> results = new ArrayList<>();
    for (Die d : dice) {
      results.add(d.roll());
    }
    return results;
  }

  /**
   * Rolls all the dice in the collection and returns the sum of their values.
   *
   * @return an integer representing the sum of the rolled dice
   * @author StianDolerud
   */
  public int roll() {
    return rollAll().stream().mapToInt(i -> i).sum();
  }
}