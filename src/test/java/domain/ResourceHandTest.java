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
}
