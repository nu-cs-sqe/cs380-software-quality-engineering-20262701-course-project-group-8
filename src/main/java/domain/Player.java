package domain;

public class Player {
    String name;
    PlayerColor color;
    ResourceHand resourceHand;
    public Player(String name, PlayerColor color) {
        if (name == null) {
            throw new NullPointerException("Error: Name cannot be a null value");
        } else if (name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Error: Name cannot be an empty value");
        }

        if (color == null) {
            throw new NullPointerException("Error: Color cannot be a null value");
        }

        this.name = name;
        this.color = color;
        this.resourceHand = new ResourceHand();
    }

    public String getName() {
        return name;
    }
    
    public PlayerColor getColor() {
        return color;
    }

    public int getResourceCount(ResourceType resource) {
        if (resource == null) {
            throw new NullPointerException("Error: Resource cannot be a null value");
        }
        return resourceHand.getCount(resource);
    }

    public void addResource(ResourceType resourceType, int amount) {
        if (resourceType == null) {
            throw new NullPointerException("Error: Resource cannot be a null value");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Error: Amount cannot be a non-positive value");
        }

        resourceHand.add(resourceType, amount);
    }
}
