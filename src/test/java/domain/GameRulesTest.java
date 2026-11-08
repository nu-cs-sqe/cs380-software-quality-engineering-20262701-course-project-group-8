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

    // TC 10
    @Test
    public void canAfford_nullHand_throwsNullPointerException() {
        GameRules rules = new GameRules();
        assertThrows(NullPointerException.class, () -> rules.canAfford(null, BuildingType.ROAD));
    }

    // TC 11
    @Test
    public void canAfford_nullType_throwsNullPointerException() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        assertThrows(NullPointerException.class, () -> rules.canAfford(hand, null));
    }

    // TC 12
    @Test
    public void canAfford_roadExactCost_returnsTrue() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.BRICK, 1);
        hand.add(ResourceType.LUMBER, 1);
        assertTrue(rules.canAfford(hand, BuildingType.ROAD));
    }

    // TC 13
    @Test
    public void canAfford_roadMissingLumber_returnsFalse() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.BRICK, 1);
        assertFalse(rules.canAfford(hand, BuildingType.ROAD));
    }

    // TC 14
    @Test
    public void canAfford_settlementExactCost_returnsTrue() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.BRICK, 1);
        hand.add(ResourceType.LUMBER, 1);
        hand.add(ResourceType.WOOL, 1);
        hand.add(ResourceType.GRAIN, 1);
        assertTrue(rules.canAfford(hand, BuildingType.SETTLEMENT));
    }

    // TC 15
    @Test
    public void canAfford_settlementMissingGrain_returnsFalse() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.BRICK, 1);
        hand.add(ResourceType.LUMBER, 1);
        hand.add(ResourceType.WOOL, 1);
        assertFalse(rules.canAfford(hand, BuildingType.SETTLEMENT));
    }

    // TC 16
    @Test
    public void canAfford_cityExactCost_returnsTrue() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.ORE, 3);
        hand.add(ResourceType.GRAIN, 2);
        assertTrue(rules.canAfford(hand, BuildingType.CITY));
    }

    // TC 17
    @Test
    public void canAfford_cityOneOreShort_returnsFalse() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.ORE, 2);
        hand.add(ResourceType.GRAIN, 2);
        assertFalse(rules.canAfford(hand, BuildingType.CITY));
    }

    // TC 18
    @Test
    public void canAfford_cityOneGrainShort_returnsFalse() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.ORE, 3);
        hand.add(ResourceType.GRAIN, 1);
        assertFalse(rules.canAfford(hand, BuildingType.CITY));
    }

    // TC 19
    @Test
    public void canAfford_developmentCardExactCost_returnsTrue() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.ORE, 1);
        hand.add(ResourceType.WOOL, 1);
        hand.add(ResourceType.GRAIN, 1);
        assertTrue(rules.canAfford(hand, BuildingType.DEVELOPMENT_CARD));
    }

    // TC 20
    @Test
    public void canAfford_developmentCardMissingWool_returnsFalse() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.ORE, 1);
        hand.add(ResourceType.GRAIN, 1);
        assertFalse(rules.canAfford(hand, BuildingType.DEVELOPMENT_CARD));
    }

    // TC 21
    @Test
    public void payCost_nullArgument_throwsNullPointerException() {
        GameRules rules = new GameRules();
        ResourceHand hand = new ResourceHand();
        Bank bank = new Bank();
        assertThrows(NullPointerException.class, () -> rules.payCost(null, bank, BuildingType.ROAD));
        assertThrows(NullPointerException.class, () -> rules.payCost(hand, null, BuildingType.ROAD));
        assertThrows(NullPointerException.class, () -> rules.payCost(hand, bank, null));
    }

    // TC 22
    @Test
    public void payCost_cityExactCost_movesCostFromHandToBank() {
        GameRules rules = new GameRules();
        Bank bank = new Bank();
        bank.giveResource(ResourceType.ORE, 3);
        bank.giveResource(ResourceType.GRAIN, 2);
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.ORE, 3);
        hand.add(ResourceType.GRAIN, 2);

        rules.payCost(hand, bank, BuildingType.CITY);

        assertEquals(0, hand.getCount(ResourceType.ORE));
        assertEquals(0, hand.getCount(ResourceType.GRAIN));
        assertEquals(19, bank.getResourceCount(ResourceType.ORE));
        assertEquals(19, bank.getResourceCount(ResourceType.GRAIN));
    }

    // TC 23
    @Test
    public void payCost_roadWithExtraCards_removesOnlyCost() {
        GameRules rules = new GameRules();
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, 2);
        bank.giveResource(ResourceType.LUMBER, 2);
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.BRICK, 2);
        hand.add(ResourceType.LUMBER, 2);

        rules.payCost(hand, bank, BuildingType.ROAD);

        assertEquals(1, hand.getCount(ResourceType.BRICK));
        assertEquals(1, hand.getCount(ResourceType.LUMBER));
        assertEquals(18, bank.getResourceCount(ResourceType.BRICK));
        assertEquals(18, bank.getResourceCount(ResourceType.LUMBER));
    }

    // TC 24
    @Test
    public void payCost_cannotAfford_throwsIllegalStateExceptionAndChangesNothing() {
        GameRules rules = new GameRules();
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, 1);
        bank.giveResource(ResourceType.LUMBER, 1);
        bank.giveResource(ResourceType.WOOL, 1);
        ResourceHand hand = new ResourceHand();
        hand.add(ResourceType.BRICK, 1);
        hand.add(ResourceType.LUMBER, 1);
        hand.add(ResourceType.WOOL, 1);

        assertThrows(IllegalStateException.class,
                () -> rules.payCost(hand, bank, BuildingType.SETTLEMENT));

        assertEquals(1, hand.getCount(ResourceType.BRICK));
        assertEquals(1, hand.getCount(ResourceType.LUMBER));
        assertEquals(1, hand.getCount(ResourceType.WOOL));
        assertEquals(18, bank.getResourceCount(ResourceType.BRICK));
        assertEquals(18, bank.getResourceCount(ResourceType.LUMBER));
        assertEquals(18, bank.getResourceCount(ResourceType.WOOL));
    }

    // TC 25
    @Test
    public void canMoveRobberTo_nullTarget_throwsNullPointerException() {
        GameRules rules = new GameRules();
        assertThrows(NullPointerException.class, () -> rules.canMoveRobberTo(null));
    }

    // TC 26
    @Test
    public void canMoveRobberTo_targetHoldsRobber_returnsFalse() {
        GameRules rules = new GameRules();
        Hex forest = new Hex(TerrainType.FORESTS, 5);
        forest.setRobber(true);
        assertFalse(rules.canMoveRobberTo(forest));
    }

    // TC 27
    @Test
    public void canMoveRobberTo_targetWithoutRobber_returnsTrue() {
        GameRules rules = new GameRules();
        Hex hills = new Hex(TerrainType.HILLS, 6);
        assertTrue(rules.canMoveRobberTo(hills));
    }

    // TC 28
    @Test
    public void canMoveRobberTo_desertAfterRobberLeft_returnsTrue() {
        GameRules rules = new GameRules();
        Hex desert = new Hex(TerrainType.DESERTS, 0);
        desert.setRobber(false);
        assertTrue(rules.canMoveRobberTo(desert));
    }
}
