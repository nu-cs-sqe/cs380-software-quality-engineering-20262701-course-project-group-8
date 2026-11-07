# BVA Analysis: `Bank`

## Design Under Test

```java
public class Bank {
    public static final int RESOURCE_SUPPLY = 19;
    public static final int DEVELOPMENT_CARD_SUPPLY = 25;

    public Bank()
    public Bank(Random random)
    public int getResourceCount(ResourceType type)
    public boolean hasResource(ResourceType type, int amount)
    public void giveResource(ResourceType type, int amount)
    public void receiveResource(ResourceType type, int amount)
    public int getDevelopmentCardCount()
    public DevelopmentCard drawDevelopmentCard(int currentTurn)
}
```

- A new `Bank` holds 19 cards of each `ResourceType` and a shuffled deck of 25 development cards:
  14 `KNIGHT`, 5 `VICTORY_POINT`, 2 `ROAD_BUILDING`, 2 `YEAR_OF_PLENTY`, 2 `MONOPOLY`.
- `Bank(Random random)` uses `random` to shuffle the deck so tests can control the order. `random` must not be `null` -> `NullPointerException`
- `type` must not be `null` in any method -> `NullPointerException`
- `amount` must be >= 1 in `hasResource`, `giveResource` and `receiveResource` -> `IllegalArgumentException`
- `giveResource()` moves cards out of the bank. If the bank holds fewer than `amount` -> `IllegalStateException`, and the count does not change.
- `receiveResource()` moves cards back into the bank. The bank can never hold more than 19 of a type -> `IllegalStateException`, and the count does not change.
- `drawDevelopmentCard()` removes the top card of the deck and returns it with `turnPurchased = currentTurn`.
  An empty deck -> `IllegalStateException`. `currentTurn` < 0 -> `IllegalArgumentException`, and the deck does not change.

## Step 1-3: Analysis

### Method: `Bank()`, `Bank(Random random)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `random` | Pointer | `null`, non-null |
| Output | new bank | Cases | 19 of every resource type, 25 development cards, `NullPointerException` |

### Method: `getResourceCount(ResourceType type)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `type` | Pointer | `null`, non-null |
| Input | `type` | Cases | `BRICK`, `LUMBER`, `ORE`, `GRAIN`, `WOOL` |
| Output | count of that type | Interval [0, 19] / exception | 19, `NullPointerException` |

### Method: `hasResource(ResourceType type, int amount)` (new bank, 19 `BRICK`)
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `type` | Pointer | `null`, non-null |
| Input | `amount` | Interval [1, 19] | 0, 19, 20 |
| Output | | Boolean / exception | `true`, `false`, `NullPointerException`, `IllegalArgumentException` |

### Method: `giveResource(ResourceType type, int amount)` (new bank, 19 `BRICK`)
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `type` | Pointer | `null`, non-null |
| Input | `amount` | Interval [1, 19] | 0, 1, 19, 20 |
| Output | state change or exception | Cases | count decreases by `amount`, `NullPointerException`, `IllegalArgumentException`, `IllegalStateException` |

### Method: `receiveResource(ResourceType type, int amount)` (bank has given out 3 `BRICK`, holds 16)
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `type` | Pointer | `null`, non-null |
| Input | `amount` | Interval [1, 3] | 0, 1, 3, 4 |
| Output | state change or exception | Cases | count increases by `amount`, `NullPointerException`, `IllegalArgumentException`, `IllegalStateException` |

### Method: `getDevelopmentCardCount()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | cards in the deck | Collection | full (25), empty (0) |
| Output | number of cards left | Interval [0, 25] | 25, 24, 0 |

### Method: `drawDevelopmentCard(int currentTurn)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `currentTurn` | Interval [0, ∞) | -1, 0 |
| Input | cards in the deck | Collection | full (25), one card left, empty (0) |
| Output | drawn card or exception | Cases | card with `turnPurchased = currentTurn`, all 25 draws match the deck mix, `IllegalArgumentException`, `IllegalStateException` |

