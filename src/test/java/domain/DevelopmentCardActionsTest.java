package domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
            () -> actions.playYearOfPlenty(card, 3, hand, ResourceType.BRICK, ResourceType.ORE));
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

    // TC17
    @Test
    public void playMonopoly_nullArgument_throwsNullPointerException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.MONOPOLY, 3);
        ResourceHand hand = new ResourceHand();
        List<ResourceHand> otherHands = List.of(new ResourceHand());

        assertThrows(NullPointerException.class,
            () -> actions.playMonopoly(null, 4, hand, otherHands, ResourceType.WOOL));
        assertThrows(NullPointerException.class,
            () -> actions.playMonopoly(card, 4, null, otherHands, ResourceType.WOOL));
        assertThrows(NullPointerException.class,
            () -> actions.playMonopoly(card, 4, hand, null, ResourceType.WOOL));
        assertThrows(NullPointerException.class,
            () -> actions.playMonopoly(card, 4, hand, otherHands, null));
    }

    // TC18
    @Test
    public void playMonopoly_wrongCardType_throwsIllegalArgumentException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.YEAR_OF_PLENTY, 3);
        ResourceHand hand = new ResourceHand();
        ResourceHand other = new ResourceHand();
        other.add(ResourceType.WOOL, 2);

        assertThrows(IllegalArgumentException.class,
            () -> actions.playMonopoly(card, 4, hand, List.of(other), ResourceType.WOOL));
        assertEquals(0, hand.getTotal());
        assertEquals(2, other.getCount(ResourceType.WOOL));
        assertFalse(card.isPlayed());
    }

    // TC19
    @Test 
    public void playMonopoly_sameTurnAsPurchase_throwsIllegalStateException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.MONOPOLY, 3);
        ResourceHand hand = new ResourceHand();
        ResourceHand other = new ResourceHand();
        other.add(ResourceType.WOOL, 2);

        assertThrows(IllegalStateException.class,
            () -> actions.playMonopoly(card, 3, hand, List.of(other), ResourceType.WOOL));
        assertEquals(0, hand.getTotal());
        assertEquals(2, other.getCount(ResourceType.WOOL));
        assertFalse(card.isPlayed());
    }

    // TC20
    @Test public void playMonopoly_otherHandsContainsOwnHand_throwsIllegalArgumentException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.MONOPOLY, 3);
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.WOOL, 1);
        ResourceHand other = new ResourceHand();
        other.add(ResourceType.WOOL, 2);

        assertThrows(IllegalArgumentException.class,
            () -> actions.playMonopoly(card, 4, hand, List.of(other, hand), ResourceType.WOOL));
        assertEquals(1, hand.getCount(ResourceType.WOOL));
        assertEquals(2, other.getCount(ResourceType.WOOL));
        assertFalse(card.isPlayed());
    }

    // TC21
    @Test
    public void playMonopoly_noOtherHands_returnsZeroAndPlaysCard() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.MONOPOLY, 3);
        ResourceHand hand = new ResourceHand();

        int taken = actions.playMonopoly(card, 4, hand, List.of(), ResourceType.WOOL);

        assertEquals(0, taken);
        assertEquals(0, hand.getTotal());
        assertTrue(card.isPlayed());
    }

    // TC22
    @Test 
    public void playMonopoly_otherHandHasNone_returnsZeroAndPlayCard() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.MONOPOLY, 3);
        ResourceHand hand = new ResourceHand();
        ResourceHand other = new ResourceHand();
        other.add(ResourceType.BRICK, 1);

        int taken = actions.playMonopoly(card, 4, hand, List.of(other), ResourceType.WOOL);

        assertEquals(0, taken);
        assertEquals(0, hand.getTotal());
        assertEquals(0, other.getCount(ResourceType.WOOL));
        assertEquals(1, other.getCount(ResourceType.BRICK));
        assertTrue(card.isPlayed());
    }
    
    // TC23
    @Test
    public void playMonopoly_otherHandHasOne_takesItAndReturnsOne() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.MONOPOLY, 3);
        ResourceHand hand = new ResourceHand();
        ResourceHand other = new ResourceHand();
        other.add(ResourceType.WOOL, 1);

        int taken = actions.playMonopoly(card, 4, hand, List.of(other), ResourceType.WOOL);

        assertEquals(1, taken);
        assertEquals(0, other.getCount(ResourceType.WOOL));
        assertEquals(1, hand.getCount(ResourceType.WOOL));
        assertTrue(card.isPlayed());
    }
    
    // TC24
    @Test
    public void playMonopoly_manyOtherHands_takesAllOfTypeAndReturnsTotal() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.MONOPOLY, 3);
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.WOOL, 1);

        ResourceHand otherA = new ResourceHand();
        otherA.add(ResourceType.BRICK, 1);
        ResourceHand otherB = new ResourceHand();
        otherB.add(ResourceType.WOOL, 2);
        otherB.add(ResourceType.BRICK, 1);
        ResourceHand otherC = new ResourceHand();
        otherC.add(ResourceType.WOOL, 5);
        otherC.add(ResourceType.BRICK, 1);

        int taken = actions.playMonopoly(card, 4, hand, List.of(otherA, otherB, otherC), ResourceType.WOOL);

        assertEquals(7, taken);
        assertEquals(8, hand.getCount(ResourceType.WOOL));
        for (ResourceHand other : List.of(otherA, otherB, otherC)) {
            assertEquals(0, other.getCount(ResourceType.WOOL));
            assertEquals(1, other.getCount(ResourceType.BRICK));
        }
        assertTrue(card.isPlayed());
    }

    // TC25
    @Test
    public void playKnight_wrongCardType_throwsIllegalArgumentException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.MONOPOLY, 3);
        Board board = new Board();
        Hex desert = board.getRobberHex();
        Hex target = nonDesertHex(board);

        assertThrows(IllegalArgumentException.class,
            () -> actions.playKnight(card, 4, board, target));
        assertTrue(desert.hasRobber());
        assertFalse(target.hasRobber());
        assertFalse(card.isPlayed());
    }

    // TC26
    @Test
    public void playKnight_sameTurnAsPurchase_throwsIllegalStateException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        Board board = new Board();
        Hex desert = board.getRobberHex();
        Hex target = nonDesertHex(board);

        assertThrows(IllegalStateException.class,
            () -> actions.playKnight(card, 3, board, target));
        assertTrue(desert.hasRobber());
        assertFalse(target.hasRobber());
        assertFalse(card.isPlayed());
    }

    // TC27
    @Test 
    public void playKnight_alreadyPlayed_throwsIllegalStateException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        Board board = new Board();
        Hex desert = board.getRobberHex();
        Hex target = nonDesertHex(board);
        actions.playKnight(card, 4, board, target);

        assertThrows(IllegalStateException.class,
            () -> actions.playKnight(card, 5, board, desert));
        assertTrue(target.hasRobber());
        assertFalse(desert.hasRobber());
        assertTrue(card.isPlayed());
    }

    // TC28
    @Test
    public void playKnight_targetHasRobber_throwsIllegalArgumentException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        Board board = new Board();
        Hex desert = board.getRobberHex();

        assertThrows(IllegalArgumentException.class,
            () -> actions.playKnight(card, 4, board, desert));
        assertTrue(desert.hasRobber());
        assertFalse(card.isPlayed());
    }

    // TC29
    @Test 
    public void playKnight_targetNotOnBoard_throwsIllegalArgumentException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        Board board = new Board();
        Hex desert = board.getRobberHex();
        Hex otherBoardHex = nonDesertHex(new Board());

        assertThrows(IllegalArgumentException.class,
            () -> actions.playKnight(card, 4, board, otherBoardHex));
        assertTrue(desert.hasRobber());
        assertFalse(otherBoardHex.hasRobber());
        assertFalse(card.isPlayed());
    }

    // TC30
    @Test 
    public void playKnight_validTarget_movesRobberAndPlaysCard() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        DevelopmentCard card = new DevelopmentCard(DevelopmentCardType.KNIGHT, 3);
        Board board = new Board();
        Hex desert = board.getRobberHex();
        Hex target = nonDesertHex(board);

        actions.playKnight(card, 4, board, target);

        assertEquals(target, board.getRobberHex());
        assertTrue(target.hasRobber());
        assertFalse(desert.hasRobber());
        assertTrue(card.isPlayed());
    }

    // TC31
    @Test
    public void countVictoryPoints_nullCards_throwsNullPointerException() {
        DevelopmentCardActions actions = new DevelopmentCardActions(new GameRules(), new Bank());
        assertThrows(NullPointerException.class, () -> actions.countVictoryPoints(null));
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

    private static Hex nonDesertHex(Board board) {
        for (Hex hex : board.getHexes()) {
            if (!hex.hasRobber()) {
                return hex;
            }
        }
        throw new IllegalStateException("Board has no hex without the robber");
    }
}
