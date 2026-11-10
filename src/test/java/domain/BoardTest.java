package domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    // Helper function to create a mock Random
    private Random createRandomMock() {
        Random randomMock = mock(Random.class);

        expect(randomMock.nextInt(anyInt()))
                .andAnswer(() -> 0)
                .anyTimes();

        replay(randomMock);

        return randomMock;
    }

    // TC1
    @Test
    public void constructor_default_createsBoardWithNineteenHexes() {
        Board board = new Board();

        assertNotNull(board);
        assertEquals(19, board.getNumberOfHexes());
    }

    // TC2
    @Test
    public void constructor_nullRandom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Board(null));
    }

    // TC3
    @Test
    public void constructor_validRandom_createsBoard() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        assertNotNull(board);
        assertEquals(19, board.getNumberOfHexes());
        verify(randomMock);
    }

    // TC4
    @Test
    public void constructor_assignsCorrectTerrainDistribution() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        List<Hex> hexes = board.getHexes();

        assertEquals(4, hexes.stream()
                .filter(hex -> hex.getTerrainType() == TerrainType.FORESTS)
                .count());

        assertEquals(3, hexes.stream()
                .filter(hex -> hex.getTerrainType() == TerrainType.HILLS)
                .count());

        assertEquals(3, hexes.stream()
                .filter(hex -> hex.getTerrainType() == TerrainType.MOUNTAINS)
                .count());

        assertEquals(4, hexes.stream()
                .filter(hex -> hex.getTerrainType() == TerrainType.FIELDS)
                .count());

        assertEquals(4, hexes.stream()
                .filter(hex -> hex.getTerrainType() == TerrainType.PASTURES)
                .count());

        assertEquals(1, hexes.stream()
                .filter(hex -> hex.getTerrainType() == TerrainType.DESERTS)
                .count());
        verify(randomMock);
    }

    // TC5
    @Test
    public void constructor_desertHasZeroDiscAndNonDesertsHaveDiscs() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        List<Hex> hexes = board.getHexes();

        assertEquals(1, hexes.stream()
                .filter(hex -> hex.getTerrainType() == TerrainType.DESERTS
                        && hex.getNumberDisc() == 0)
                .count());

        assertEquals(18, hexes.stream()
                .filter(hex -> hex.getTerrainType() != TerrainType.DESERTS
                        && hex.getNumberDisc() != 0)
                .count());
        verify(randomMock);
    }

    // TC6
    @Test
    public void constructor_assignsValidNumberDiscs() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        Set<Integer> validDiscs = Set.of(
                2, 3, 4, 5, 6, 8, 9, 10, 11, 12
        );

        for (Hex hex : board.getHexes()) {
            if (hex.getTerrainType() != TerrainType.DESERTS) {
                assertTrue(validDiscs.contains(hex.getNumberDisc()));
                assertNotEquals(7, hex.getNumberDisc());
            }
        }
        verify(randomMock);
    }

    // TC7
    @Test
    public void constructor_exactlyOneRobberStartsOnDesert() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        List<Hex> hexes = board.getHexes();

        assertEquals(1, hexes.stream()
                .filter(Hex::hasRobber)
                .count());

        assertEquals(TerrainType.DESERTS,
                board.getRobberHex().getTerrainType());
        verify(randomMock);
    }
}