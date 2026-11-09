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
}

