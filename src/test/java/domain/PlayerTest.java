package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;

public class PlayerTest {

    // TC1
    @Test
    public void constructor_nameISNullColorIsValid_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Player(null, PlayerColor.RED));
        assertEquals("Error: Name cannot be a null value", exception.getMessage());  
    }

    // TC2
    @Test
    public void constructor_nameIsEmptyColorIsValid_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Player("", PlayerColor.RED));
        assertEquals("Error: Name cannot be an empty value", exception.getMessage());  
    }

    // TC3
    @Test
    public void constructor_nameIsBlankColorIsValid_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Player(" ", PlayerColor.RED));
        assertEquals("Error: Name cannot be an empty value", exception.getMessage());  
    }

    // TC4
    @Test
    public void constructor_nameIsValidColorIsNull_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Player("Alice", null));
        assertEquals("Error: Color cannot be a null value", exception.getMessage());  
    }
}
