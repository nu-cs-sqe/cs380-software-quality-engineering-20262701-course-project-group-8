package domain;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    
    // TC4
    @Test 
    public void buyDevelopmentCard_oneResourceShort_throwsIllegalStateException() {
        Bank bank = new Bank();
        ResourceHand hand = new ResourceHand();
        moveFromBank(bank, hand, ResourceType.ORE, 1);
        moveFromBank(bank, hand, ResourceType.GRAIN, 1);
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);
        
        assertThrows(IllegalStateException.class, () -> actions.buyDevelopmentCard(hand, 0));
        assertEquals(1, hand.getCount(ResourceType.ORE));
        assertEquals(1, hand.getCount(ResourceType.GRAIN));
        assertEquals(0, hand.getCount(ResourceType.WOOL));
        assertEquals(Bank.DEVELOPMENT_CARD_SUPPLY, bank.getDevelopmentCardCount());
    }
    
    // TC5
    @Test
    public void buyDevelopmentCard_exactCost_returnsCardAndPaysBank() {
        Bank bank = new Bank();
        ResourceHand hand = new ResourceHand();
        moveFromBank(bank, hand, ResourceType.ORE, 1);
        moveFromBank(bank, hand, ResourceType.GRAIN, 1);
        moveFromBank(bank, hand, ResourceType.WOOL, 1);
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);

        DevelopmentCard card = actions.buyDevelopmentCard(hand, 0);

        assertNotNull(card);
        assertDoesNotThrow(() -> card.canBePlayed(0));
        assertEquals(0, hand.getTotal());
        assertEquals(Bank.RESOURCE_SUPPLY, bank.getResourceCount(ResourceType.ORE));
        assertEquals(Bank.RESOURCE_SUPPLY, bank.getResourceCount(ResourceType.GRAIN));
        assertEquals(Bank.RESOURCE_SUPPLY, bank.getResourceCount(ResourceType.WOOL));
        assertEquals(Bank.DEVELOPMENT_CARD_SUPPLY - 1, bank.getDevelopmentCardCount());
    }

    // TC 6
    @Test 
    public void buyDevelopmentCard_moreThanCost_paysOnlyCost() {
        Bank bank = new Bank();
        ResourceHand hand = new ResourceHand();
        moveFromBank(bank, hand, ResourceType.ORE, 2);
        moveFromBank(bank, hand, ResourceType.GRAIN, 2);
        moveFromBank(bank, hand, ResourceType.WOOL, 2);
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);

        DevelopmentCard card = actions.buyDevelopmentCard(hand, 0);

        assertNotNull(card);
        assertEquals(1, hand.getCount(ResourceType.ORE));
        assertEquals(1, hand.getCount(ResourceType.GRAIN));
        assertEquals(1, hand.getCount(ResourceType.WOOL));
        assertEquals(Bank.RESOURCE_SUPPLY - 1, bank.getResourceCount(ResourceType.ORE));
        assertEquals(Bank.RESOURCE_SUPPLY - 1, bank.getResourceCount(ResourceType.GRAIN));
        assertEquals(Bank.RESOURCE_SUPPLY - 1, bank.getResourceCount(ResourceType.WOOL));
        assertEquals(Bank.DEVELOPMENT_CARD_SUPPLY - 1, bank.getDevelopmentCardCount());

    }
    
    private static void moveFromBank(Bank bank, ResourceHand hand, ResourceType type, int amount) {
        bank.giveResource(type, amount);
        hand.add(type, amount);
    }
}
