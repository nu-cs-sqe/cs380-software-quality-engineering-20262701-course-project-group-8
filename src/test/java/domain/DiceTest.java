package domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DiceTest {
    
    //TC 1
    @Test
    public void constructor_nullRandom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dice(null));
    }

    //TC 2
    @Test
    public void constructor_validRandom_createsDice() {
        Dice dice = new Dice(new Random());
        assertNotNull(dice);
    }
}
