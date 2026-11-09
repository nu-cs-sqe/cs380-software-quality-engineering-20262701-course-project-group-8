package domain;

import java.util.List;

public class Game {

    private List<Player> players;
    private GamePhase phase;
    private int currentPlayerIndex;

    public Game(List<Player> players) {
        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("Error: Game must have at least 2 players");
        }
        if (players.size() > 4) {
            throw new IllegalArgumentException("Error: Game cannot have more than 4 players");
        }
        this.players = players;
        this.phase = GamePhase.NOT_STARTED;
        this.currentPlayerIndex = 0;
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
        if (phase == GamePhase.PLAYING) {
            this.phase = GamePhase.FINISHED;
        } else {
            throw new IllegalStateException("Error: Cannot end game in current phase");
        }
    }

    public Player getWinner() {
        return null;
    }

    public void completeSetup() {
        if (phase == GamePhase.SETUP) {
            this.phase = GamePhase.PLAYING;
        } else {
            throw new IllegalStateException("Error: Cannot complete setup in current phase");
        }
    }

}
