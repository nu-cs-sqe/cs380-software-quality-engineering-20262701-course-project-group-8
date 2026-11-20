package domain;

public class Player {
    String name;
    PlayerColor color;

    public Player(String name, PlayerColor color) {
        if (name == null) {
            throw new NullPointerException("Error: Name cannot be a null value");
        } else if (name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Error: Name cannot be an empty value");
        }
    }
}
