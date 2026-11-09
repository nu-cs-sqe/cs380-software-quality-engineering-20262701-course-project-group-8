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

    public static List<Hex> getNeighboringHexes(Location location) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }

        return new ArrayList<>();
    }



}
