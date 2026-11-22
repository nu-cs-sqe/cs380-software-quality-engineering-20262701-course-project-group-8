package domain;

import java.util.List;

public final class Game {

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    private final List<Player> players;
    private GamePhase phase;
    private int currentPlayerIndex;

    public Game(List<Player> players) {
        validatePlayerCount(players);
s        this.players = List.copyOf(players);
        this.phase = GamePhase.NOT_STARTED;
        this.currentPlayerIndex = 0;
    }

    private static void validatePlayerCount(List<Player> players) {
        if (players.size() < MIN_PLAYERS) {
            throw new IllegalArgumentException("Error: Game must have at least 2 players");
        }
        if (players.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("Error: Game cannot have more than 4 players");
        }
    }

    public void startGame() {
        if (phase == GamePhase.NOT_STARTED) {
            this.phase = GamePhase.SETUP;
        } else {
            throw new IllegalStateException("Error: Game has already started");
        }
    }

    public GamePhase getPhase() {
        return this.phase;
    }

    public Player getCurrentPlayer() {
        if (phase == GamePhase.NOT_STARTED || phase == GamePhase.SETUP) {
            return null;
        }
        return this.players.get(this.currentPlayerIndex);
    }

    public void advanceTurn() {
        if (phase == GamePhase.PLAYING) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            throw new IllegalStateException("Error: Cannot advance turn in current phase");
        }
    }

    public void endGame(Player winner) {
        validateWinner(winner);

        if (phase == GamePhase.PLAYING) {
            this.phase = GamePhase.FINISHED;
            this.currentPlayerIndex = players.indexOf(winner);
        } else {
            throw new IllegalStateException("Error: Cannot end game in current phase");
        }
    }

    private void validateWinner(Player winner) {
        if (!players.contains(winner)) {
            throw new IllegalArgumentException("Error: Winner must be a player in this game");
        }
    }

    public Player getWinner() {
        if (phase == GamePhase.FINISHED) {
            return this.players.get(this.currentPlayerIndex);
        } else {
            return null;
        }
    }

    public void completeSetup() {
        if (phase == GamePhase.SETUP) {
            this.phase = GamePhase.PLAYING;
        } else {
            throw new IllegalStateException("Error: Cannot complete setup in current phase");
        }
    }

}
