package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.easymock.EasyMock.*;

public class GameTest {

    // Helper method to create mock Player objects
    private List<Player> createPlayers(int count) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            players.add(mock(Player.class));
        }

        return players;
    }

    // Helper method to create a game with the specified number of players
    private Game createGame(int count) {
        return new Game(createPlayers(count));
    }

    // TC1
    @Test
    public void constructor_nullPlayers_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Game(null));
    }

    // TC2
    @Test
    public void constructor_onePlayer_throwsIllegalArgumentException() {
        List<Player> players = createPlayers(1);

        assertThrows(IllegalArgumentException.class,
                () -> new Game(players));
    }

    // TC3
    @Test
    public void constructor_twoPlayers_createsGame() {
        List<Player> players = createPlayers(2);

        Game game = new Game(players);

        assertNotNull(game);
    }

    // TC4
    @Test
    public void constructor_threePlayers_createsGame() {
        List<Player> players = createPlayers(3);

        Game game = new Game(players);

        assertNotNull(game);
    }

    // TC5
    @Test
    public void constructor_fourPlayers_createsGame() {
        List<Player> players = createPlayers(4);

        Game game = new Game(players);

        assertNotNull(game);
    }

    // TC6
    @Test
    public void constructor_fivePlayers_throwsIllegalArgumentException() {
        List<Player> players = createPlayers(5);

        assertThrows(IllegalArgumentException.class,
                () -> new Game(players));
    }
}