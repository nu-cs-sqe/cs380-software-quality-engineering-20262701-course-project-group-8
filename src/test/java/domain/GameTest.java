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

    // Helper method to start a game and complete setup
    private Game createPlayingGame(List<Player> players) {
        Game game = new Game(players);
        game.startGame();
        game.completeSetup();
        return game;
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

    // TC7
    @Test
    public void startGame_notStarted_changesPhaseToSetup() {
        Game game = createGame(2);

        game.startGame();

        assertEquals(GamePhase.SETUP, game.getPhase());
    }

    // TC8
    @Test
    public void startGame_assignsFirstPlayerAsCurrentPlayer() {
        List<Player> players = createPlayers(2);
        Game game = new Game(players);

        game.startGame();
        game.completeSetup();
        assertSame(players.get(0), game.getCurrentPlayer());
    }

    // TC9
    @Test
    public void startGame_alreadyInSetup_throwsIllegalStateException() {
        Game game = createGame(2);
        game.startGame();

        assertThrows(IllegalStateException.class,
                game::startGame);
    }

    // TC10
    @Test
    public void startGame_alreadyPlaying_throwsIllegalStateException() {
        Game game = createPlayingGame(createPlayers(2));

        assertThrows(IllegalStateException.class,
                game::startGame);
    }

    // TC11
    @Test
    public void startGame_alreadyFinished_throwsIllegalStateException() {
        List<Player> players = createPlayers(2);
        Game game = createPlayingGame(players);
        game.endGame(players.get(0));

        assertThrows(IllegalStateException.class,
                game::startGame);
    }

    // TC12
    @Test
    public void getPhase_newGame_returnsNotStarted() {
        Game game = createGame(2);

        assertEquals(GamePhase.NOT_STARTED, game.getPhase());
    }

    // TC13
    @Test
    public void getPhase_afterStartGame_returnsSetup() {
        Game game = createGame(2);
        game.startGame();

        assertEquals(GamePhase.SETUP, game.getPhase());
    }

    // TC14
    @Test
    public void getPhase_afterCompletingSetup_returnsPlaying() {
        Game game = createGame(2);
        game.startGame();

        game.completeSetup();

        assertEquals(GamePhase.PLAYING, game.getPhase());
    }

    // TC15
    @Test
    public void getPhase_afterEndingGame_returnsFinished() {
        List<Player> players = createPlayers(2);
        Game game = createPlayingGame(players);

        game.endGame(players.get(0));

        assertEquals(GamePhase.FINISHED, game.getPhase());
    }

    // TC16
    @Test
    public void getCurrentPlayer_gameNotStarted_returnsNull() {
        Game game = createGame(2);

        assertNull(game.getCurrentPlayer());
    }

    // TC17
    @Test
    public void getCurrentPlayer_gameStarted_returnsFirstPlayer() {
        List<Player> players = createPlayers(2);
        Game game = new Game(players);

        game.startGame();
        game.completeSetup();
        assertSame(players.get(0), game.getCurrentPlayer());
    }

    // TC18
    @Test
    public void getCurrentPlayer_firstPlayersTurn_returnsFirstPlayer() {
        List<Player> players = createPlayers(2);
        Game game = createPlayingGame(players);
    
        assertSame(players.get(0), game.getCurrentPlayer());
    }

    // TC19
    @Test
    public void getCurrentPlayer_lastPlayersTurn_returnsLastPlayer() {
        List<Player> players = createPlayers(4);
        Game game = createPlayingGame(players);

        game.advanceTurn();
        game.advanceTurn();
        game.advanceTurn();

        assertSame(players.get(3), game.getCurrentPlayer());
    }

    // TC20
    @Test
    public void advanceTurn_gameNotStarted_throwsIllegalStateException() {
        Game game = createGame(2);

        assertThrows(IllegalStateException.class,
                game::advanceTurn);
    }

    // TC21
    @Test
    public void advanceTurn_gameInSetup_throwsIllegalStateException() {
        Game game = createGame(2);
        game.startGame();

        assertThrows(IllegalStateException.class,
                game::advanceTurn);
    }

    // TC22
    @Test
    public void advanceTurn_twoPlayers_firstPlayerAdvancesToSecond() {
        List<Player> players = createPlayers(2);
        Game game = createPlayingGame(players);

        game.advanceTurn();

        assertSame(players.get(1), game.getCurrentPlayer());
    }

    // TC23
    @Test
    public void advanceTurn_twoPlayers_lastPlayerWrapsToFirst() {
        List<Player> players = createPlayers(2);
        Game game = createPlayingGame(players);

        game.advanceTurn();
        game.advanceTurn();

        assertSame(players.get(0), game.getCurrentPlayer());
    }

    // TC24
    @Test
    public void advanceTurn_threePlayers_firstPlayerAdvancesToSecond() {
        List<Player> players = createPlayers(3);
        Game game = createPlayingGame(players);

        game.advanceTurn();

        assertSame(players.get(1), game.getCurrentPlayer());
    }

    // TC25
    @Test
    public void advanceTurn_fourPlayers_secondPlayerAdvancesToThird() {
        List<Player> players = createPlayers(4);
        Game game = createPlayingGame(players);

        game.advanceTurn();
        game.advanceTurn();

        assertSame(players.get(2), game.getCurrentPlayer());
    }

    // TC26
    @Test
    public void advanceTurn_fourPlayers_lastPlayerWrapsToFirst() {
        List<Player> players = createPlayers(4);
        Game game = createPlayingGame(players);

        game.advanceTurn();
        game.advanceTurn();
        game.advanceTurn();
        game.advanceTurn();

        assertSame(players.get(0), game.getCurrentPlayer());
    }

    // TC27
    @Test
    public void advanceTurn_gameFinished_throwsIllegalStateException() {
        List<Player> players = createPlayers(2);
        Game game = createPlayingGame(players);
        game.endGame(players.get(0));

        assertThrows(IllegalStateException.class,
                game::advanceTurn);
    }

    // TC28
    @Test
    public void endGame_nullWinner_throwsIllegalArgumentException() {
        Game game = createPlayingGame(createPlayers(2));

        assertThrows(IllegalArgumentException.class,
                () -> game.endGame(null));
    }

    // TC29
    @Test
    public void endGame_validWinner_changesPhaseAndRecordsWinner() {
        List<Player> players = createPlayers(2);
        Game game = createPlayingGame(players);

        game.endGame(players.get(0));

        assertEquals(GamePhase.FINISHED, game.getPhase());
        assertSame(players.get(0), game.getWinner());
    }

    // TC30
    @Test
    public void endGame_externalWinner_throwsIllegalArgumentException() {
        List<Player> players = createPlayers(2);
        Game game = createPlayingGame(players);
        Player externalPlayer = mock(Player.class);

        assertThrows(IllegalArgumentException.class,
                () -> game.endGame(externalPlayer));
    }

    // TC31
    @Test
    public void endGame_gameNotStarted_throwsIllegalStateException() {
        List<Player> players = createPlayers(2);
        Game game = new Game(players);

        assertThrows(IllegalStateException.class,
                () -> game.endGame(players.get(0)));
    }

    // TC32
    @Test
    public void endGame_gameInSetup_throwsIllegalStateException() {
        List<Player> players = createPlayers(2);
        Game game = new Game(players);
        game.startGame();

        assertThrows(IllegalStateException.class,
                () -> game.endGame(players.get(0)));
    }

    // TC33
    @Test
    public void endGame_gameAlreadyFinished_throwsIllegalStateException() {
        List<Player> players = createPlayers(2);
        Game game = createPlayingGame(players);
        game.endGame(players.get(0));

        assertThrows(IllegalStateException.class,
                () -> game.endGame(players.get(1)));
    }
}