package domain;

import java.util.Random;
import java.util.Objects;

public class Dice {
    private static final int SIDES = 6;

    private final Random random;

    public Dice(Random random) {
        this.random = Objects.requireNonNull(random);
    }

    public int roll() {
        return rollOneDie() + rollOneDie();
    }

    private int rollOneDie() {
        return random.nextInt(SIDES) + 1;
    }
}
