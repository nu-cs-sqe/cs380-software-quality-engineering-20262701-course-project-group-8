package domain;

import java.util.List;
import java.util.Objects;

public class Player {
    private String name;
    private PlayerColor color;
    private ResourceHand resourceHand;
    private int victoryPoints;
    private Intersections intersections;
    private static final String NAME_CANNOT_BE_NULL_MESSAGE = 
                        "Error: Name cannot be a null value";
    private static final String COLOR_CANNOT_BE_NULL_MESSAGE = 
                        "Error: Color cannot be a null value";
    private static final String RESOURCE_CANNOT_BE_NULL_MESSAGE = 
                        "Error: Resource cannot be a null value";

    public Player(String name, PlayerColor color) {
        Objects.requireNonNull(name, NAME_CANNOT_BE_NULL_MESSAGE);
        Objects.requireNonNull(color, COLOR_CANNOT_BE_NULL_MESSAGE);
        if (name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Error: Name cannot be an empty value");
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
        Objects.requireNonNull(resource, RESOURCE_CANNOT_BE_NULL_MESSAGE);

        return resourceHand.getCount(resource);
    }

    public void addResource(ResourceType resourceType, int amount) {
        Objects.requireNonNull(resourceType, RESOURCE_CANNOT_BE_NULL_MESSAGE);

        if (amount <= 0) {
            throw new IllegalArgumentException("Error: Amount cannot be a non-positive value");
        }

        resourceHand.add(resourceType, amount);
    }

    public void removeResource(ResourceType resource, int amount) {
        Objects.requireNonNull(resource, RESOURCE_CANNOT_BE_NULL_MESSAGE);

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
    
    public int getCityCount() {
        return intersections.getBuildingCount(Building.CITY);
    }

    public List<Location> getCityLocations() {
        return intersections.getBuildingLocations(Building.CITY);
    }

    public int getSettlementCount() {
        return intersections.getBuildingCount(Building.SETTLEMENT);
    }

    public List<Location> getSettlementLocations() {
        return intersections.getBuildingLocations(Building.SETTLEMENT);
    }

    public int getRoadCount() {
        return intersections.getBuildingCount(Building.ROAD);
    }

    public List<Location> getRoadLocations() {
        return intersections.getBuildingLocations(Building.ROAD);
    }
}
