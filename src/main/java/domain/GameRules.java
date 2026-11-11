package domain;

import java.util.Map;
import java.util.Objects;

public class GameRules {
    public static final int MIN_PLAYERS = 3;
    public static final int MAX_PLAYERS = 4;
    public static final int DISCARD_THRESHOLD = 7;
    public static final int MAX_DEVELOPMENT_CARDS_PER_TURN = 1;

    private static final Map<BuildingType, Map<ResourceType, Integer>> COSTS = Map.of(
            BuildingType.ROAD, Map.of(ResourceType.BRICK, 1, ResourceType.LUMBER, 1),
            BuildingType.SETTLEMENT, Map.of(ResourceType.BRICK, 1, ResourceType.LUMBER, 1,
                    ResourceType.WOOL, 1, ResourceType.GRAIN, 1),
            BuildingType.CITY, Map.of(ResourceType.ORE, 3, ResourceType.GRAIN, 2),
            BuildingType.DEVELOPMENT_CARD, Map.of(ResourceType.ORE, 1, ResourceType.WOOL, 1,
                    ResourceType.GRAIN, 1));

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

    public boolean canAfford(ResourceHand hand, BuildingType type) {
        Objects.requireNonNull(hand);
        Objects.requireNonNull(type);
        for (Map.Entry<ResourceType, Integer> entry : COSTS.get(type).entrySet()) {
            if (hand.getCount(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void payCost(ResourceHand hand, Bank bank, BuildingType type) {
        Objects.requireNonNull(bank);
        if (!canAfford(hand, type)) {
            throw new IllegalStateException("Hand cannot afford " + type);
        }
        for (Map.Entry<ResourceType, Integer> entry : COSTS.get(type).entrySet()) {
            hand.remove(entry.getKey(), entry.getValue());
            bank.receiveResource(entry.getKey(), entry.getValue());
        }
    }

    public boolean canMoveRobberTo(Hex target) {
        Objects.requireNonNull(target);
        return !target.hasRobber();
    }

    public boolean canPlayDevelopmentCard(DevelopmentCard card, int currentTurn, int cardsPlayedThisTurn) {
        Objects.requireNonNull(card);
        if (cardsPlayedThisTurn < 0) {
            throw new IllegalArgumentException("cardsPlayedThisTurn must be >= 0");
        }
        return cardsPlayedThisTurn < MAX_DEVELOPMENT_CARDS_PER_TURN && card.canBePlayed(currentTurn);
    }
}