## Step 4: Test Cases

### Method under test: `Bank()`, `Bank(Random random)`, `getResourceCount(ResourceType type)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 1  | New bank, `getResourceCount` called with each of the 5 types | `19` | :white_check_mark: |
| Test Case 2  | New bank, `getResourceCount(null)` | `NullPointerException` | :white_check_mark: |
| Test Case 3  | `new Bank(null)` | `NullPointerException` | :white_check_mark: |

### Method under test: `hasResource(ResourceType type, int amount)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 4  | New bank, `hasResource(null, 1)` | `NullPointerException` | :x: |
| Test Case 5  | New bank, `hasResource(BRICK, 0)` | `IllegalArgumentException` | :x: |
| Test Case 6  | New bank, `hasResource(BRICK, 19)` | `true` | :x: |
| Test Case 7  | New bank, `hasResource(BRICK, 20)` | `false` | :x: |

### Method under test: `giveResource(ResourceType type, int amount)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 8  | New bank, `giveResource(null, 1)` | `NullPointerException` | :x: |
| Test Case 9  | New bank, `giveResource(BRICK, 0)` | `IllegalArgumentException`, `getResourceCount(BRICK)` stays `19` | :x: |
| Test Case 10 | New bank, `giveResource(BRICK, 1)` | `getResourceCount(BRICK)` is `18` | :x: |
| Test Case 11 | New bank, `giveResource(BRICK, 19)` | `getResourceCount(BRICK)` is `0` | :x: |
| Test Case 12 | New bank, `giveResource(BRICK, 20)` | `IllegalStateException`, `getResourceCount(BRICK)` stays `19` | :x: |
| Test Case 13 | New bank, `giveResource(BRICK, 2)` | `getResourceCount` of the other 4 types is still `19` | :x: |

### Method under test: `receiveResource(ResourceType type, int amount)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 14 | Bank holds 16 `BRICK`, `receiveResource(null, 1)` | `NullPointerException` | :x: |
| Test Case 15 | Bank holds 16 `BRICK`, `receiveResource(BRICK, 0)` | `IllegalArgumentException`, `getResourceCount(BRICK)` stays `16` | :x: |
| Test Case 16 | Bank holds 16 `BRICK`, `receiveResource(BRICK, 1)` | `getResourceCount(BRICK)` is `17` | :x: |
| Test Case 17 | Bank holds 16 `BRICK`, `receiveResource(BRICK, 3)` | `getResourceCount(BRICK)` is `19` | :x: |
| Test Case 18 | Bank holds 16 `BRICK`, `receiveResource(BRICK, 4)` | `IllegalStateException`, `getResourceCount(BRICK)` stays `16` | :x: |

### Method under test: `getDevelopmentCardCount()`, `drawDevelopmentCard(int currentTurn)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 19 | New bank, `getDevelopmentCardCount()` | `25` | :x: |
| Test Case 20 | New bank, `drawDevelopmentCard(0)` | returned card has `turnPurchased` `0` (`canBePlayed(0)` is `false`), `getDevelopmentCardCount()` is `24` | :x: |
| Test Case 21 | New bank, `drawDevelopmentCard(-1)` | `IllegalArgumentException`, `getDevelopmentCardCount()` stays `25` | :x: |
| Test Case 22 | New bank, draw all 25 cards | 14 `KNIGHT`, 5 `VICTORY_POINT`, 2 `ROAD_BUILDING`, 2 `YEAR_OF_PLENTY`, 2 `MONOPOLY`; `getDevelopmentCardCount()` is `0` | :x: |
| Test Case 23 | Bank with empty deck, `drawDevelopmentCard(0)` | `IllegalStateException` | :x: |
| Test Case 24 | Two banks built with `new Random(42)`, draw all 25 cards from each | Both banks return the same order of types | :x: |
