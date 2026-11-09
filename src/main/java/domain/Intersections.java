package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intersections {
    Map<Location, Building> locationToBuilding;
    Map<Location, Hex> locationToHex;

    public Intersections() {
        locationToBuilding = new HashMap<>();
        locationToHex = new HashMap<>();
    }

    public void addIntersection(Location location, Building building) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
        if (building == null) {
            throw new NullPointerException("Error: Building cannot be a null value");
        }
        locationToBuilding.put(location, building);
    }

    public Building getBuilding(Location location) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
        return locationToBuilding.get(location);
    }

    public List<Hex> getNeighboringHexes(Location location) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }

        return new ArrayList<>();
    }


}
