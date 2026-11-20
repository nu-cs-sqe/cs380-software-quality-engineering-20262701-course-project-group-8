package domain;

import java.util.Objects;

public final class DevelopmentCardActions {
    private final GameRules rules;
    private final Bank bank;

    public DevelopmentCardActions(GameRules rules, Bank bank) {
        this.rules = Objects.requireNonNull(rules);
        this.bank = Objects.requireNonNull(bank);
    }

    public DevelopmentCard buyDevelopmentCard(ResourceHand hand, int currentTurn) {
        Objects.requireNonNull(hand);
        if (currentTurn < 0) {
            throw new IllegalArgumentException("currentTurn must be >= 0");
        }
        rules.payCost(hand, bank, PurchaseType.DEVELOPMENT_CARD);
        return bank.drawDevelopmentCard(currentTurn);
    }
}