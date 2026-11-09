package domain;

public class Location {
    public final int xPos;
    public Location(int xPos, int yPos) {
        if (xPos < 0) {
            throw new IllegalArgumentException("Error: x-coordinate position cannot be negative");
        }
        this.xPos = xPos;
    }
}
