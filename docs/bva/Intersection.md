# BVA Analysis: `Intersection`

## Design Under Test

```java
public class Intersection {
    public Intersection(Location location, Building building);
    public Building getBuilding(Location location);
    public List<Hex> getNeighboringHexes(Location location);
}
```
- 'Intersection(Location location, Building building) rejects 'null' location with 'NullPointerException'. The location must be a valid location on the game board, otherwise rejects invalid location with 'IllegalArgumentException'. Rejects 'null' building with 'NullPointerException'. The building must be either a Settlement or City on the game board, otherwise rejects invalid location with 'IllegalArgumentException'.
- 'getBuilding(Location location) rejects 'null' location with 'NullPointerException'. The location must be a valid location on the game board, otherwise rejects invalid location with 'IllegalArgumentException'. Returns the Building, which is a Settlement or City.
- 'getNeighboringHexes(Location location)' rejects 'null' location with 'NullPointerException'. The location must be a valid location on the game board, otherwise rejects invalid location with 'IllegalArgumentException'. Returns a List of neighboring hexes adjacent to the location.

## Step 1-3: Analysis

### Method: `Intersection(Location location, Building building)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `Location` | Pointer | `null`, valid non-null, invalid non-null |
| Input | `Building` | Pointer | `null`, valid non-null, invalid non-null |
| Output | a new 'Intersection' type or an exception | Cases | Intersection Object, `NullPointerException`, 'IllegalArgumentException' |

### Method: `getBuilding(Location location)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `Location` | Pointer | `null`, non-null |
| Output | the Building at this intersection or an exception | Cases | Building object, `NullPointerException`, 'IllegalArgumentException' |

### Method: `getNeighboringHexes(Location location)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `Location` | Pointer | `null`, non-null |
| Output | a List of neighboring Hex's or an exception | Cases | List<Hex>, `NullPointerException`, 'IllegalArgumentException' |

## Step 4: Test Cases

### Method under test: `Intersection(Location location, Building building)`
|             | State of the System | Expected output | Implemented? |
|-------------|---------------------|-----------------|--------------|
| Test Case 1 | `location` is null, `building' is a City | `NullPointerException` is thrown | :white_check_mark: |
| Test Case 2 | `location` is a valid `Location` object, `building' is a City | a valid `Intersection` object is created without exception | :white_check_mark: |
| Test Case 3 | `location` is a valid `Location` object, `building' is null | an 'NullPointerException' is thrown | :white_check_mark: |

### Method under test: `getBuilding(Location location)`
| Test Case 4 | `location` is `null` | `NullPointerException` is thrown | :x: |
| Test Case 5 | `location` is a valid `Location` object | Building is returned without exception | :x: |

### Method under test: `getNeighboringHexes(Location location)`
| Test Case 6 | `location` is `null` | `NullPointerException` is thrown | :x: |
| Test Case 7 | `location` is a valid `Location` object | a List<Hex> is returned without exception | :x: |