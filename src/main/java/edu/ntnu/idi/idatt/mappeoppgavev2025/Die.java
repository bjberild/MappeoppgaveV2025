package edu.ntnu.idi.idatt.mappeoppgavev2025;

import java.util.Random;

/**
 * A class representing a die. The die can be rolled to get a random value between 1 and 6.
 */
public class Die {

  private int value = 1;
  private final Random random = new Random();

  public int roll() {
    value = random.nextInt(6) + 1;
    return value;
  }

  public int getValue() {
    return value;
  }
}
