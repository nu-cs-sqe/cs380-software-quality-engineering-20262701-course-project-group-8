package domain;

// Hex class represents a hexagonal tile in the game. numberDisc cannot be 7
// because it is reserved for the robber. The desert hex must has a numberDisc of 0
// because it does not produce resources. The robber starts on the desert hex.

// terrainType: type of terrain on the hex
// numberDisc: determines when the hex produces resources based on dice rolls
// hasRobber: indicates if the robber is on this hex
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
        this.hasRobber = hasRobber;
    }

    public boolean producesResource(int diceRoll) {
        return !hasRobber && terrainType != TerrainType.DESERTS && numberDisc == diceRoll;
    }
}