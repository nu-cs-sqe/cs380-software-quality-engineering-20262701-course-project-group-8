package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intersections {
    private static final Map<Location, Building> locationToBuilding = new HashMap<>();
    private static final Map<Location, Hex> locationToHex = new HashMap<>();

    public Intersections() {}

    public static void addHex(Location location, Hex hex) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
        if (hex == null) {
            throw new NullPointerException("Error: Hex cannot be a null value");
        }

        locationToHex.put(location, hex);
    }

    public static void addBuilding(Location location, Building building) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
        if (building == null) {
            throw new NullPointerException("Error: Building cannot be a null value");
        }
        locationToBuilding.put(location, building);
    }

    public static Building getBuilding(Location location) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
        return locationToBuilding.get(location);
    }

    public static Hex getHex(Location location) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
        return locationToHex.get(location);
    }

    public static List<Hex> getNeighboringHexes(Location location) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }

        int x = location.getXPos();
        int y = location.getYPos();

        List<Hex> neighboringHexes = new ArrayList<>();
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) if (dx != 0 || dy != 0) {
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

        for (int i = 0; i < neighboringHexes.size(); ++i) {
            System.out.println(neighboringHexes.get(i));
        }

        return neighboringHexes;
    }



}
