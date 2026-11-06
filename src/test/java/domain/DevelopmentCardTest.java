package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DevelopmentCardTest {
    // TC1
    @Test 
    public void constructor_nullType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DevelopmentCard(null, 0));
    }
}
