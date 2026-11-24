package domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class ResourceHand {
    private static final String INVALID_AMOUNT_MESSAGE = "Amount must be at least 1";

    private final Map<ResourceType, Integer> counts = new EnumMap<>(ResourceType.class);

    public int getCount(ResourceType type) {
        Objects.requireNonNull(type);
        return counts.getOrDefault(type, 0);
    }

    public int getTotal() {
        int total = 0;
        for (int count : counts.values()) {
            total += count;
        }
        return total;
    }

    public void add(ResourceType type, int amount) {
        Objects.requireNonNull(type);
        if (amount < 1) {
            throw new IllegalArgumentException(INVALID_AMOUNT_MESSAGE);
        }
        counts.put(type, getCount(type) + amount);
    }

    public void remove(ResourceType type, int amount) {
        Objects.requireNonNull(type);
        if (amount < 1) {
            throw new IllegalArgumentException(INVALID_AMOUNT_MESSAGE);
        }
        int current = getCount(type);
        if (amount > current) {
            throw new IllegalStateException("Not enough " + type + " to remove");
        }
        counts.put(type, current - amount);
    }

    public boolean has(ResourceType type, int amount) {
        Objects.requireNonNull(type);
        if (amount < 1) {
            throw new IllegalArgumentException(INVALID_AMOUNT_MESSAGE);
        }
        return getCount(type) >= amount;
    }

    public ResourceType removeRandom(Random random) {
        throw new IllegalStateException("Cannot remove from an empty hand");
    }
}
