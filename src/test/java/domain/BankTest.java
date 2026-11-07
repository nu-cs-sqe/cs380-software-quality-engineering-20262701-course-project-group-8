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
}