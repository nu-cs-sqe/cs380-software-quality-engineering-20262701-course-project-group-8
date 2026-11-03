package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

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

    // TC 3 - TC 7
    @ParameterizedTest
    @MethodSource("rollBoundaryCases")
    public void testRollSum(int firstDice, int secondDice, int expectedSum) {
        Random randomMock = mock(Random.class);
        expect(randomMock.nextInt(6)).andReturn(firstDice);
        expect(randomMock.nextInt(6)).andReturn(secondDice);

        Dice dice = new Dice(randomMock);

        assertEquals(expectedSum, dice.roll());
        verify(randomMock);
    }

    static Stream<Arguments> rollBoundaryCases() {
        return Stream.of(
            Arguments.of(0, 0, 2),
            Arguments.of(5, 5, 12),
            Arguments.of(0, 5, 7),
            Arguments.of(0, 1, 3),
            Arguments.of(4, 5, 11)
        );
    }

    // TC 8
    @Test
    public void testRollCallsNextIntTwiceWithBound6() {
        Random randomMock = mock(Random.class);
        expect(randomMock.nextInt(6)).andReturn(0).times(2);
        replay(randomMock);

        Dice dice = new Dice(randomMock);
        dice.roll();

        verify(randomMock);
    }

    // TC 9
    @Test
    public void testRollTwiceUsesNewValues() {
        Random randomMock = mock(Random.class);
        expect(randomMock.nextInt(6)).andReturn(9).times(2);
        expect(randomMock.nextInt(6)).andReturn(5).times(2);
        replay(randomMock);

        Dice dice = new Dice(randomMock);

        assertEquals(2, dice.roll());
        assertEquals(12, dice.roll());
        verify(randomMock);
    }

}
