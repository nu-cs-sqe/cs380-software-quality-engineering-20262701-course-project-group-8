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

    // TC8
    @Test
    public void getHexes_returnsAllNineteenHexes() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        assertEquals(19, board.getHexes().size());
        verify(randomMock);
    }

    // TC9
    @Test
    public void getHexes_addToList_throwsUnsupportedOperationException() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        assertThrows(UnsupportedOperationException.class,
                () -> board.getHexes().add(
                        new Hex(TerrainType.FORESTS, 2)
                ));
        verify(randomMock);
    }

    // TC10
    @Test
    public void getHexes_removeFromList_throwsUnsupportedOperationException() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        assertThrows(UnsupportedOperationException.class,
                () -> board.getHexes().remove(0));
        verify(randomMock);
    }

    // TC11
    @Test
    public void getHex_negativeIndex_throwsIndexOutOfBoundsException() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        assertThrows(IndexOutOfBoundsException.class,
                () -> board.getHex(-1));
        verify(randomMock);
    }

    // TC12
    @Test
    public void getHex_zeroIndex_returnsFirstHex() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        assertNotNull(board.getHex(0));
        assertSame(board.getHexes().get(0), board.getHex(0));
        verify(randomMock);
    }

    // TC13
    @Test
    public void getHex_middleIndex_returnsTenthHex() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        assertNotNull(board.getHex(9));
        assertSame(board.getHexes().get(9), board.getHex(9));
        verify(randomMock);
    }

    // TC14
    @Test
    public void getHex_lastValidIndex_returnsNineteenthHex() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        assertNotNull(board.getHex(18));
        assertSame(board.getHexes().get(18), board.getHex(18));
        verify(randomMock);
    }

    // TC15
    @Test
    public void getHex_indexNineteen_throwsIndexOutOfBoundsException() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        assertThrows(IndexOutOfBoundsException.class,
                () -> board.getHex(19));
        verify(randomMock);
    }

    // TC16
    @Test
    public void getNumberOfHexes_returnsNineteen() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        assertEquals(19, board.getNumberOfHexes());
        verify(randomMock);
    }

    // TC17
    @Test
    public void getRobberHex_initiallyReturnsDesertHex() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        Hex robberHex = board.getRobberHex();

        assertNotNull(robberHex);
        assertEquals(TerrainType.DESERTS, robberHex.getTerrainType());
        assertTrue(robberHex.hasRobber());
        verify(randomMock);
    }

    // TC18
    @Test
    public void getRobberHex_afterMovingRobber_returnsNewHex() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        Hex originalHex = board.getRobberHex();
        Hex targetHex = board.getHexes().stream()
                .filter(hex -> hex.getTerrainType() != TerrainType.DESERTS)
                .findFirst()
                .orElseThrow();

        board.moveRobber(targetHex);

        assertFalse(originalHex.hasRobber());
        assertTrue(targetHex.hasRobber());
        assertSame(targetHex, board.getRobberHex());
        verify(randomMock);
    }

    // TC19
    @Test
    public void getRobberHex_noRobber_throwsIllegalStateException() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        board.getRobberHex().setRobber(false);

        assertThrows(IllegalStateException.class,
                board::getRobberHex);
        verify(randomMock);
    }

    // TC20
    @Test
    public void moveRobber_nullTarget_throwsIllegalArgumentException() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        assertThrows(IllegalArgumentException.class,
                () -> board.moveRobber(null));
        verify(randomMock);
    }

    // TC21
    @Test
    public void moveRobber_externalHex_throwsIllegalArgumentException() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        Hex externalHex = new Hex(TerrainType.FORESTS, 8);

        assertThrows(IllegalArgumentException.class,
                () -> board.moveRobber(externalHex));
        verify(randomMock);
    }

    // TC22
    @Test
    public void moveRobber_sameHex_throwsIllegalArgumentException() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        Hex currentHex = board.getRobberHex();

        assertThrows(IllegalArgumentException.class,
                () -> board.moveRobber(currentHex));
        verify(randomMock);
    }

    // TC23
    @Test
    public void moveRobber_differentHex_movesRobberSuccessfully() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        Hex originalHex = board.getRobberHex();
        Hex targetHex = board.getHexes().stream()
                .filter(hex -> hex != originalHex)
                .findFirst()
                .orElseThrow();

        board.moveRobber(targetHex);

        assertFalse(originalHex.hasRobber());
        assertTrue(targetHex.hasRobber());
        assertSame(targetHex, board.getRobberHex());
        verify(randomMock);
    }

    // TC24
    @Test
    public void moveRobber_fromNonDesertToAnotherHex_updatesRobberPosition() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        Hex firstTarget = board.getHexes().stream()
                .filter(hex -> hex.getTerrainType() != TerrainType.DESERTS)
                .findFirst()
                .orElseThrow();

        board.moveRobber(firstTarget);

        Hex secondTarget = board.getHexes().stream()
                .filter(hex -> hex != firstTarget)
                .findFirst()
                .orElseThrow();

        board.moveRobber(secondTarget);

        assertFalse(firstTarget.hasRobber());
        assertTrue(secondTarget.hasRobber());
        assertSame(secondTarget, board.getRobberHex());
        verify(randomMock);
    }

    // TC25
    @Test
    public void getHexesProducingResource_rollTwo_returnsMatchingHex() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        List<Hex> producingHexes = board.getHexesProducingResource(2);

        assertEquals(1, producingHexes.size());
        assertEquals(2, producingHexes.get(0).getNumberDisc());
        assertFalse(producingHexes.get(0).hasRobber());
        verify(randomMock);
    }

    // TC26
    @Test
    public void getHexesProducingResource_rollEight_returnsAllEligibleHexes() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        List<Hex> expectedHexes = board.getHexes().stream()
                .filter(hex -> hex.getNumberDisc() == 8)
                .filter(hex -> !hex.hasRobber())
                .collect(Collectors.toList());

        List<Hex> producingHexes = board.getHexesProducingResource(8);

        assertEquals(expectedHexes, producingHexes);
        assertTrue(producingHexes.size() >= 1);
        assertTrue(producingHexes.size() <= 2);
        verify(randomMock);
    }

    // TC27
    @Test
    public void getHexesProducingResource_robberOnMatchingHex_excludesThatHex() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);

        Hex targetHex = board.getHexes().stream()
                .filter(hex -> hex.getNumberDisc() == 8)
                .findFirst()
                .orElseThrow();

        board.moveRobber(targetHex);

        List<Hex> producingHexes = board.getHexesProducingResource(8);

        assertFalse(producingHexes.contains(targetHex));
        verify(randomMock);
    }

    // TC28
    @Test
    public void getHexesProducingResource_desertHex_doesNotProduceResource() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        List<Hex> producingHexes = board.getHexesProducingResource(0);

        assertTrue(producingHexes.isEmpty());
        verify(randomMock);
    }

    // TC29
    @Test
    public void getHexesProducingResource_rollSix_returnsMatchingHexesWithoutRobber() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        List<Hex> producingHexes = board.getHexesProducingResource(6);

        assertTrue(producingHexes.size() >= 1);
        assertTrue(producingHexes.size() <= 2);

        for (Hex hex : producingHexes) {
            assertEquals(6, hex.getNumberDisc());
            assertFalse(hex.hasRobber());
        }
        verify(randomMock);
    }

    // TC30
    @Test
    public void getHexesProducingResource_rollSeven_returnsEmptyList() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        List<Hex> producingHexes = board.getHexesProducingResource(7);

        assertTrue(producingHexes.isEmpty());
        verify(randomMock);
    }

    // TC31
    @Test
    public void getHexesProducingResource_rollOne_returnsEmptyList() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        List<Hex> producingHexes = board.getHexesProducingResource(1);

        assertTrue(producingHexes.isEmpty());
        verify(randomMock);
    }

    // TC32
    @Test
    public void getHexesProducingResource_rollThirteen_returnsEmptyList() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        List<Hex> producingHexes = board.getHexesProducingResource(13);

        assertTrue(producingHexes.isEmpty());
        verify(randomMock);
    }

    // TC33
    @Test
    public void getHexesProducingResource_robberOnOnlyMatchingHex_returnsEmptyList() {
        Random randomMock = createRandomMock();
        Board board = new Board(randomMock);
        Hex twoHex = board.getHexes().stream()
                .filter(hex -> hex.getNumberDisc() == 2)
                .findFirst()
                .orElseThrow();

        board.moveRobber(twoHex);

        List<Hex> producingHexes = board.getHexesProducingResource(2);

        assertTrue(producingHexes.isEmpty());
        verify(randomMock);
    }
}