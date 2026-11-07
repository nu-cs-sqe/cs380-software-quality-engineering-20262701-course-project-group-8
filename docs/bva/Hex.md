# BVA Analysis: `Hex`

## Design Under Test

```java
public class Hex {
    public Hex(TerrainType terrainType, int numberDisc)
    public TerrainType getTerrainType()
    public int getNumberDisc()
    public boolean hasRobber()
    public void setRobber(boolean hasRobber)
    public boolean producesResource(int diceRoll)
}
```

- `Hex(TerrainType terrainType, int numberDisc)` rejects `null` terrain type with `IllegalArgumentException`. A desert must have a number token of 0. All other terrain types must have a number token between 2 and 12, excluding 7. The robber initially occupies the desert hex.
- `getTerrainType()` returns the hex's terrain type.
- `getNumberDisc()` returns the hex's assigned number token.
- `hasRobber()` returns whether the robber currently occupies the hex.
- `setRobber(boolean hasRobber)` updates the robber's location status.
- `producesResource(int diceRoll)` returns true only when the dice roll matches the hex's number, the hex is not a desert, and the robber is not occupying it.

## Step 1-3: Analysis

### Method: `Hex(TerrainType terrainType, int numberDisc)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `terrainType` | Enum/Pointer | `null`, `DESERT`, `FOREST` |
| Input | `numberDisc (desert)` | Integer | 0, -1, 1 |
| Input | `numberDisc (non-desert)` | Interval | 1, 2, 6, 7, 8, 12, 13 |
| Output | a new `Hex` object, or an exception | Cases | object created, `IllegalArgumentException` |

### Method: `getTerrainType()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | Hex object | Enum | `DESERT`, `FOREST` |
| Output | Terrain type | Cases | Correct terrain type returned |

### Method: `getNumberDisc()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | Hex object | Integer | 0, 2, 6, 8, 12 |
| Output | Number disc | Cases | Correct number returned |

### Method: `hasRobber()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | Hex object | Boolean | `true`, `false` |
| Output | Robber status | Cases | `true`, `false` |

### Method: `setRobber()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | hasRobber | Boolean | `true`, `false` |
| Output | Updated robber status | Cases | Robber present, robber absent |

### Method: `producesResource(int diceRoll)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | diceRoll | Interval | 0, 2, 6, 7, 8, 12 |
| Input | hasRobber | Boolean | `true`, `false` |
| Input | `terrainType` | Enum | `DESERT`, `FOREST` |
| Output | Whether hex produces a resource | Cases | `true`, `false` |

## Step 4: Test Cases

### Method under test: `Hex(TerrainType terrainType, int numberDisc)`
| | State of the System | Expected output | Status |
|---|---|---|---|
| Test Case 1 | `terrainType` is `null` | `IllegalArgumentException` is thrown | :white_check_mark: |
| Test Case 2 | `terrainType` is `DESERTS` and `numberDisc` is `0` | A valid `Hex` is created; desert robber starts on the hex | :white_check_mark: |
| Test Case 3 | `terrainType` is `DESERTS` and `numberDisc` is `-1` | `IllegalArgumentException` is thrown | :white_check_mark: |
| Test Case 4 | `terrainType` is `DESERTS` and `numberDisc` is `1` | `IllegalArgumentException` is thrown | :white_check_mark: |
| Test Case 5 | `terrainType` is non-desert and `numberDisc` is `1` | `IllegalArgumentException` is thrown | :white_check_mark: |
| Test Case 6 | `terrainType` is non-desert and `numberDisc` is `2` | A valid `Hex` is created | :white_check_mark: |
| Test Case 7 | `terrainType` is non-desert and `numberDisc` is `6` | A valid `Hex` is created | :white_check_mark: |
| Test Case 8 | `terrainType` is non-desert and `numberDisc` is `7` | `IllegalArgumentException` is thrown | :white_check_mark: |
| Test Case 9 | `terrainType` is non-desert and `numberDisc` is `8` | A valid `Hex` is created | :white_check_mark: |
| Test Case 10 | `terrainType` is non-desert and `numberDisc` is `12` | A valid `Hex` is created | :white_check_mark: |
| Test Case 11 | `terrainType` is non-desert and `numberDisc` is `13` | `IllegalArgumentException` is thrown | :white_check_mark: |

### Method under test: `getTerrainType()`
| | State of the System | Expected output | Status |
|---|---|---|---|
| Test Case 12 | Hex is created with `DESERTS` terrain | Returns `DESERTS` | :white_check_mark: |
| Test Case 13 | Hex is created with `FOREST` terrain | Returns `FOREST` | :white_check_mark: |

### Method under test: `getNumberDisc()`
| | State of the System | Expected output | Status |
|---|---|---|---|
| Test Case 14 | Desert hex with `numberDisc = 0` | Returns `0` | :white_check_mark: |
| Test Case 15 | Non-desert hex with `numberDisc = 2` | Returns `2` | :white_check_mark: |
| Test Case 16 | Non-desert hex with `numberDisc = 12` | Returns `12` | :white_check_mark: |

### Method under test: `hasRobber()` and `setRobber(boolean hasRobber)`
| | State of the System | Expected output | Status |
|---|---|---|---|
| Test Case 17 | New desert hex is created | `hasRobber()` returns `true` | :white_check_mark: |
| Test Case 18 | `setRobber(false)` is called on a desert hex | `hasRobber()` returns `false` | :white_check_mark: |
| Test Case 19 | `setRobber(true)` is called on a non-desert hex | `hasRobber()` returns `true` | :white_check_mark: |

### Method under test: `producesResource(int diceRoll)`
| | State of the System | Expected output | Status |
|---|---|---|---|
| Test Case 20 | `terrainType` is `DESERTS`, `numberDisc = 0`, and `diceRoll = 0` | Returns `false` | :white_check_mark: |
| Test Case 21 | Non-desert hex with `numberDisc = 2`, robber absent, and `diceRoll = 2` | Returns `true` | :white_check_mark: |
| Test Case 22 | Non-desert hex with `numberDisc = 2`, robber absent, and `diceRoll = 0` | Returns `false` | :white_check_mark: |
| Test Case 23 | Non-desert hex with `numberDisc = 8`, robber absent, and `diceRoll = 6` | Returns `false` | :white_check_mark: |
| Test Case 24 | Non-desert hex with `numberDisc = 8`, robber absent, and `diceRoll = 7` | Returns `false` | :white_check_mark: |
| Test Case 25 | Non-desert hex with `numberDisc = 8`, robber absent, and `diceRoll = 8` | Returns `true` | :white_check_mark: |
| Test Case 26 | Non-desert hex with `numberDisc = 12`, robber present, and `diceRoll = 12` | Returns `false` | :white_check_mark: |
| Test Case 27 | Non-desert hex with `numberDisc = 12`, robber absent, and `diceRoll = 12` | Returns `true` | :white_check_mark: |