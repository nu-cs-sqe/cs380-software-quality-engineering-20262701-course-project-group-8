package domain;

import java.util.Map;
import java.util.EnumMap;
import java.util.Objects;

public class ResourceHand {
    private final Map<ResourceType, Integer> counts = new EnumMap<>(ResourceType.class);
    public int getCount(ResourceType type) {
        Objects.requireNonNull(type);
        return counts.getOrDefault(type, 0);
    }

    public int getTotal() {
        int total = 0;
        for (int count: counts.values()) {
            total += count;
        }
        return total;
    }

    public void add(ResourceType type, int amount) {
        Objects.requireNonNull(type);
        if (amount < 1) {
            throw new IllegalArgumentException("Amount must be at least 1");
        }
        counts.put(type, getCount(type) + amount);
    }
}
