package domain;

import java.util.HashMap;
import java.util.Map;

public class Intersection {
    Map<Location, Building> locationToBuilding;
    public Intersection(Location location, Building building) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
        if (building == null) {
            throw new NullPointerException("Error: Building cannot be a null value");
        }
        locationToBuilding = new HashMap();
        locationToBuilding.put(location, building);
    }

    public Building getBuilding(Location location) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
        return locationToBuilding.get(location);
    }
}
