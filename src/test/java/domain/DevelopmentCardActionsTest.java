package domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DevelopmentCardActionsTest {
    // TC1
    @Test
    public void constructor_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, 
            () -> new DevelopmentCardActions(null, new Bank()));
        assertThrows(NullPointerException.class, 
            () -> new DevelopmentCardActions(new GameRules(), null));
    }
}
