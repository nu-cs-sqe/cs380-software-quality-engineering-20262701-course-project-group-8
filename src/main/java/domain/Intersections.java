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
    private static final String LOCATION_CANNOT_BE_NULL_MESSAGE =
                            "Error: Location cannot be a null value";
    private static final String BUILDING_CANNOT_BE_NULL_MESSAGE = 
                            "Error: Building cannot be a null value";
    private static final String HEX_CANNOT_BE_NULL_MESSAGE = 
                            "Error: Hex cannot be a null value";

    public Intersections() {
        this.locationToBuilding = new HashMap<>();
        this.locationToHex = new HashMap<>();
        this.buildingToLocations = new HashMap<>();
    }

    public void addHex(Location location, Hex hex) {
        Objects.requireNonNull(location, LOCATION_CANNOT_BE_NULL_MESSAGE);
        Objects.requireNonNull(hex, HEX_CANNOT_BE_NULL_MESSAGE);

        locationToHex.put(location, hex);
    }

    public void addBuilding(Location location, Building building) {
        Objects.requireNonNull(location, LOCATION_CANNOT_BE_NULL_MESSAGE);
        Objects.requireNonNull(building, BUILDING_CANNOT_BE_NULL_MESSAGE);

        locationToBuilding.put(location, building);
        buildingToLocations.computeIfAbsent(building, k -> new ArrayList<>()).add(location);
    }

    public Building getBuilding(Location location) {
        Objects.requireNonNull(location, LOCATION_CANNOT_BE_NULL_MESSAGE);

        return locationToBuilding.get(location);
    }

    public Hex getHex(Location location) {
        Objects.requireNonNull(location, LOCATION_CANNOT_BE_NULL_MESSAGE);

        return locationToHex.get(location);
    }

    public List<Hex> getNeighboringHexes(Location location) {
        Objects.requireNonNull(location, LOCATION_CANNOT_BE_NULL_MESSAGE);

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

        return neighboringHexes;
    }

    public int getBuildingCount(Building building) {
        Objects.requireNonNull(building, BUILDING_CANNOT_BE_NULL_MESSAGE);

        return (buildingToLocations.getOrDefault(building, new ArrayList<Location>())).size();
    }

    public List<Location> getBuildingLocations(Building building) {
        Objects.requireNonNull(building, BUILDING_CANNOT_BE_NULL_MESSAGE);

        return buildingToLocations.getOrDefault(building, new ArrayList<Location>());
    }
}