package domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Bank {
    public static final int RESOURCE_SUPPLY = 19;
    
    private final Map<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);

     public Bank() {
        this(new Random());
    }

    public Bank(Random random) {
        Objects.requireNonNull(random);
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, RESOURCE_SUPPLY);
        }
    }

    public int getResourceCount(ResourceType type) {
        Objects.requireNonNull(type);
        return resources.get(type);
    }
}