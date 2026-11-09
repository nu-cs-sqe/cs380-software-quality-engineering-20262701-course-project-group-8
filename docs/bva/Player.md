# BVA Analysis: `Player`

## Design Under Test

```java
public class Player {
    public Player(String name, PlayerColor color);
    public String getName();
    public PlayerColor getColor();
    public void addResource(Resource resource, int amount);
    public void removeResource(Resource resource, int amount);
    public int getResourceCount(Resource resource);
    public void addVictoryPoints(int points);
    public int getVictoryPoints();
    public int getCityCount();
    public Location[] getCityLocations();
    public int getSettlementCount();
    public Location[] getSettlementLocations();
    public int getRoadCount();
    public Location[] getRoadLocations();
}
```

- `Player(String name, PlayerColor color)` creates a player with a non-null, non-blank name and valid player color. Rejects `null` with `NullPointerException` and blank names with `IllegalArgumentException`.
- `getName()` returns the player's name.
- `getColor()` returns the player's color.
- `addResource(Resource resource, int amount)` adds resources to the player's inventory. Rejects null resources with `NullPointerException` and nonpositive amounts with `IllegalArgumentException`.
- `removeResource(Resource resource, int amount)` removes resources from the player's inventory. Rejects null resources with `NullPointerException`, nonpositive amounts with `IllegalArgumentException`, and insufficient resources with `IllegalStateException`.
- `getResourceCount(Resource resource)` returns the quantity of a specified resource. Rejects null resources with `NullPointerException`.
- `addVictoryPoints(int points)` increases the player's victory points. Rejects nonpositive amounts with `IllegalArgumentException`.
- `getVictoryPoints()` returns the player's current victory points.
- `getCityCount()` returns the number of cities owned by the player.
- `getCityLocations()` returns an array containing the locations of all cities owned by the player.
- `getSettlementCount()` returns the number of settlements owned by the player.
- `getSettlementLocations()` returns an array containing the locations of all settlements owned by the player.
- `getRoadCount()` returns the number of roads owned by the player.
- `getRoadLocations()` returns an array containing the locations of all roads owned by the player.

## Current Project Assumptions

- Each player has a name and a color.
- `PlayerColor` is an enum representing available player colors.
- `Resource` is an enum containing `BRICK`, `WOOD`, `SHEEP`, `WHEAT`, and `ORE`.
- Players initially have 0 resources and 0 victory points.
- Players initially own 0 cities, settlements, and roads.
- Resource quantities, victory points, and building counts cannot be negative.
- Resource additions and victory-point additions cannot cause integer overflow.
- A player cannot spend more resources than they own.
- A player wins upon reaching 10 victory points, but 10 is not a maximum score.
- Each player can own at most 4 cities, 5 settlements, and 15 roads, based on standard Catan piece supplies.
- Each owned city, settlement, and road has a corresponding `Location` representation.
- Location arrays are empty when the player owns no structures of that type.
- Location arrays never return `null`.
- The order of locations in the arrays is unspecified.

## Step 1–3: Analysis

### Method: `Player(String name, PlayerColor color)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `name` | Pointer / String | `null`, empty, blank, valid non-blank |
| Input | `color` | Pointer | `null`, valid non-null |
| Output | Player construction or exception | Cases | Player created, `NullPointerException`, `IllegalArgumentException` |

### Method: `getName()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | Player's name | String | Valid non-blank name |

### Method: `getColor()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | Player's color | Cases | Valid `PlayerColor` enum constant |

### Method: `addResource(Resource resource, int amount)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `resource` | Pointer | `null`, valid non-null |
| Input | `amount` | Number | [1, Integer.MAX_VALUE] |
| Output | Resource inventory updated or exception | Cases | Resources added, `NullPointerException`, `IllegalArgumentException`, overflow exception |

### Method: `removeResource(Resource resource, int amount)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `resource` | Pointer | `null`, valid non-null |
| Input | `amount` | Number | [1, current resource count] |
| Output | Resource inventory updated or exception | Cases | Resources removed, `NullPointerException`, `IllegalArgumentException`, `IllegalStateException` |

### Method: `getResourceCount(Resource resource)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `resource` | Pointer | `null`, valid non-null |
| Output | Quantity of specified resource | Number / Cases | [0, Integer.MAX_VALUE], `NullPointerException` |

### Method: `addVictoryPoints(int points)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `points` | Number | [1, Integer.MAX_VALUE] |
| Output | Victory points updated or exception | Cases | Points increased, `IllegalArgumentException`, overflow exception |

### Method: `getVictoryPoints()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | Current victory point total | Number | [0, Integer.MAX_VALUE] |

### Method: `getCityCount()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | Number of cities owned by the player | Number | [0, 4] |

### Method: `getCityLocations()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | Array of locations containing the player's cities | Collection | Empty array, one location, multiple locations |

### Method: `getSettlementCount()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | Number of settlements owned by the player | Number | [0, 5] |

