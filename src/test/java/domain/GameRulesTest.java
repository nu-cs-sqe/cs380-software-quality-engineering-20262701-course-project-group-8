package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameRulesTest {
    // TC 1
    @Test
    public void isValidPlayerCount_twoPlayers_returnsFalse() {
        GameRules rules = new GameRules();
        assertFalse(rules.isValidPlayerCount(2));
    }

    // TC 2
    @Test
    public void isValidPlayerCount_threePlayers_returnsTrue() {
        GameRules rules = new GameRules();
        assertTrue(rules.isValidPlayerCount(3));
    }

    // TC 3
    @Test
    public void isValidPlayerCount_fourPlayers_returnsTrue() {
        GameRules rules = new GameRules();
        assertTrue(rules.isValidPlayerCount(4));
    }

    // TC 4
    @Test
    public void isValidPlayerCount_fivePlayers_returnsFalse() {
        GameRules rules = new GameRules();
        assertFalse(rules.isValidPlayerCount(5));
    }

    // TC 5
    @Test
    public void getDiscardCount_negativeHandSize_throwsIllegalArgumentException() {
        GameRules rules = new GameRules();
        assertThrows(IllegalArgumentException.class, () -> rules.getDiscardCount(-1));
    }

    // TC 6
    @Test
    public void getDiscardCount_emptyHand_returnsZero() {
        GameRules rules = new GameRules();
        assertEquals(0, rules.getDiscardCount(0));
    }

    // TC 7
    @Test
    public void getDiscardCount_sevenCards_returnsZero() {
        GameRules rules = new GameRules();
        assertEquals(0, rules.getDiscardCount(7));
    }

    // TC 8
    @Test
    public void getDiscardCount_eightCards_returnsFour() {
        GameRules rules = new GameRules();
        assertEquals(4, rules.getDiscardCount(8));
    }

    // TC 9
    @Test
    public void getDiscardCount_nineCards_returnsFourRoundedDown() {
        GameRules rules = new GameRules();
        assertEquals(4, rules.getDiscardCount(9));
    }
}
