package domain;

import java.util.Objects;

public class DevelopmentCard {
    public DevelopmentCard(DevelopmentCardType type, int turnPurchased) {
        Objects.requireNonNull(type);
        if (turnPurchased < 0) {
            throw new IllegalArgumentException("turnPurchased must be >= 0");
        }
    }
}
