package domain;

import java.util.Objects;

public class DevelopmentCard {
    public DevelopmentCard(DevelopmentCardType type, int turnPurchased) {
        Objects.requireNonNull(type);
    }
}
