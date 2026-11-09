package domain;


public class Intersection {
    public Intersection(Location location, Building building) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
        if (building == null) {
            throw new NullPointerException("Error: Building cannot be a null value");
        }
    }

    public Building getBuilding(Location location) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
        return Building.CITY;
    }
}
