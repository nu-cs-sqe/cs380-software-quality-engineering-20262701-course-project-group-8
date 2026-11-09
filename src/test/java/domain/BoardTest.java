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
}