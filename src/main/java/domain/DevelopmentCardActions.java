package domain;

import java.util.Objects;

public final class DevelopmentCardActions {
    private final GameRules rules;
    
    public DevelopmentCardActions(GameRules rules, Bank bank) {
        this.rules = Objects.requireNonNull(rules);
        Objects.requireNonNull(bank);
    }

    public DevelopmentCard buyDevelopmentCard(ResourceHand hand, int currentTurn) {
        Objects.requireNonNull(hand);
        if (currentTurn < 0) {
            throw new IllegalArgumentException("currentTurn must be >= 0");
        }
        if (!rules.canAfford(hand, PurchaseType.DEVELOPMENT_CARD)) {
            throw new IllegalStateException("Hand cannot afford a development card");
        }
        return null;
    }
}