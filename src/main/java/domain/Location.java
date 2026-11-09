package domain;

public class Location {
    public final int xPos;
    public final int yPos;
    public Location(int xPos, int yPos) {
        if (xPos < 0) {
            throw new IllegalArgumentException("Error: x-coordinate position cannot be negative");
        }
        if (yPos < 0) {
            throw new IllegalArgumentException("Error: y-coordinate position cannot be negative");
        }
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
