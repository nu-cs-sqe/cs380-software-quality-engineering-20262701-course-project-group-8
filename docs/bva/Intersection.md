# BVA Analysis: `Intersections`

## Design Under Test

```java
public class Intersections {
    public Intersections();
    public void addBuilding(Location location, Building building);
    public Hex addHex(Location location, Hex hex);
    public Building getBuilding(Location location);
    public List<Hex> getNeighboringHexes(Location location);
    public Hex getHex(Location location);

}
```
- Intersections() constructor for the class
'addBuilding(Location location, Building building) rejects 'null' location with 'NullPointerException'. The location must be a valid location on the game board, otherwise rejects invalid location with 'IllegalArgumentException'. Rejects 'null' building with 'NullPointerException'. The building must be either a Settlement or City on the game board, otherwise rejects invalid location with 'IllegalArgumentException'.
- 'getBuilding(Location location) rejects 'null' location with 'NullPointerException'. The location must be a valid location on the game board, otherwise rejects invalid location with 'IllegalArgumentException'. Returns the Building, which is a Settlement or City.
- 'getNeighboringHexes(Location location)' rejects 'null' location with 'NullPointerException'. The location must be a valid location on the game board, otherwise rejects invalid location with 'IllegalArgumentException'. Returns a List of neighboring hexes adjacent to the location or the empty list if no Hexes are adjacent to the location.
- 'addHex(Location location, Hex hex)' rejects 'null' location with 'NullPointerException'. Rejects 'null' Hex with 'NullPointerException'. Maps the corresponding Hex to the Location.
- 'getHex(Location location)' rejects 'null' location with 'NullPointerException'. Returns the Hex at the corresponding location, or 'null' if no Hex is found.

## Step 1-3: Analysis

### Method: `addBuilding(Location location, Building building)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `Location` | Pointer | `null`, valid non-null, invalid non-null |
| Input | `Building` | Pointer | `null`, valid non-null, invalid non-null |
| Output | a new entry into Location-Building mapping or an exception | Cases | Building added, `NullPointerException` |

### Method: `addHex(Location location, Hex hex)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `Location` | Pointer | `null`, valid non-null, invalid non-null |
| Input | `Hex` | Pointer | `null`, valid non-null, invalid non-null |
| Output | added entry into Location-Hex mapping or an exception | Cases | Hex added, `NullPointerException`|

### Method: `getBuilding(Location location)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `Location` | Pointer | `null`, non-null |
| Output | the Building at this intersection or an exception | Cases | Building object, `NullPointerException`|

### Method: `getHex(Location location)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `Location` | Pointer | `null`, non-null |
| Output | the Hex at this intersection or an exception | Cases | Hex object, `NullPointerException`|

### Method: `getNeighboringHexes(Location location)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `Location` | Pointer | `null`, non-null |
| Output | a List of neighboring Hex's or an exception | Cases | List<Hex>, `NullPointerException` |

## Step 4: Test Cases

### Method under test: `addBuilding(Location location, Building building)`
|             | State of the System | Expected output | Implemented? |
|-------------|---------------------|-----------------|--------------|
| Test Case 1 | `location` is null, `building' is a City | `NullPointerException` is thrown | :white_check_mark: |
| Test Case 2 | `location` is a valid `Location` object, `building' is a City | a valid `Intersection` object is created without exception | :white_check_mark: |
| Test Case 3 | `location` is a valid `Location` object, `building' is null | an 'NullPointerException' is thrown | :white_check_mark: |

### Method under test: `getBuilding(Location location)`
| Test Case 4 | `location` is `null` | `NullPointerException` is thrown | :white_check_mark: |
| Test Case 5 | `location` is a valid `Location` object | Building at location is returned without exception | :white_check_mark: |

### Method under test: `getNeighboringHexes(Location location)`
| Test Case 6 | `location` is `null` | `NullPointerException` is thrown | :white_check_mark: |
| Test Case 7 | `location` is a valid `Location` object | a List<Hex> is returned without exception | :x: |

### Method under test: ` addHex(Location location, Hex hex);`
| Test Case 8 | `location` is `null`, Hex is a non-null value | `NullPointerException` is thrown | :white_check_mark: |
| Test Case 9 | `location` is a valid `Location` object, Hex is a valid Hex object| the Hex at the location is added without exception | :white_check_mark: |
| Test Case 10 | `location` is a valid 'Location' object, Hex is a null value | `NullPointerException` is thrown | :white_check_mark: |

### Method under test: ` getHex(Location location);`
| Test Case 11 | `location` is `null | `NullPointerException` is thrown | :white_check_mark: |
| Test Case 12 | `location` is a valid `Location` object | the Hex at the location is returned without exception | :x: |