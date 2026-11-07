# BVA Analysis: `DevelopmentCard`

## Design Under Test

```java
public class DevelopmentCard {
    public DevelopmentCard(DevelopmentCardType type, int turnPurchased)
    public DevelopmentCardType getType()
    public boolean isVictoryPoint()
    public boolean isPlayed()
    public boolean canBePlayed(int currentTurn)
    public void play(int currentTurn)
}
```

- `type` must not be `null` -> `NullPointerException`
- `turnPurchased` must be >= 0 -> `IllegalArgumentException`.
- A card cannot be played on the turn it was purchased, can be played only once, and Victory Point cards are never played.
- `currentTurn` < `turnPurchased` is invalid -> `IllegalArgumentException`.
- `play()` on a card that cannot be played -> `IllegalStateException`.

## Step 1-3: Analysis

### Method: `DevelopmentCard(DevelopmentCardType type, int turnPurchased)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `type` | Pointer | `null`, non-null |
| Input | `turnPurchased` | Interval [0, ∞) | -1, 0, 1 |
| Output | new object, or exception | Cases | created, `NullPointerException`, `IllegalArgumentException` |

### Method: `getType()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | type given in constructor | Cases | `KNIGHT`, `VICTORY_POINT`, `ROAD_BUILDING`, `YEAR_OF_PLENTY`, `MONOPOLY` |

### Method: `isVictoryPoint()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | card type | Cases | `VICTORY_POINT`, other types |
| Output | | Boolean | `true`, `false` |

### Method: `isPlayed()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | whether `play()` succeeded before | Boolean | not played, played |
| Output | | Boolean | `false`, `true` |

### Method: `canBePlayed(int currentTurn)` (card purchased on turn 3)
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `currentTurn` | Interval [3, ∞) | 2, 3, 4 |
| Input | played state | Boolean | not played, played |
| Input | card type | Cases | `VICTORY_POINT`, other |
| Output | | Boolean / exception | `true`, `false`, `IllegalArgumentException` |

### Method: `play(int currentTurn)` (card purchased on turn 3)
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `currentTurn` | Interval [3, ∞) | 2, 3, 4 |
| Input | played state | Boolean | not played, played |
| Input | card type | Cases | `VICTORY_POINT`, other |
| Output | state change or exception | Cases | `isPlayed()` becomes `true`, `IllegalStateException`, `IllegalArgumentException` |

## Step 4: Test Cases

### Method under test: `DevelopmentCard(DevelopmentCardType type, int turnPurchased)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 1  | `type = null`, `turnPurchased = 0` | `NullPointerException` | :white_check_mark: |
| Test Case 2  | `type = KNIGHT`, `turnPurchased = -1` | `IllegalArgumentException` | :white_check_mark: |
| Test Case 3  | `type = KNIGHT`, `turnPurchased = 0` | Object created | :white_check_mark: |
| Test Case 4  | `type = KNIGHT`, `turnPurchased = 1` | Object created | :white_check_mark: |

### Method under test: `getType()`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 5  | Card created with each of the 5 types | Returns the same type | :white_check_mark: |

### Method under test: `isVictoryPoint()`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 6  | `type = VICTORY_POINT` | `true` | :white_check_mark: |
| Test Case 7  | `type` is each of the other 4 types | `false` | :white_check_mark: |

### Method under test: `isPlayed()`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 8  | New `KNIGHT` card, never played | `false` | :white_check_mark: |
| Test Case 9  | `KNIGHT` bought turn 3, `play(4)` called | `true` | :white_check_mark: |

### Method under test: `canBePlayed(int currentTurn)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 10 | `KNIGHT` bought turn 3, `currentTurn = 2` | `IllegalArgumentException` | :white_check_mark: |
| Test Case 11 | `KNIGHT` bought turn 3, `currentTurn = 3` | `false` | :white_check_mark: |
| Test Case 12 | `KNIGHT` bought turn 3, `currentTurn = 4` | `true` | :white_check_mark: |
| Test Case 13 | `KNIGHT` bought turn 3, already played on turn 4, `currentTurn = 5` | `false` | :white_check_mark: |
| Test Case 14 | `VICTORY_POINT` bought turn 3, `currentTurn = 4` | `false` | :white_check_mark: |

### Method under test: `play(int currentTurn)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 15 | `KNIGHT` bought turn 3, `play(2)` | `IllegalArgumentException` | :white_check_mark: |
| Test Case 16 | `KNIGHT` bought turn 3, `play(3)` | `IllegalStateException`, `isPlayed()` stays `false` | :white_check_mark: |
| Test Case 17 | `KNIGHT` bought turn 3, `play(4)` | No exception, `isPlayed()` is `true` | implemented in Test Case 9 |
| Test Case 18 | `KNIGHT` bought turn 3, played on turn 4, `play(5)` | `IllegalStateException` | :white_check_mark: |
| Test Case 19 | `VICTORY_POINT` bought turn 3, `play(4)` | `IllegalStateException` | :x: |

