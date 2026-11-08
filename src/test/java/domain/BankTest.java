package domain;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    // TC 8
    @Test
    public void giveResource_nullType_throwsNullPointerException() {
        Bank bank = new Bank();
        assertThrows(NullPointerException.class, () -> bank.giveResource(null, 1));
    }

    // TC 9
    @Test 
    public void giveResource_zeroAmount_throwsIllegalArgumentException() {
        Bank bank = new Bank();
        assertThrows(IllegalArgumentException.class, () -> bank.giveResource(ResourceType.BRICK, 0));
        assertEquals(19, bank.getResourceCount(ResourceType.BRICK));
    }

    // TC 10
    @Test
    public void giveResource_oneBrick_leavesEighteen() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, 1);
        assertEquals(18, bank.getResourceCount(ResourceType.BRICK));
    }
    
    // TC 11
    @Test
    public void giveResource_allNineteenBrick_leavesZero() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, 19);
        assertEquals(0, bank.getResourceCount(ResourceType.BRICK));
    }

    // TC 12
    @Test
    public void giveResource_twentyBrick_throwsIllegalStateException() {
        Bank bank = new Bank();
        assertThrows(IllegalStateException.class, () -> bank.giveResource(ResourceType.BRICK, 20));
        assertEquals(19, bank.getResourceCount(ResourceType.BRICK));
    }

    // TC 13
    @Test
    public void giveResource_twoBrick_otherTypesUnchanged() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, 2);
        assertEquals(19, bank.getResourceCount(ResourceType.LUMBER));
        assertEquals(19, bank.getResourceCount(ResourceType.ORE));
        assertEquals(19, bank.getResourceCount(ResourceType.GRAIN));
        assertEquals(19, bank.getResourceCount(ResourceType.WOOL));
    }

    // TC 14
    @Test
    public void receiveResource_nullType_throwsNullPointerException() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, 3);
        assertThrows(NullPointerException.class, () -> bank.receiveResource(null, 1));
    }

    // TC 15
    @Test
    public void receiveResource_zeroAmount_throwsIllegalArgumentException() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, 3);
        assertThrows(IllegalArgumentException.class, () -> bank.receiveResource(ResourceType.BRICK, 0));
        assertEquals(16, bank.getResourceCount(ResourceType.BRICK));
    }

    // TC 16
    @Test 
    public void receiveResource_oneBrick_becomesSeventeen() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, 3);
        bank.receiveResource(ResourceType.BRICK, 1);
        assertEquals(17, bank.getResourceCount(ResourceType.BRICK));
    }

    // TC 17
    @Test
    public void receiveResource_threeBrick_backToNineteen() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, 3);
        bank.receiveResource(ResourceType.BRICK, 3);
        assertEquals(19, bank.getResourceCount(ResourceType.BRICK));
    }

    // TC 18
    @Test
    public void receiveResource_fourBrick_throwsIllegalStateException() {
        Bank bank = new Bank();
        bank.giveResource(ResourceType.BRICK, 3);
        assertThrows(IllegalStateException.class, () -> bank.receiveResource(ResourceType.BRICK, 4));
        assertEquals(16, bank.getResourceCount(ResourceType.BRICK));
    }

    // TC 19
    @Test
    public void getDevelopmentCardCount_newBank_returnsTwentyFive() {
        Bank bank = new Bank();
        assertEquals(25, bank.getDevelopmentCardCount());
    }

    // TC 20
    @Test
    public void drawDevelopmentCard_turnZero_returnsCardPurchasedOnTurnZero() {
        Bank bank = new Bank();
        DevelopmentCard card = bank.drawDevelopmentCard(0);
        assertFalse(card.canBePlayed(0));
        assertEquals(24, bank.getDevelopmentCardCount());
    }

    // TC 21
    @Test
    public void drawDevelopmentCard_negativeTurn_throwsIllegalArgumentException() {
        Bank bank = new Bank();
        assertThrows(IllegalArgumentException.class, ()->bank.drawDevelopmentCard(-1));
        assertEquals(25, bank.getDevelopmentCardCount());
    }

    // TC 22
    @Test
    public void drawDevelopmentCard_drawAll_matchesDeckMix() {
        Bank bank = new Bank();
        Map<DevelopmentCardType, Integer> counts = new EnumMap<>(DevelopmentCardType.class);
        for (int i = 0; i < 25; i++) {
            DevelopmentCardType type = bank.drawDevelopmentCard(0).getType();
            counts.put(type, counts.getOrDefault(type, 0) + 1);
        }
        assertEquals(14, counts.get(DevelopmentCardType.KNIGHT));
        assertEquals(5, counts.get(DevelopmentCardType.VICTORY_POINT));
        assertEquals(2, counts.get(DevelopmentCardType.ROAD_BUILDING));
        assertEquals(2, counts.get(DevelopmentCardType.YEAR_OF_PLENTY));
        assertEquals(2, counts.get(DevelopmentCardType.MONOPOLY));
        assertEquals(0, bank.getDevelopmentCardCount());
    }

    // TC 23
    @Test
    public void drawDevelopmentCard_emptyDeck_throwsIllegalStateException() {
        Bank bank = new Bank();
        for (int i = 0; i < 25; i++) {
            bank.drawDevelopmentCard(0);
        }
        assertThrows(IllegalStateException.class, () -> bank.drawDevelopmentCard(0));
    }

    // TC 24
    @Test
    public void drawDevelopmentCard_sameSeed_sameOrder() {
        Bank first = new Bank(new Random(42));
        Bank second = new Bank(new Random(42));
        List<DevelopmentCardType> firstOrder = new ArrayList<>();
        List<DevelopmentCardType> secondOrder = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            firstOrder.add(first.drawDevelopmentCard(0).getType());
            secondOrder.add(second.drawDevelopmentCard(0).getType());
        }
        assertEquals(firstOrder, secondOrder);
    }
}