### Method: `getSettlementLocations()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | Array of locations containing the player's settlements | Collection | Empty array, one location, multiple locations |

### Method: `getRoadCount()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | Number of roads owned by the player | Number | [0, 15] |

### Method: `getRoadLocations()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | Array of locations containing the player's roads | Collection | Empty array, one location, multiple locations |

## Step 4: Test Cases

### Method under test: `Player(String name, PlayerColor color)`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 1 | `name = null`, `color = RED` | `NullPointerException` | :x: |
| Test Case 2 | `name = ""`, `color = RED` | `IllegalArgumentException` | :x: |
| Test Case 3 | `name = " "`, `color = RED` | `IllegalArgumentException` | :x: |
| Test Case 4 | `name = "Alice"`, `color = null` | `NullPointerException` | :x: |
| Test Case 5 | `name = "Alice"`, `color = RED` | Player created successfully | :x: |

### Method under test: `getName()`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 6 | Player constructed with name `"Alice"` | `"Alice"` | :x: |

### Method under test: `getColor()`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 7 | Player constructed with color `RED` | `PlayerColor.RED` | :x: |

### Method under test: `addResource(Resource resource, int amount)`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 8 | `resource = null`, `amount = 1` | `NullPointerException` | :x: |
| Test Case 9 | `resource = WOOD`, `amount = -1` | `IllegalArgumentException` | :x: |
| Test Case 10 | `resource = WOOD`, `amount = 0` | `IllegalArgumentException` | :x: |
| Test Case 11 | Player has 0 wood, add 1 wood | Wood quantity becomes 1 | :x: |
| Test Case 12 | Player has 1 wood, add 1 wood | Wood quantity becomes 2 | :x: |

### Method under test: `removeResource(Resource resource, int amount)`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 13 | `resource = null`, `amount = 1` | `NullPointerException` | :x: |
| Test Case 14 | Player has 2 wood, remove -1 | `IllegalArgumentException` | :x: |
| Test Case 15 | Player has 2 wood, remove 0 | `IllegalArgumentException` | :x: |
| Test Case 16 | Player has 2 wood, remove 1 | Wood quantity becomes 1 | :x: |
| Test Case 17 | Player has 2 wood, remove 2 | Wood quantity becomes 0 | :x: |
| Test Case 18 | Player has 2 wood, remove 3 | `IllegalStateException` | :x: |

### Method under test: `getResourceCount(Resource resource)`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 19 | `resource = null` | `NullPointerException` | :x: |
| Test Case 20 | Player has 0 brick | 0 | :x: |
| Test Case 21 | Player has 1 brick | 1 | :x: |

### Method under test: `addVictoryPoints(int points)`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 22 | `points = -1`, current points = 0 | `IllegalArgumentException` | :x: |
| Test Case 23 | `points = 0`, current points = 0 | `IllegalArgumentException` | :x: |
| Test Case 24 | `points = 1`, current points = 0 | Victory points become 1 | :x: |
| Test Case 25 | `points = 1`, current points = 9 | Victory points become 10 | :x: |

### Method under test: `getVictoryPoints()`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 26 | Newly constructed player | 0 | :x: |
| Test Case 27 | Player has 10 victory points | 10 | :x: |

### Method under test: `getCityCount()`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 28 | Player owns 0 cities | 0 | :x: |
| Test Case 29 | Player owns 1 city | 1 | :x: |
| Test Case 30 | Player owns 4 cities | 4 | :x: |

### Method under test: `getCityLocations()`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 31 | Player owns 0 cities | Empty `Location[]` | :x: |
| Test Case 32 | Player owns 1 city at (0, 0) | Array containing location (0, 0) | :x: |
| Test Case 33 | Player owns 2 cities at (0, 0) and (1, 1) | Array containing both locations | :x: |

### Method under test: `getSettlementCount()`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 34 | Player owns 0 settlements | 0 | :x: |
| Test Case 35 | Player owns 1 settlement | 1 | :x: |
| Test Case 36 | Player owns 5 settlements | 5 | :x: |

### Method under test: `getSettlementLocations()`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 37 | Player owns 0 settlements | Empty `Location[]` | :x: |
| Test Case 38 | Player owns 1 settlement at (0, 0) | Array containing location (0, 0) | :x: |
| Test Case 39 | Player owns 2 settlements at (0, 0) and (1, 1) | Array containing both locations | :x: |

### Method under test: `getRoadCount()`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 40 | Player owns 0 roads | 0 | :x: |
| Test Case 41 | Player owns 1 road | 1 | :x: |
| Test Case 42 | Player owns 15 roads | 15 | :x: |

### Method under test: `getRoadLocations()`

| | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| Test Case 43 | Player owns 0 roads | Empty `Location[]` | :x: |
| Test Case 44 | Player owns 1 road at a valid location | Array containing that location | :x: |
| Test Case 45 | Player owns 2 roads at different valid locations | Array containing both locations | :x: |