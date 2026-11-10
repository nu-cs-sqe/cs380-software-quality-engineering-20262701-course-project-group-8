package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HexTest {

    // TC1
    @Test
    public void constructor_nullTerrain_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hex(null, 0));
    }

    // TC2
    @Test
    public void constructor_desertZero_createsHex() {
        Hex hex = new Hex(TerrainType.DESERTS, 0);

        assertNotNull(hex);
        assertEquals(TerrainType.DESERTS, hex.getTerrainType());
        assertEquals(0, hex.getNumberDisc());
        assertTrue(hex.hasRobber());
    }

    // TC3
    @Test
    public void constructor_desertNegativeNumber_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hex(TerrainType.DESERTS, -1));
    }

    // TC4
    @Test
    public void constructor_desertOne_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hex(TerrainType.DESERTS, 1));
    }

    // TC5
    @Test
    public void constructor_nonDesertOne_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hex(TerrainType.FORESTS, 1));
    }

    // TC6
    @Test
    public void constructor_nonDesertTwo_createsHex() {
        Hex hex = new Hex(TerrainType.FORESTS, 2);

        assertNotNull(hex);
        assertEquals(TerrainType.FORESTS, hex.getTerrainType());
        assertEquals(2, hex.getNumberDisc());
    }

    // TC7
    @Test
    public void constructor_nonDesertSix_createsHex() {
        Hex hex = new Hex(TerrainType.FORESTS, 6);

        assertNotNull(hex);
        assertEquals(TerrainType.FORESTS, hex.getTerrainType());
        assertEquals(6, hex.getNumberDisc());
    }

    // TC8
    @Test
    public void constructor_nonDesertSeven_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hex(TerrainType.FORESTS, 7));
    }

    // TC9
    @Test
    public void constructor_nonDesertEight_createsHex() {
        Hex hex = new Hex(TerrainType.FORESTS, 8);

        assertNotNull(hex);
        assertEquals(TerrainType.FORESTS, hex.getTerrainType());
        assertEquals(8, hex.getNumberDisc());
    }

    // TC10
    @Test
    public void constructor_nonDesertTwelve_createsHex() {
        Hex hex = new Hex(TerrainType.FORESTS, 12);

        assertNotNull(hex);
        assertEquals(TerrainType.FORESTS, hex.getTerrainType());
        assertEquals(12, hex.getNumberDisc());
    }

    // TC11
    @Test
    public void constructor_nonDesertThirteen_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hex(TerrainType.FORESTS, 13));
    }

    // TC12
    @Test
    public void getTerrainType_returnsAssignedTerrain_Desert() {
        Hex desertHex = new Hex(TerrainType.DESERTS, 0);

        assertEquals(TerrainType.DESERTS, desertHex.getTerrainType());
    }

    // TC13
    @Test
    public void getTerrainType_returnsAssignedTerrain_Forest() {
        Hex forestHex = new Hex(TerrainType.FORESTS, 2);

        assertEquals(TerrainType.FORESTS, forestHex.getTerrainType());
    }

    // TC14
    @Test
    public void getNumberDisc_returnsAssignedToken() {
        Hex desertHex = new Hex(TerrainType.DESERTS, 0);

        assertEquals(0, desertHex.getNumberDisc());
    }

    // TC15
    @Test
    public void getNumberDisc_returnsAssignedToken_Forest_2() {
        Hex forestHex = new Hex(TerrainType.FORESTS, 2);

        assertEquals(2, forestHex.getNumberDisc());
    }

    // TC16
    @Test
    public void getNumberDisc_returnsAssignedToken_Forest_12() {
        Hex forestHex = new Hex(TerrainType.FORESTS, 12);

        assertEquals(12, forestHex.getNumberDisc());
    }

    // TC17
    @Test
    public void desertHex_startsWithRobber() {
        Hex hex = new Hex(TerrainType.DESERTS, 0);

        assertTrue(hex.hasRobber());
    }

    // TC18
    @Test
    public void setRobber_false_updatesStatus() {
        Hex hex = new Hex(TerrainType.DESERTS, 0);

        hex.setRobber(false);

        assertFalse(hex.hasRobber());
    }

    // TC19
    @Test
    public void setRobber_true_updatesStatus() {
        Hex hex = new Hex(TerrainType.FORESTS, 6);

        hex.setRobber(true);

        assertTrue(hex.hasRobber());
    }

    // TC20
    @Test
    public void producesResource_desertRollZero_returnsFalse() {
        Hex hex = new Hex(TerrainType.DESERTS, 0);

        assertFalse(hex.producesResource(0));
    }

    // TC21
    @Test
    public void producesResource_nonDesertMatchingRoll_returnsTrue() {
        Hex hex = new Hex(TerrainType.FORESTS, 2);

        assertTrue(hex.producesResource(2));
    }

    // TC22
    @Test
    public void producesResource_nonDesertZeroRoll_returnsFalse() {
        Hex hex = new Hex(TerrainType.FORESTS, 2);

        assertFalse(hex.producesResource(0));
    }

    // TC23
    @Test
    public void producesResource_nonDesertSixRoll_returnsFalse() {
        Hex hex = new Hex(TerrainType.FORESTS, 8);

        assertFalse(hex.producesResource(6));
    }

    // TC24
    @Test
    public void producesResource_nonDesertSevenRoll_returnsFalse() {
        Hex hex = new Hex(TerrainType.FORESTS, 8);

        assertFalse(hex.producesResource(7));
    }

    // TC25
    @Test
    public void producesResource_nonDesertEightRoll_returnsTrue() {
        Hex hex = new Hex(TerrainType.FORESTS, 8);

        assertTrue(hex.producesResource(8));
    }

    // TC26
    @Test
    public void producesResource_robberPresent_returnsFalse() {
        Hex hex = new Hex(TerrainType.FORESTS, 12);
        hex.setRobber(true);

        assertFalse(hex.producesResource(12));
    }

    // TC27
    @Test
    public void producesResource_robberAbsentMatchingRoll_returnsTrue() {
        Hex hex = new Hex(TerrainType.FORESTS, 12);
        hex.setRobber(false);

        assertTrue(hex.producesResource(12));
    }
}