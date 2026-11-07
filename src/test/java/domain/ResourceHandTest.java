package domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceHandTest {
    // TC 1
    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void getCount_newHand_returnsZero(ResourceType type) {
        ResourceHand resourceHand = new ResourceHand();
        assertEquals(0, resourceHand.getCount(type));
    }

    // TC 2
    @Test
    public void getCount_nullType_throwsNullPointerException() {
        ResourceHand resourceHand = new ResourceHand();
        assertThrows(NullPointerException.class, () -> resourceHand.getCount(null));
    }

    // TC 3
    @Test
    public void getTotal_newHand_returnsZero() {
        ResourceHand resourceHand = new ResourceHand();
        assertEquals(0, resourceHand.getTotal());
    }

    // TC 4
    @Test
    public void getTotal_twoBrickThreeOre_returnsFive() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 2);
        resourceHand.add(ResourceType.ORE, 3);
        assertEquals(5, resourceHand.getTotal());
    }

    // TC 5
    @Test
    public void add_nullType_throwsNullPointerException() {
        ResourceHand resourceHand = new ResourceHand();
        assertThrows(NullPointerException.class, () -> resourceHand.add(null, 1));
    }

    // TC 6
    @Test
    public void add_zeroAmount_throwsIllegalArgumentException() {
        ResourceHand resourceHand = new ResourceHand();
        assertThrows(IllegalArgumentException.class, () -> resourceHand.add(ResourceType.BRICK, 0));
        assertEquals(0, resourceHand.getCount(ResourceType.BRICK));
    }

    // TC 7
    @Test
    public void add_oneBrickToNewHand_countIsOne() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 1);
        assertEquals(1, resourceHand.getCount(ResourceType.BRICK));
    }

    // TC 8
    @Test
    public void add_threeBrickToHandWithTwoBrick_countIsFive() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 2);
        resourceHand.add(ResourceType.BRICK, 3);
        assertEquals(5, resourceHand.getCount(ResourceType.BRICK));
    }

    // TC 9
    @ParameterizedTest
    @EnumSource(value=ResourceType.class, mode=EnumSource.Mode.EXCLUDE, names={"BRICK"})
    public void add_twoBrick_otherTypesStayZero(ResourceType othertype) {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 2);
        assertEquals(0, resourceHand.getCount(othertype));
    }

    // TC 10
    @Test
    public void remove_nullType_throwsNullPointerException() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 3);
        assertThrows(NullPointerException.class, () -> resourceHand.remove(null, 1));
    }

    // TC 11
    @Test
    public void remove_zeroAmount_throwsIllegalArgumentException() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 3);
        assertThrows(IllegalArgumentException.class, () -> resourceHand.remove(ResourceType.BRICK, 0));
        assertEquals(3, resourceHand.getCount(ResourceType.BRICK));
    }

    // TC 12
    @Test
    public void remove_oneBrickFromThree_countIsTwo() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 3);
        resourceHand.remove(ResourceType.BRICK, 1);
        assertEquals(2, resourceHand.getCount(ResourceType.BRICK));
    }

    // TC 13
    @Test
    public void remove_allThreeBrick_countIsZero() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 3);
        resourceHand.remove(ResourceType.BRICK, 3);
        assertEquals(0, resourceHand.getCount(ResourceType.BRICK));
    }

    // TC 14
    @Test
    public void remove_moreThanHeld_throwsIllegalStateException() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 3);
        assertThrows(IllegalStateException.class, () -> resourceHand.remove(ResourceType.BRICK, 4));
        assertEquals(3, resourceHand.getCount(ResourceType.BRICK));
    }

    // TC 15
    @Test
    public void has_nullType_throwsNullPointerException() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 3);
        assertThrows(NullPointerException.class, () -> resourceHand.has(null, 1));
    }

    // TC 16
    @Test
    public void has_zeroAmount_throwsIllegalArgumentException() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 3);
        assertThrows(IllegalArgumentException.class, () -> resourceHand.has(ResourceType.BRICK, 0));
    }

    // TC 17
    @Test 
    public void has_exactAmountHeld_returnsTrue() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 3);
        assertTrue(resourceHand.has(ResourceType.BRICK, 3));
    }

    // TC 18
    @Test
    public void has_moreThanHeld_returnsFalse() {
        ResourceHand resourceHand = new ResourceHand();
        resourceHand.add(ResourceType.BRICK, 3);
        assertFalse(resourceHand.has(ResourceType.BRICK, 4));
    }
}
