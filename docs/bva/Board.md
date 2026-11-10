# BVA Analysis: `Board`

## Design Under Test

```java
public class Board {
    public Board()
    public Board(Random random)
    public List<Hex> getHexes()
    public Hex getHex(int index)
    public int getNumberOfHexes()
    public Hex getRobberHex()
    public void moveRobber(Hex targetHex)
    public List<Hex> getHexesProducingResource(int diceRoll)
}
```

- A board must contain exactly 19 hexes.
- The board must contain 4 forests, 3 hills, 3 mountains, 4 fields, 4 pastures, and 1 desert.
- The desert must have a number disc of 0, and all other hexes must have valid number discs between 2 and 12, excluding 7.
- The robber initially occupies the desert hex.
- `Random` must not be `null` when passed to the constructor → `NullPointerException`.
- `getHexes()` returns an unmodifiable list of hexes.
- `getHex(index)` returns the hex at the specified index. Invalid indices cause `IndexOutOfBoundsException`.
- `getNumberOfHexes()` returns the total number of hexes.
- `getRobberHex()` returns the hex currently occupied by the robber. If no hex has the robber, it throws `IllegalStateException`.
- `moveRobber(targetHex)` rejects `null`, hexes that do not belong to the board, and the current robber hex with `IllegalArgumentException`.
- `moveRobber()` removes the robber from its previous hex and places it on the target hex.
- `getHexesProducingResource(diceRoll)` returns all hexes whose number discs match the dice roll, excluding desert hexes and hexes occupied by the robber.

## Step 1-3: Analysis

### Method: `Board()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | No parameters | Cases | Default constructor |
| Output | New board | Integer | Exactly 19 hexes |

### Method: `Board(Random random)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `random` | Pointer | `null`, non-null |
| Output | New board or exception | Cases | Board created, `NullPointerException` |

### Method: `initializeBoard()` (private)

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | Terrain distribution | Integer | 1 desert, 3 hills, 3 mountains, 4 forests, 4 fields, 4 pastures |
| Input | Number-disc distribution | Integer | 18 valid number discs |
| Output | Initialized board | Cases | 19 hexes, correct terrain distribution, valid number discs |

### Method: `getHexes()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | Board object | Cases | Initialized board |
| Output | List of hexes | Integer | 19 elements |
| Output | List modification | Cases | Read allowed, modification rejected with `UnsupportedOperationException` |

### Method: `getHex(int index)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `index` | Interval [0, 18] | -1, 0, 9, 18, 19 |
| Output | Hex or exception | Cases | Correct hex returned, `IndexOutOfBoundsException` |

### Method: `getNumberOfHexes()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | Board object | Cases | Initialized board |
| Output | Number of hexes | Integer | 19 |

### Method: `getRobberHex()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | Robber location | Cases | Robber on desert, robber on non-desert |
| Input | Number of hexes containing robber | Integer | 0, 1 |
| Output | Hex or exception | Cases | Current robber hex, `IllegalStateException` |

### Method: `moveRobber(Hex targetHex)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `targetHex` | Pointer | `null`, board hex, external hex |
| Input | Target location | Cases | Current robber hex, different hex |
| Input | Current robber location | Cases | Desert, non-desert |
| Output | State change or exception | Cases | Robber moved, `IllegalArgumentException` |

### Method: `getHexesProducingResource(int diceRoll)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `diceRoll` | Interval [2, 12], excluding 7 | 1, 2, 6, 7, 8, 12, 13 |
| Input | Hex number disc | Integer | Matching, nonmatching |
| Input | Terrain type | Enum | `DESERTS`, non-desert |
| Input | Robber status | Boolean | `true`, `false` |
| Output | List of producing hexes | Integer | 0, 1, multiple matching hexes |

## Step 4: Test Cases

### Method under test: `Board()`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 1 | Default constructor called | Board created with exactly 19 hexes |:white_check_mark:|

### Method under test: `Board(Random random)`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 2 | `random = null` | `NullPointerException` |:white_check_mark:|
| Test Case 3 | Valid `Random` object supplied | Board created with exactly 19 hexes |:white_check_mark:|

### Method under test: `initializeBoard()` (private)

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 4 | Board initialized | Exactly 4 forests, 3 hills, 3 mountains, 4 fields, 4 pastures, and 1 desert |:white_check_mark:|
| Test Case 5 | Board initialized | Exactly 18 non-desert number discs and 1 desert number disc of 0 |:white_check_mark:|
| Test Case 6 | Board initialized | Every non-desert number disc is valid; no number disc is 7 |:white_check_mark:|
| Test Case 7 | Board initialized | Exactly one hex initially contains the robber, and it is the desert |:white_check_mark:|

### Method under test: `getHexes()`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 8 | `getHexes()` called on initialized board | Returns a list containing 19 hexes |:white_check_mark:|
| Test Case 9 | Attempt to add a hex to the returned list | `UnsupportedOperationException` |:white_check_mark:|
| Test Case 10 | Attempt to remove a hex from the returned list | `UnsupportedOperationException` |:white_check_mark:|

### Method under test: `getHex(int index)`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 11 | `index = -1` | `IndexOutOfBoundsException` |:white_check_mark:|
| Test Case 12 | `index = 0` | Returns first hex |:white_check_mark:|
| Test Case 13 | `index = 9` | Returns tenth hex |:white_check_mark:|
| Test Case 14 | `index = 18` | Returns nineteenth hex |:white_check_mark:|
| Test Case 15 | `index = 19` | `IndexOutOfBoundsException` |:white_check_mark:|

### Method under test: `getNumberOfHexes()`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 16 | Board initialized | Returns `19` |:white_check_mark:|

### Method under test: `getRobberHex()`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 17 | Newly initialized board | Returns the desert hex |  |
| Test Case 18 | Robber moved to a non-desert hex | Returns the new robber hex |  |
| Test Case 19 | No hex currently contains the robber | `IllegalStateException` |  |

### Method under test: `moveRobber(Hex targetHex)`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 20 | `targetHex = null` | `IllegalArgumentException` |  |
| Test Case 21 | Target hex belongs to another board | `IllegalArgumentException` |  |
| Test Case 22 | Target hex is the current robber hex | `IllegalArgumentException` |  |
| Test Case 23 | Target hex is a different hex on the same board | Robber removed from old hex and placed on target hex |  |
| Test Case 24 | Robber currently occupies a non-desert hex and is moved to another hex | Old hex has no robber; target hex has robber |  |

### Method under test: `getHexesProducingResource(int diceRoll)`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 25 | `diceRoll = 2`, one non-desert hex has number disc 2 and no robber | Returns matching hex |  |
| Test Case 26 | `diceRoll = 8`, multiple non-desert hexes have number disc 8 and no robber | Returns all matching hexes |  |
| Test Case 27 | Matching hex has the robber | Does not include that hex |  |
| Test Case 28 | Desert hex is checked | Does not include desert hex |  |
| Test Case 29 | `diceRoll = 6` | Returns matching hex |  |
| Test Case 30 | `diceRoll = 7` | Returns empty list |  |
| Test Case 31 | `diceRoll = 1` | Returns empty list |  |
| Test Case 32 | `diceRoll = 13` | Returns empty list |  |
| Test Case 33 | Matching number discs exist, but all matching hexes have the robber | Returns empty list |  |