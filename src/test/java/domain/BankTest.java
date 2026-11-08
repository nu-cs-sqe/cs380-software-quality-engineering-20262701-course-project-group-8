package domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BankTest {
    // TC 1
    @ParameterizedTest 
    @EnumSource(ResourceType.class)
    public void getResourceCount_newBank_returnsNineteen(ResourceType type) {
        Bank bank = new Bank();
        assertEquals(19, bank.getResourceCount(type));
    }

    // TC 2
    @Test
    public void getResourceCount_nullType_throwsNullPointerException() {
        Bank bank = new Bank();
        assertThrows(NullPointerException.class, () -> bank.getResourceCount(null));
    }

    // TC 3
    @Test
    public void constructor_nullRandom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Bank(null));
    }

    // TC 4
    @Test
    public void hasResource_nullType_throwsNullPointerException() {
        Bank bank = new Bank();
        assertThrows(NullPointerException.class, ()->bank.hasResource(null, 1));
    }

    // TC 5
    @Test 
    public void hasResource_zeroAmount_throwsIllegalArgumentException() {
        Bank bank = new Bank();
        assertThrows(IllegalArgumentException.class, () -> bank.hasResource(ResourceType.BRICK, 0));
    }

    // TC 6
    @Test 
    public void hasResource_nineteenBrick_returnsTrue() {
        Bank bank = new Bank();
        assertTrue(bank.hasResource(ResourceType.BRICK, 19));
    }

    // TC 7
    @Test
    public void hasResource_twentyBrick_returnsFalse() {
        Bank bank = new Bank();
        assertFalse(bank.hasResource(ResourceType.BRICK, 20));
    }
}