package domain;

import java.util.Objects;

public class DevelopmentCard {
    private final DevelopmentCardType type;
    private final int turnPurchased;
    
    public DevelopmentCard(DevelopmentCardType type, int turnPurchased) {
        this.type = Objects.requireNonNull(type);
        this.turnPurchased = turnPurchased;
        if (turnPurchased < 0) {
            throw new IllegalArgumentException("turnPurchased must be >= 0");
        }
    }

    public DevelopmentCardType getType() {
        return type;
    }

    public boolean isVictoryPoint() {
        return type == DevelopmentCardType.VICTORY_POINT;
    }

    public boolean isPlayed() {
        return false;
    }

    public boolean canBePlayed(int currentTurn) {
        if (currentTurn < turnPurchased) {
            throw new IllegalArgumentException("currentTurn must be >= turnPurchased");
        }
        return false;
    }
}
