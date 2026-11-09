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
}