package domain;


public class Intersection {
    public Intersection(Location location, Building building) {
        if (location == null) {
            throw new NullPointerException("Error: Location cannot be a null value");
        }
    }
}
