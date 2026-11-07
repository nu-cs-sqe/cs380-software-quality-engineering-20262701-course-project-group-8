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

    // TC10
    @Test
    public void canBePlayed_turnBeforePurchase_throwsIllegalArgumentException() {
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        assertThrows(IllegalArgumentException.class, () -> card.canBePlayed(2));
    }

    // TC11
    @Test
    public void canBePlayed_sameTurnAsPurchase_returnsFalse() {
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        assertFalse(card.canBePlayed(3));
    }

    // TC12
    @Test
    public void canBePlayed_turnAfterPurchase_returnsTrue() {
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        assertTrue(card.canBePlayed(4));
    }

    // TC14
    @Test
    public void canBePlayed_victoryPointCard_returnFalse() {
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.VICTORY_POINT, 3);
        assertFalse(card.canBePlayed(4));
    }

    // TC9, TC17
    @Test 
    public void isPlayed_afterPlayOnNextTurn_returnsTrue() {
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        assertDoesNotThrow(() -> card.play(4));
        assertTrue(card.isPlayed());
    }
    
    // TC13
    @Test
    public void canBePlayed_afterPlayed_returnsFalse() {
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        card.play(4);
        assertFalse(card.canBePlayed(5));
    }
    
    // TC15
    @Test
    public void play_turnBeforePurchase_throwsIllegalArgumentException() {
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        assertThrows(IllegalArgumentException.class, () -> card.play(2));
    }

    // TC16
    @Test 
    public void play_sameTurnAsPurchase_throwsIllegalArgumentException() {
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        assertThrows(IllegalArgumentException.class, () -> card.play(3));
        assertFalse(card.isPlayed());
    }
}
