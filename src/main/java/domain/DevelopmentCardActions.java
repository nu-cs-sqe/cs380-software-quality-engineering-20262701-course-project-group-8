package domain;

import java.util.List;
import java.util.Objects;

public final class DevelopmentCardActions {
    private static final String CANNOT_PLAY_THIS_TURN = "Card cannot be played this turn";

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
        if (bank.getDevelopmentCardCount() == 0) {
            throw new IllegalStateException("No development cards left");
        }
        rules.payCost(hand, bank, PurchaseType.DEVELOPMENT_CARD);
        return bank.drawDevelopmentCard(currentTurn);
    }

    public void playYearOfPlenty(DevelopmentCard card, int currentTurn, ResourceHand hand, 
        ResourceType first, ResourceType second) {
        Objects.requireNonNull(card);
        Objects.requireNonNull(hand);
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);

        if (card.getType() != DevelopmentCardType.YEAR_OF_PLENTY) {
            throw new IllegalArgumentException("Card is not YEAR_OF_PLENTY");
        }
        if (!card.canBePlayed(currentTurn)) {
            throw new IllegalStateException(CANNOT_PLAY_THIS_TURN);
        }
        int firstNeeded = first == second ? 2 : 1;
        if (!bank.hasResource(first, firstNeeded) || !bank.hasResource(second, 1)) {
            throw new IllegalStateException("Bank does not have enough resources");
        }

        bank.giveResource(first, 1);
        hand.add(first, 1);
        bank.giveResource(second, 1);
        hand.add(second, 1);
        card.play(currentTurn);
    }

    public int playMonopoly(DevelopmentCard card, int currentTurn, ResourceHand hand, 
        List<ResourceHand> otherHands, ResourceType type) {
        Objects.requireNonNull(card);
        Objects.requireNonNull(hand);
        Objects.requireNonNull(otherHands);
        Objects.requireNonNull(type);
        if (card.getType() != DevelopmentCardType.MONOPOLY) {
            throw new IllegalArgumentException("Card is not MONOPOLY");
        }
        if (!card.canBePlayed(currentTurn)) {
            throw new IllegalStateException(CANNOT_PLAY_THIS_TURN);
        }
        if (otherHands.contains(hand)) {
            throw new IllegalArgumentException("otherHands must not contain hand");
        }

        int total = 0;
        for (ResourceHand other : otherHands) {
            int count = other.getCount(type);
            if (count > 0) {
                other.remove(type, count);
                hand.add(type, count);
                total += count;
            }
        }
        card.play(currentTurn);
        return total;
    }

    public void playKnight(DevelopmentCard card, int currentTurn, Board board, Hex target) {
        if (card.getType() != DevelopmentCardType.KNIGHT) {
            throw new IllegalArgumentException("Card is not KNIGHT");
        }
        if (!card.canBePlayed(currentTurn)) {
            throw new IllegalStateException(CANNOT_PLAY_THIS_TURN);
        }
        board.moveRobber(target);
        card.play(currentTurn);
    }
}