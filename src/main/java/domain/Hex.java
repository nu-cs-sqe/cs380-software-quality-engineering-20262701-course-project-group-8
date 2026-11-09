package domain;

public class Hex {

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
            if (numberDisc < 2 || numberDisc > 12 || numberDisc == 7) {
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

    public void setRobber(boolean hasRobber) {

    }

    public boolean producesResource(int diceRoll) {
        return false;
    }
}
