package domain;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    // TC2
    @Test
    public void buyDevelopmentCard_nullHand_throwsNullPointerException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        assertThrows(NullPointerException.class, () -> actions.buyDevelopmentCard(null, 0));
    }

    // TC3
    @Test
    public void buyDevelopmentCard_negativeTurn_throwsIllegalArgumentException() {
        Bank bank = new Bank();
        ResourceHand hand = new ResourceHand();
        moveFromBank(bank, hand, ResourceType.ORE, 1);
        moveFromBank(bank, hand, ResourceType.GRAIN, 1);
        moveFromBank(bank, hand, ResourceType.WOOL, 1);
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);

        assertThrows(IllegalArgumentException.class, () -> actions.buyDevelopmentCard(hand, -1));
        assertEquals(1, hand.getCount(ResourceType.ORE));
        assertEquals(1, hand.getCount(ResourceType.GRAIN));
        assertEquals(1, hand.getCount(ResourceType.WOOL));
        assertEquals(Bank.DEVELOPMENT_CARD_SUPPLY, bank.getDevelopmentCardCount());
    }

    private static void moveFromBank(Bank bank, ResourceHand hand, ResourceType type, int amount) {
        bank.giveResource(type, amount);
        hand.add(type, amount);
    }
}
