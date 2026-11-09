package domain;

public class Location {
    private final int xPos;
    private final int yPos;
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

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
