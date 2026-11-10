package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Board {

    private static final List<TerrainType> TERRAIN = Arrays.asList(
        TerrainType.FORESTS,
        TerrainType.FORESTS,
        TerrainType.FORESTS,
        TerrainType.FORESTS,

        TerrainType.HILLS,
        TerrainType.HILLS,
        TerrainType.HILLS,

        TerrainType.MOUNTAINS,
        TerrainType.MOUNTAINS,
        TerrainType.MOUNTAINS,

        TerrainType.FIELDS,
        TerrainType.FIELDS,
        TerrainType.FIELDS,
        TerrainType.FIELDS,

        TerrainType.PASTURES,
        TerrainType.PASTURES,
        TerrainType.PASTURES,
        TerrainType.PASTURES,

        TerrainType.DESERTS
    );

    private static final List<Integer> NUMBER_DISCS = Arrays.asList(
        2, 3, 3, 4, 4, 5, 5, 6, 6,
        8, 8, 9, 9, 10, 10, 11, 11, 12
    );

    private final List<Hex> hexes;
    private final Random random;

    public Board() {
        this(new Random());
    }

    public Board(Random random) {
        this.random = Objects.requireNonNull(random);
        this.hexes = new ArrayList<>();

        initializeBoard();
    }

    private void initializeBoard() {
        List<TerrainType> terrains = new ArrayList<>(TERRAIN);
        List<Integer> numberDiscs = new ArrayList<>(NUMBER_DISCS);

        Collections.shuffle(terrains, random);
        Collections.shuffle(numberDiscs, random);

        int numberIndex = 0;

        for (TerrainType terrain : terrains) {

            int numberDisc;

            if (terrain == TerrainType.DESERTS) {
                numberDisc = 0;
            } else {
                numberDisc = numberDiscs.get(numberIndex);
                numberIndex++;
            }

            Hex hex = new Hex(terrain, numberDisc);
            hexes.add(hex);
        }
    }

    public List<Hex> getHexes() {
        return Collections.unmodifiableList(hexes);
    }

    public Hex getHex(int index) {
        return hexes.get(index);
    }

    public int getNumberOfHexes() {
        return hexes.size();
    }

    public Hex getRobberHex() {
        for (Hex hex : hexes) {
            if (hex.hasRobber()) {
                return hex;
            }
        }

        throw new IllegalStateException("Error: No hex currently has the robber");
    }

    public void moveRobber(Hex targetHex) {
        if (targetHex == null) {
            throw new IllegalArgumentException("Error: Target hex cannot be null");
        }

        if (!hexes.contains(targetHex)) {
            throw new IllegalArgumentException("Error: Target hex does not belong to this board");
        }

        Hex currentHex = getRobberHex();

        if (currentHex == targetHex) {
            throw new IllegalArgumentException("Error: Robber must move to a different hex");
        }

        currentHex.setRobber(false);
        targetHex.setRobber(true);
    }

    public List<Hex> getHexesProducingResource(int diceRoll) {
        return null;
    }
}
