package domain;

public class GameRules {
    public static final int MIN_PLAYERS = 3;
    public static final int MAX_PLAYERS = 4;
    public static final int DISCARD_THRESHOLD = 7;

    public boolean isValidPlayerCount(int playerCount) {
        return playerCount >= MIN_PLAYERS && playerCount <= MAX_PLAYERS;
    }

    public int getDiscardCount(int handSize) {
        if (handSize < 0) {
            throw new IllegalArgumentException("handSize must be >= 0");
        }
        if (handSize <= DISCARD_THRESHOLD) {
            return 0;
        }
        return handSize / 2;
    }
}