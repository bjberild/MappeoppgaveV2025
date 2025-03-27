package edu.ntnu.idi.idatt.mappeoppgavev2025;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DieTest {
    @Test
    public void testRollWithinRange() {
        Die die = new Die();
        for (int i = 0; i < 1000; i++) {
            int result = die.roll();
            assertTrue(result >= 1 && result <= 6, "Die roll should be between 1 and 6");
        }
    }
}