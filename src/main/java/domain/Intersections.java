package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Intersections {
    private final Map<Location, Building> locationToBuilding;
    private final Map<Location, Hex> locationToHex;
    private final Map<Building, List<Location>> buildingToLocations;
    
    public Intersections() {
        this.locationToBuilding = new HashMap<>();
        this.locationToHex = new HashMap<>();
        this.buildingToLocations = new HashMap<>();
    }

    public void addHex(Location location, Hex hex) {
        if (location == null) {
            Objects.requireNonNull(location, "Error: Location cannot be a null value");
        }
        if (hex == null) {
            Objects.requireNonNull(hex, "Error: Hex cannot be a null value");
        }

        locationToHex.put(location, hex);
    }

    public void addBuilding(Location location, Building building) {
        if (location == null) {
            Objects.requireNonNull(location, "Error: Location cannot be a null value");
        }
        if (building == null) {
            Objects.requireNonNull(building, "Error: Building cannot be a null value");
        }
        locationToBuilding.put(location, building);
        buildingToLocations.computeIfAbsent(building, k -> new ArrayList<>()).add(location);
    }

    public Building getBuilding(Location location) {
        if (location == null) {
            Objects.requireNonNull(location, "Error: Location cannot be a null value");
        }
        return locationToBuilding.get(location);
    }

    public Hex getHex(Location location) {
        if (location == null) {
            Objects.requireNonNull(location, "Error: Location cannot be a null value");
        }
        return locationToHex.get(location);
    }

    public List<Hex> getNeighboringHexes(Location location) {
        if (location == null) {
            Objects.requireNonNull(location, "Error: Location cannot be a null value");
        }

        int x = location.getXPos();
        int y = location.getYPos();

        List<Hex> neighboringHexes = new ArrayList<>();

        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                if (dx != 0 || dy != 0) {
                    int nx = x + dx;
                    int ny = y + dy;

                    if (nx >= 0 && ny >= 0) {
                        Location adjacentLocation = new Location(nx, ny);
                        Hex adjacentHex = locationToHex.get(adjacentLocation);

                        if (adjacentHex != null) {
                            neighboringHexes.add(adjacentHex);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < neighboringHexes.size(); ++i) {
            System.out.println(neighboringHexes.get(i));
        }

        return neighboringHexes;
    }

    public int getBuildingCount(Building building) {
        if (building == null) {
            Objects.requireNonNull(building, "Error: Building cannot be a null value");
        }
        return (buildingToLocations.getOrDefault(building, new ArrayList<Location>())).size();
    }

    public List<Location> getBuildingLocations(Building building) {
        if (building == null) {
            Objects.requireNonNull(building, "Error: Building cannot be a null value");
        }
        return buildingToLocations.getOrDefault(building, new ArrayList<Location>());
    }
}