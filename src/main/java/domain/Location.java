package domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Location)) {
            return false;
        }

        Location other = (Location) obj;

        return xPos == other.xPos && yPos == other.yPos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPos, yPos);
    }
}
