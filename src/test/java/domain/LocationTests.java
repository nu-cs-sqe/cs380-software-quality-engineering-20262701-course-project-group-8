package domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTests {
    // TC 1
    @Test
    public void constructor_negativeXCoordinate_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Location(-1, 1));
        assertEquals("Error: x-coordinate position cannot be negative", exception.getMessage());
    }

    // TC 2
    @Test
    public void constructor_negativeYCoordinate_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Location(1, -1));
        assertEquals("Error: y-coordinate position cannot be negative", exception.getMessage());
    }

    // TC 3
    @Test
    public void constructor_zeroXPosZeroYPos_constructsLocationObject() {
        Location location = assertDoesNotThrow(() -> new Location(0, 0));
        assertNotNull(location);
    }

    // TC 4
    @Test
    public void getXPos_xPosIsOne_returnsOne() {
        Location location = new Location(1, 0);
        int expected = 1;
        int actual = location.getXPos();
        assertEquals(expected, actual);
    }
}

