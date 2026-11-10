# BVA Analysis: `Location`

## Design Under Test

```java
public class Location {
    public Location(int xPos, int yPos);
    public int getXPos();
    public int getYPos();
}
```
- 'Location(int xPos, int yPos)' The location must be a valid location on the game board, otherwise rejects invalid location with 'IllegalArgumentException'.

### Current Project Assumptions
- The game board is a 2-Dimensional Array where the position (0, 0) always exists
- Negative indices in this 2-Dimensional Array are invalid

## Step 1-3: Analysis

### Method: `Location(int xPos, int yPos)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | xPos | Number | [0, ∞) |
| Input | yPos | Number | [0, ∞) |
| Output | an 'IllegalArgumentException' if the position inputs do not specify a valid location on the game board| Cases | creates a Location object, 'IllegalArgumentException' |

### Method: `getXPos()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | integer value of the x-coordinate of the Location object | Number | [0, ∞) |

### Method: `getYPos()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | integer value of the y-coordinate of the Location object | Number | [0, ∞) |

## Step 4: Test Cases

### Method under test: `Location(int xPos, int yPos)`
|             | State of the System | Expected output | Implemented? |
|-------------|---------------------|-----------------|--------------|
| Test Case 1 | xPos: -1, yPos: 1| `IllegalArgumentException` is thrown  | :white_check_mark: |
| Test Case 2 | xPos: 1, yPos: -1 | `IllegalArgumentException` is thrown  | :white_check_mark: |
| Test Case 3 | xPos: 0, yPos: 0 | `IllegalArgumentException` is thrown  | :white_check_mark: |

### Method under test: `getXPos()`
| Test Case 4 | xPos: 1, yPos: 0 | 1 | :white_check_mark: |

### Method under test: `getYPos()`
| Test Case 5 | xPos: 0, yPos: 0 | 0 | :white_check_mark: |
