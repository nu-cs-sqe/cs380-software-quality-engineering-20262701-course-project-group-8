package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class DevelopmentCardTest {
    // TC1
    @Test 
    public void constructor_nullType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DevelopmentCard(null, 0));
    }

    // TC2
    @Test
    public void constructor_negativeTurnPurchased_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new DevelopmentCard(DevelopmentCardType.KNIGHT, -1));
    }

    // TC3
    @Test
    public void constructor_zeroTurnPurchased_createsCard() {
        assertDoesNotThrow(() -> new DevelopmentCard(DevelopmentCardType.KNIGHT, 0));
    }

    // TC4
    @Test
    public void constructor_positiveTurnPurchased_createsCard() {
        assertDoesNotThrow(() -> new DevelopmentCard(DevelopmentCardType.KNIGHT, 1));
    }

    // TC5
    @ParameterizedTest 
    @EnumSource(DevelopmentCardType.class)
    public void getType_returnsTypeGivenInConstructor(DevelopmentCardType type) {
        DevelopmentCard card = new DevelopmentCard(type, 0);
        assertEquals(type, card.getType());
    }
}
