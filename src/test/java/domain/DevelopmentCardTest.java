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

    // TC6
    @Test
    public void isVictoryPointCard_returnsTrue() {
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.VICTORY_POINT, 0);
        assertTrue(card.isVictoryPoint());
    }

    // TC7
    @ParameterizedTest
    @EnumSource(value = DevelopmentCardType.class, names = "VICTORY_POINT", mode = EnumSource.Mode.EXCLUDE)
    public void isVictoryPoint_nonVictoryPointCard_returnsFalse(DevelopmentCardType type) {
        DevelopmentCard card = new DevelopmentCard(type, 0);
        assertFalse(card.isVictoryPoint());
    }

    // TC8
    @Test
    public void isPlayed_newCard_returnsFalse() {
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 0);
        assertFalse(card.isPlayed());
    }
}
