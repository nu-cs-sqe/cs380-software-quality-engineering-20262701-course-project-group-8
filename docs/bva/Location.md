# BVA Analysis: `Location`

## Design Under Test

```java
public class Location {
    public Location(int xPos, int yPos);
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

## Step 4: Test Cases

### Method under test: `Location(int xPos, int yPos)`
|             | State of the System | Expected output | Implemented? |
|-------------|---------------------|-----------------|--------------|
| Test Case 1 | xPos: -1, yPos: 1| `IllegalArgumentException` is thrown  | :x: |
| Test Case 2 | xPos: 1, yPos: -1 | `IllegalArgumentException` is thrown  | :x: |
| Test Case 3 | xPos: 0, yPos: 0 | `IllegalArgumentException` is thrown  | :x: |