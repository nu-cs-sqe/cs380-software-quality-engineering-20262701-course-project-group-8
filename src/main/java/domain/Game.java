package domain;

import java.util.List;

public class Game {

    private final List<Player> players;
    private final GamePhase phase;
    private final int currentPlayerIndex;

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

    }

    public GamePhase getPhase() {

    }

    public Player getCurrentPlayer() {
        
    }

    public void advanceTurn() {

    }

    public void endGame(Player winner) {

    }

    public Player getWinner() {

    }

    public void completeSetup() {

    }

}
