package edu.ntnu.idi.idatt.mappeoppgavev2025;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Dice;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.idatt.mappeoppgavev2025.model.Dice;


public class DiceTest {
    @Test 
    public void testRollSum() {
        int numberOfDice = 2; 
        Dice dice = new Dice(numberOfDice);
        for (int i = 0; i < 100; i++) {
            int result = dice.roll();
            assertTrue(result >= 2 && result <= 12, "Die roll should be between 2 and 12");
        }
    }

}
