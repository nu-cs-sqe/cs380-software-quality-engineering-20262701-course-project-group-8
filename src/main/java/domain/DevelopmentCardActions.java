package domain;

import java.util.Objects;

public final class DevelopmentCardActions {

    public DevelopmentCardActions(GameRules rules, Bank bank) {
        Objects.requireNonNull(rules);
        Objects.requireNonNull(bank);
    }
}