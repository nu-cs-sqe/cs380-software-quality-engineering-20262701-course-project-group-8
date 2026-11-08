package domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bank {
    public static final int RESOURCE_SUPPLY = 19;
    
    public static final int DEVELOPMENT_CARD_SUPPLY = 25;
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
        addDevelopmentCards(DevelopmentCardType.KNIGHT, 14);
        addDevelopmentCards(DevelopmentCardType.VICTORY_POINT, 5);
        addDevelopmentCards(DevelopmentCardType.ROAD_BUILDING, 2);
        addDevelopmentCards(DevelopmentCardType.YEAR_OF_PLENTY, 2);
        addDevelopmentCards(DevelopmentCardType.MONOPOLY, 2);
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
            throw new IllegalArgumentException("Amount must be at least 1");
        }
        return getResourceCount(type) >= amount;
    }

    public void giveResource(ResourceType type, int amount) {
        Objects.requireNonNull(type);
        if (amount < 1) {
            throw new IllegalArgumentException("Amount must be at least 1");
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
            throw new IllegalArgumentException("Amount must be at least 1");
        }
        int current = getResourceCount(type);
        if (current + amount > RESOURCE_SUPPLY) {
            throw new IllegalStateException("Bank cannot hold more than " + RESOURCE_SUPPLY + " " + type);
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