package domain;

import java.util.Objects;

public final class DevelopmentCardActions {

    public DevelopmentCardActions(GameRules rules, Bank bank) {
        Objects.requireNonNull(rules);
        Objects.requireNonNull(bank);
    }

    public DevelopmentCard buyDevelopmentCard(ResourceHand hand, int currentTurn) {
        Objects.requireNonNull(hand);
        if (currentTurn < 0) {
            throw new IllegalArgumentException("currentTurn must be >= 0");
        }
        return null;
    }
}