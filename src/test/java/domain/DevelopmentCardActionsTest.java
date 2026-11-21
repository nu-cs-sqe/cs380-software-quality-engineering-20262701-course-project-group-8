package domain;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    
    // TC7
    @Test
    public void buyDevelopmentCard_lastCardInDeck_returnsCardAndEmptiesDeck() {
        Bank bank = new Bank();
        drawFromBank(bank, Bank.DEVELOPMENT_CARD_SUPPLY - 1);
        ResourceHand hand = new ResourceHand();
        moveFromBank(bank, hand, ResourceType.ORE, 1);
        moveFromBank(bank, hand, ResourceType.GRAIN, 1);
        moveFromBank(bank, hand, ResourceType.WOOL, 1);
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);

        DevelopmentCard card = actions.buyDevelopmentCard(hand, 0);

        assertNotNull(card);
        assertEquals(0, hand.getTotal());
        assertEquals(0, bank.getDevelopmentCardCount());
    }

    // TC8
    @Test
    public void buyDevelopmentCard_emptyDeck_throwsIllegalStateException() {
        Bank bank = new Bank();
        drawFromBank(bank, bank.DEVELOPMENT_CARD_SUPPLY);
        ResourceHand hand = new ResourceHand();
        moveFromBank(bank, hand, ResourceType.ORE, 1);
        moveFromBank(bank, hand, ResourceType.GRAIN, 1);
        moveFromBank(bank, hand, ResourceType.WOOL, 1);
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);



    }

    // TC9
    @Test
    public void playYearOfPlenty_nullArgument_throwsNullPointerException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.YEAR_OF_PLENTY, 3);
        ResourceHand hand = new ResourceHand();

        assertThrows(NullPointerException.class,
            () -> actions.playYearOfPlenty(null, 4, hand, ResourceType.BRICK, ResourceType.ORE));
        assertThrows(NullPointerException.class,
            () -> actions.playYearOfPlenty(card, 4, null, ResourceType.BRICK, ResourceType.ORE));
        assertThrows(NullPointerException.class,
            () -> actions.playYearOfPlenty(card, 4, hand, null, ResourceType.ORE));
        assertThrows(NullPointerException.class,
            () -> actions.playYearOfPlenty(card, 4, hand, ResourceType.BRICK, null));
    }

    // TC10
    @Test
    public void playYearOfPlenty_wrongCardType_throwsIllegalArgumentException() {
        Bank bank = new Bank();
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.MONOPOLY, 3);
        ResourceHand hand = new ResourceHand();

        assertThrows(IllegalArgumentException.class,
            () -> actions.playYearOfPlenty(card, 4, hand, ResourceType.BRICK, ResourceType.ORE));
        assertEquals(0, hand.getTotal());
        assertEquals(Bank.RESOURCE_SUPPLY, bank.getResourceCount(ResourceType.BRICK));
        assertEquals(Bank.RESOURCE_SUPPLY, bank.getResourceCount(ResourceType.ORE));
        assertFalse(card.isPlayed());
    }

    // TC11
    @Test
    public void playYearOfPlenty_sameTurnAsPurchase_throwsIllegalStateException() {
        Bank bank = new Bank();
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.YEAR_OF_PLENTY, 3);
        ResourceHand hand = new ResourceHand();

        assertThrows(IllegalStateException.class,
            () -> actions.playYearOfPlenty(card, 3, hand, ResourceType.BRICK, ResourceType.ORE)
        );
        assertEquals(0, hand.getTotal());
        assertEquals(Bank.RESOURCE_SUPPLY, bank.getResourceCount(ResourceType.BRICK));
        assertEquals(Bank.RESOURCE_SUPPLY, bank.getResourceCount(ResourceType.ORE));
        assertFalse(card.isPlayed());
    }

    // TC 12
    @Test
    public void playYearOfPlenty_twoDifferentTypes_givesBothAndPlaysCard() {
        Bank bank = new Bank();
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.YEAR_OF_PLENTY, 3);
        ResourceHand hand = new ResourceHand();

        actions.playYearOfPlenty(card, 4, hand, ResourceType.BRICK, ResourceType.ORE);

        assertEquals(1, hand.getCount(ResourceType.BRICK));
        assertEquals(1, hand.getCount(ResourceType.ORE));
        assertEquals(2, hand.getTotal());
        assertEquals(Bank.RESOURCE_SUPPLY - 1, bank.getResourceCount(ResourceType.BRICK));
        assertEquals(Bank.RESOURCE_SUPPLY - 1, bank.getResourceCount(ResourceType.ORE));
        assertTrue(card.isPlayed());
    }

    // TC13
    @Test
    public void playYearOfPlenty_sameTypeTwice_givesTwoAndPlaysCard() {
        Bank bank = new Bank();
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.YEAR_OF_PLENTY, 3);
        ResourceHand hand = new ResourceHand();

        actions.playYearOfPlenty(card, 4, hand, ResourceType.BRICK, ResourceType.BRICK);

        assertEquals(2, hand.getCount(ResourceType.BRICK));
        assertEquals(2, hand.getTotal());
        assertEquals(Bank.RESOURCE_SUPPLY - 2, bank.getResourceCount(ResourceType.BRICK));
        assertTrue(card.isPlayed());
    }

    // TC14
    @Test
    public void playYearOfPlenty_bankHasExactlyTwo_givesBothAndEmptiesBank() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, Bank.RESOURCE_SUPPLY - 2);
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.YEAR_OF_PLENTY, 3);
        ResourceHand hand = new ResourceHand();

        actions.playYearOfPlenty(card, 4, hand, ResourceType.BRICK, ResourceType.BRICK);

        assertEquals(2, hand.getCount(ResourceType.BRICK));
        assertEquals(2, hand.getTotal());
        assertEquals(0, bank.getResourceCount(ResourceType.BRICK));
        assertTrue(card.isPlayed());
    }

    // TC15
    @Test
    public void playYearOfPlenty_bankOneShort_throwsIllegalStateException() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, Bank.RESOURCE_SUPPLY - 1);
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.YEAR_OF_PLENTY, 3);
        ResourceHand hand = new ResourceHand();

        assertThrows(IllegalStateException.class,
            () -> actions.playYearOfPlenty(card, 4, hand, ResourceType.BRICK, ResourceType.BRICK));
        assertEquals(0, hand.getTotal());
        assertEquals(1, bank.getResourceCount(ResourceType.BRICK));
        assertFalse(card.isPlayed());
    }

    // TC16
    @Test
    public void playYearOfPlenty_bankMissingSecondType_throwsAndGivesNothing() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.ORE, Bank.RESOURCE_SUPPLY);
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), bank);
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.YEAR_OF_PLENTY, 3);
        ResourceHand hand = new ResourceHand();

        assertThrows(IllegalStateException.class,
            () -> actions.playYearOfPlenty(card, 4, hand, ResourceType.BRICK, ResourceType.ORE));
        assertEquals(0, hand.getTotal());
        assertEquals(Bank.RESOURCE_SUPPLY, bank.getResourceCount(ResourceType.BRICK));
        assertEquals(0, bank.getResourceCount(ResourceType.ORE));
        assertFalse(card.isPlayed());
    }
    
    private static void moveFromBank(Bank bank, ResourceHand hand, ResourceType type, int amount) {
        bank.giveResource(type, amount);
        hand.add(type, amount);
    }

    private static void drawFromBank(Bank bank, int count) {
        for (int i = 0; i < count; i++) {
            bank.drawDevelopmentCard(0);
        }
    }
}
