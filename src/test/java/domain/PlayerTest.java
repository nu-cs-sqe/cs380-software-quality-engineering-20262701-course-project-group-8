package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.easymock.EasyMock;

public class PlayerTest {

    @Test
    public void constructor_nameISNullColorIsValid_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Player(null, PlayerColor.RED));
        assertEquals("Error: Name cannot be a null value", exception.getMessage());  
    }
}
