package domain;

public class Hex {

    private static final int MIN_NUMBER = 2;
    private static final int MAX_NUMBER = 12;
    private static final int ROBBER_NUMBER = 7;

    private final TerrainType terrainType;
    private final int numberDisc;
    private boolean hasRobber;

    public Hex(TerrainType terrainType, int numberDisc) {
        if (terrainType == null) {
            throw new IllegalArgumentException("Error: Terrain type cannot be null");
        }

        if (terrainType == TerrainType.DESERTS) {
            if (numberDisc != 0) {
                throw new IllegalArgumentException("Error: Desert hex cannot have a number token");
            }
        } else {
            if (numberDisc < MIN_NUMBER || numberDisc > MAX_NUMBER || numberDisc == ROBBER_NUMBER) {
                throw new IllegalArgumentException("Error: Invalid number token");
            }
        }

        this.terrainType = terrainType;
        this.numberDisc = numberDisc;
        this.hasRobber = terrainType == TerrainType.DESERTS;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public int getNumberDisc() {
        return numberDisc;
    }

    public boolean hasRobber() {
        return hasRobber;
    }

    public void setRobber(boolean robberStatus) {
        this.hasRobber = robberStatus;
    }

    public boolean producesResource(int diceRoll) {
        return !hasRobber && terrainType != TerrainType.DESERTS && numberDisc == diceRoll;
    }
}
