package domain;

public class Player {
    String name;
    PlayerColor color;
    ResourceHand resourceHand;
    int victoryPoints;
    Intersections intersections;
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
        this.victoryPoints = 0;
        this.intersections = new Intersections();
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

    public void removeResource(ResourceType resource, int amount) {
        if (resource == null) {
            throw new NullPointerException("Error: Resource cannot be a null value");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Error: Amount cannot be a non-positive value");
        }
        
        if (amount > getResourceCount(resource)) {
            throw new IllegalStateException("Not enough " + ResourceType.WOOL + " to remove");
        }
        resourceHand.remove(resource, amount);
    }

    public void addVictoryPoints(int points) {
        if (points <= 0) {
            throw new IllegalArgumentException("Error: Points cannot be a non-positive value");
        }

        victoryPoints += points;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
    

}
