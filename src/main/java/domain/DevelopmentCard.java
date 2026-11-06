package domain;

import java.util.Objects;

public class DevelopmentCard {
    private final DevelopmentCardType type;
    
    public DevelopmentCard(DevelopmentCardType type, int turnPurchased) {
        this.type = Objects.requireNonNull(type);
        if (turnPurchased < 0) {
            throw new IllegalArgumentException("turnPurchased must be >= 0");
        }
    }

    public DevelopmentCardType getType() {
        return type;
    }
}
