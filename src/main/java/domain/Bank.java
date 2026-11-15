package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Bank {
    public static final int RESOURCE_SUPPLY = 19;

    public static final int DEVELOPMENT_CARD_SUPPLY = 25;
    private static final int KNIGHT_CARDS = 14;
    private static final int VICTORY_POINT_CARDS = 5;
    private static final int PROGRESS_CARDS_PER_TYPE = 2;
    private static final String INVALID_AMOUNT_MESSAGE = "Amount must be at least 1";

    private final List<DevelopmentCardType> developmentCards = new ArrayList<>();
    private final Map<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);

    public Bank() {
        this(new Random());
    }

    public Bank(Random random) {
        Objects.requireNonNull(random);
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, RESOURCE_SUPPLY);
        }
        addDevelopmentCards(DevelopmentCardType.KNIGHT, KNIGHT_CARDS);
        addDevelopmentCards(DevelopmentCardType.VICTORY_POINT, VICTORY_POINT_CARDS);
        addDevelopmentCards(DevelopmentCardType.ROAD_BUILDING, PROGRESS_CARDS_PER_TYPE);
        addDevelopmentCards(DevelopmentCardType.YEAR_OF_PLENTY, PROGRESS_CARDS_PER_TYPE);
        addDevelopmentCards(DevelopmentCardType.MONOPOLY, PROGRESS_CARDS_PER_TYPE);
        Collections.shuffle(developmentCards, random);
    }

    private void addDevelopmentCards(DevelopmentCardType type, int count) {
        for (int i = 0; i < count; i++) {
            developmentCards.add(type);
        }
    }

    public int getResourceCount(ResourceType type) {
        Objects.requireNonNull(type);
        return resources.get(type);
    }

    public boolean hasResource(ResourceType type, int amount) {
        Objects.requireNonNull(type);
        if (amount < 1) {
            throw new IllegalArgumentException(INVALID_AMOUNT_MESSAGE);
        }
        return getResourceCount(type) >= amount;
    }

    public void giveResource(ResourceType type, int amount) {
        Objects.requireNonNull(type);
        if (amount < 1) {
            throw new IllegalArgumentException(INVALID_AMOUNT_MESSAGE);
        }
        int current = getResourceCount(type);
        if (amount > current) {
            throw new IllegalStateException("Bank does not have enough " + type);
        }
        resources.put(type, getResourceCount(type) - amount);
    }

    public void receiveResource(ResourceType type, int amount) {
        Objects.requireNonNull(type);
        if (amount < 1) {
            throw new IllegalArgumentException(INVALID_AMOUNT_MESSAGE);
        }
        int current = getResourceCount(type);
        if (current + amount > RESOURCE_SUPPLY) {
            throw new IllegalStateException(
                    "Bank cannot hold more than " + RESOURCE_SUPPLY + " " + type);
        }
        resources.put(type, current + amount);
    }

    public int getDevelopmentCardCount() {
        return developmentCards.size();
    }

    public DevelopmentCard drawDevelopmentCard(int currentTurn) {
        if (currentTurn < 0) {
            throw new IllegalArgumentException("currentTurn must be >= 0");
        }
        if (developmentCards.isEmpty()) {
            throw new IllegalStateException("No development cards left");
        }
        DevelopmentCardType type = developmentCards.remove(developmentCards.size() - 1);
        return new DevelopmentCard(type, currentTurn);
    }
}