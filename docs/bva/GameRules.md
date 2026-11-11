# BVA Analysis: `GameRules`

## Design Under Test

```java
public enum BuildingType {
    ROAD,
    SETTLEMENT,
    CITY,
    DEVELOPMENT_CARD
}
```

```java
public class GameRules {
    public static final int MIN_PLAYERS = 3;
    public static final int MAX_PLAYERS = 4;
    public static final int DISCARD_THRESHOLD = 7;
    public static final int MAX_DEVELOPMENT_CARDS_PER_TURN = 1;
    public static final int MIN_LONGEST_ROAD = 5;
    public static final int MIN_LARGEST_ARMY = 3;
    public static final int WINNING_POINTS = 10;

    public boolean isValidPlayerCount(int playerCount)
    public int getDiscardCount(int handSize)
    public boolean canAfford(ResourceHand hand, BuildingType type)
    public void payCost(ResourceHand hand, Bank bank, BuildingType type)
    public boolean canMoveRobberTo(Hex target)
    public boolean canPlayDevelopmentCard(DevelopmentCard card, int currentTurn, int cardsPlayedThisTurn)
    public boolean qualifiesForLongestRoad(int roadLength, int holderRoadLength)
    public boolean qualifiesForLargestArmy(int knightCount, int holderKnightCount)
    public boolean isWinner(int victoryPoints)
}
```

Rules are taken from the official CATAN base rules (2020), see `docs/requirements/game-rules.md`.

- Any object or enum argument must not be `null` -> `NullPointerException`.
- `isValidPlayerCount()`: a game needs 3 to 4 players. Any other count returns `false`.
- `getDiscardCount()`: when a 7 is rolled, a player with more than 7 cards discards half, rounded down (p11). A player with 7 or fewer discards 0. `handSize` < 0 -> `IllegalArgumentException`.
- Building costs (p4-5):
  - `ROAD` = 1 `BRICK` + 1 `LUMBER`
  - `SETTLEMENT` = 1 `BRICK` + 1 `LUMBER` + 1 `WOOL` + 1 `GRAIN`
  - `CITY` = 3 `ORE` + 2 `GRAIN`
  - `DEVELOPMENT_CARD` = 1 `ORE` + 1 `WOOL` + 1 `GRAIN`
- `canAfford()`: `true` when the hand holds at least the cost of `type`.
- `payCost()` moves the cost of `type` from the hand back to the bank. If the hand cannot afford it -> `IllegalStateException`, and neither the hand nor the bank changes.
- `canMoveRobberTo()`: the robber must move to a different hex, so the target must not already hold the robber. The desert is a legal target (p11).
- `canPlayDevelopmentCard()`: at most 1 development card per turn (p7), and the card itself must be playable (`DevelopmentCard.canBePlayed()`: not bought this turn, not played before, not a victory point card). `cardsPlayedThisTurn` < 0 -> `IllegalArgumentException`.
- `qualifiesForLongestRoad()`: needs at least 5 road segments and must be strictly longer than the current holder; a tie does not take the card (p9). `holderRoadLength = 0` means nobody holds the card. A negative value -> `IllegalArgumentException`.
- `qualifiesForLargestArmy()`: needs at least 3 knights and must be strictly more than the current holder; a tie does not take the card (p8). `holderKnightCount = 0` means nobody holds the card. A negative value -> `IllegalArgumentException`.
- `isWinner()`: a player wins with 10 or more points (p7). A player can only win on their own turn, so `Game` calls this only for the current player. `victoryPoints` < 0 -> `IllegalArgumentException`.

## Step 1-3: Analysis

### Method: `isValidPlayerCount(int playerCount)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `playerCount` | Interval [3, 4] | 2, 3, 4, 5 |
| Output | | Boolean | `true`, `false` |

### Method: `getDiscardCount(int handSize)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `handSize` | Interval [0, ∞) | -1, 0 |
| Input | `handSize` | Interval [0, 7] / [8, ∞) | 7, 8 |
| Input | `handSize` | Cases | even, odd (8, 9) |
| Output | number of cards to discard | Interval [0, ∞) / exception | 0, 4, `IllegalArgumentException` |

### Method: `canAfford(ResourceHand hand, BuildingType type)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `hand` | Pointer | `null`, non-null |
| Input | `type` | Pointer | `null`, non-null |
| Input | `type` | Cases | `ROAD`, `SETTLEMENT`, `CITY`, `DEVELOPMENT_CARD` |
| Input | count of each required resource | Interval [cost, ∞) | cost - 1, cost |
| Output | | Boolean / exception | `true`, `false`, `NullPointerException` |

### Method: `payCost(ResourceHand hand, Bank bank, BuildingType type)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `hand`, `bank`, `type` | Pointer | `null`, non-null |
| Input | `hand` | Cases | cannot afford, exactly the cost, more than the cost |
| Output | state change or exception | Cases | hand decreases by the cost and bank increases by the cost, `NullPointerException`, `IllegalStateException` |

### Method: `canMoveRobberTo(Hex target)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `target` | Pointer | `null`, non-null |
| Input | `target` holds the robber | Boolean | `true`, `false` |
| Input | `target` terrain | Cases | desert, other terrain |
| Output | | Boolean / exception | `true`, `false`, `NullPointerException` |

### Method: `canPlayDevelopmentCard(DevelopmentCard card, int currentTurn, int cardsPlayedThisTurn)` (`KNIGHT` bought on turn 3)
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `card` | Pointer | `null`, non-null |
| Input | `cardsPlayedThisTurn` | Interval [0, 0] | -1, 0, 1 |
| Input | `card.canBePlayed(currentTurn)` | Boolean | `true` (turn 4), `false` (turn 3) |
| Output | | Boolean / exception | `true`, `false`, `NullPointerException`, `IllegalArgumentException` |

### Method: `qualifiesForLongestRoad(int roadLength, int holderRoadLength)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `roadLength` | Interval [0, ∞) | -1 |
| Input | `roadLength` | Interval [5, ∞) | 4, 5 |
| Input | `holderRoadLength` | Interval [0, ∞) | -1, 0, 5 |
| Input | `roadLength` compared with `holderRoadLength` | Interval | equal, longer by 1 |
| Output | | Boolean / exception | `true`, `false`, `IllegalArgumentException` |

### Method: `qualifiesForLargestArmy(int knightCount, int holderKnightCount)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `knightCount` | Interval [0, ∞) | -1 |
| Input | `knightCount` | Interval [3, ∞) | 2, 3 |
| Input | `holderKnightCount` | Interval [0, ∞) | -1, 0, 3 |
| Input | `knightCount` compared with `holderKnightCount` | Interval | equal, more by 1 |
| Output | | Boolean / exception | `true`, `false`, `IllegalArgumentException` |

### Method: `isWinner(int victoryPoints)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `victoryPoints` | Interval [0, ∞) | -1 |
| Input | `victoryPoints` | Interval [10, ∞) | 9, 10 |
| Output | | Boolean / exception | `true`, `false`, `IllegalArgumentException` |

## Step 4: Test Cases

### Method under test: `isValidPlayerCount(int playerCount)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 1  | `isValidPlayerCount(2)` | `false` | :white_check_mark: |
| Test Case 2  | `isValidPlayerCount(3)` | `true` | :white_check_mark: |
| Test Case 3  | `isValidPlayerCount(4)` | `true` | :white_check_mark: |
| Test Case 4  | `isValidPlayerCount(5)` | `false` | :white_check_mark: |

### Method under test: `getDiscardCount(int handSize)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 5  | `getDiscardCount(-1)` | `IllegalArgumentException` | :white_check_mark: |
| Test Case 6  | `getDiscardCount(0)` | `0` | :white_check_mark: |
| Test Case 7  | `getDiscardCount(7)` | `0` | :white_check_mark: |
| Test Case 8  | `getDiscardCount(8)` | `4` | :white_check_mark: |
| Test Case 9  | `getDiscardCount(9)` | `4` (rounded down) | :white_check_mark: |

### Method under test: `canAfford(ResourceHand hand, BuildingType type)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 10 | `canAfford(null, ROAD)` | `NullPointerException` | :white_check_mark: |
| Test Case 11 | Empty hand, `canAfford(hand, null)` | `NullPointerException` | :white_check_mark: |
| Test Case 12 | Hand: 1 `BRICK`, 1 `LUMBER`; `canAfford(hand, ROAD)` | `true` | :white_check_mark: |
| Test Case 13 | Hand: 1 `BRICK`; `canAfford(hand, ROAD)` | `false` | :white_check_mark: |
| Test Case 14 | Hand: 1 `BRICK`, 1 `LUMBER`, 1 `WOOL`, 1 `GRAIN`; `canAfford(hand, SETTLEMENT)` | `true` | :white_check_mark: |
| Test Case 15 | Hand: 1 `BRICK`, 1 `LUMBER`, 1 `WOOL`; `canAfford(hand, SETTLEMENT)` | `false` | :white_check_mark: |
| Test Case 16 | Hand: 3 `ORE`, 2 `GRAIN`; `canAfford(hand, CITY)` | `true` | :white_check_mark: |
| Test Case 17 | Hand: 2 `ORE`, 2 `GRAIN`; `canAfford(hand, CITY)` | `false` | :white_check_mark: |
| Test Case 18 | Hand: 3 `ORE`, 1 `GRAIN`; `canAfford(hand, CITY)` | `false` | :white_check_mark: |
| Test Case 19 | Hand: 1 `ORE`, 1 `WOOL`, 1 `GRAIN`; `canAfford(hand, DEVELOPMENT_CARD)` | `true` | :white_check_mark: |
| Test Case 20 | Hand: 1 `ORE`, 1 `GRAIN`; `canAfford(hand, DEVELOPMENT_CARD)` | `false` | :white_check_mark: |

### Method under test: `payCost(ResourceHand hand, Bank bank, BuildingType type)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 21 | `payCost` called with `hand = null`, `bank = null`, or `type = null` (one at a time) | `NullPointerException` | :white_check_mark: |
| Test Case 22 | Bank gave out 3 `ORE` and 2 `GRAIN` (holds 16 `ORE`, 17 `GRAIN`); hand: 3 `ORE`, 2 `GRAIN`; `payCost(hand, bank, CITY)` | Hand has 0 `ORE`, 0 `GRAIN`; bank has 19 `ORE`, 19 `GRAIN` | :white_check_mark: |
| Test Case 23 | Bank gave out 2 `BRICK` and 2 `LUMBER`; hand: 2 `BRICK`, 2 `LUMBER`; `payCost(hand, bank, ROAD)` | Hand has 1 `BRICK`, 1 `LUMBER`; bank has 18 `BRICK`, 18 `LUMBER` | :white_check_mark: |
| Test Case 24 | Bank gave out 1 `BRICK`, 1 `LUMBER`, 1 `WOOL`; hand: 1 `BRICK`, 1 `LUMBER`, 1 `WOOL`; `payCost(hand, bank, SETTLEMENT)` | `IllegalStateException`; hand and bank counts do not change | :white_check_mark: |

### Method under test: `canMoveRobberTo(Hex target)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 25 | `canMoveRobberTo(null)` | `NullPointerException` | :white_check_mark: |
| Test Case 26 | Target is a `FORESTS` hex that holds the robber | `false` | :white_check_mark: |
| Test Case 27 | Target is a `HILLS` hex without the robber | `true` | :white_check_mark: |
| Test Case 28 | Target is the `DESERTS` hex after the robber left it | `true` | :white_check_mark: |

### Method under test: `canPlayDevelopmentCard(DevelopmentCard card, int currentTurn, int cardsPlayedThisTurn)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 29 | `canPlayDevelopmentCard(null, 4, 0)` | `NullPointerException` | :white_check_mark: |
| Test Case 30 | `KNIGHT` bought on turn 3; `canPlayDevelopmentCard(card, 4, -1)` | `IllegalArgumentException` | :white_check_mark: |
| Test Case 31 | `KNIGHT` bought on turn 3; `canPlayDevelopmentCard(card, 4, 0)` | `true` | :white_check_mark: |
| Test Case 32 | `KNIGHT` bought on turn 3; `canPlayDevelopmentCard(card, 4, 1)` | `false` | :white_check_mark: |
| Test Case 33 | `KNIGHT` bought on turn 3; `canPlayDevelopmentCard(card, 3, 0)` (card bought this turn) | `false` | :white_check_mark: |

### Method under test: `qualifiesForLongestRoad(int roadLength, int holderRoadLength)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 34 | `qualifiesForLongestRoad(-1, 0)` | `IllegalArgumentException` | :x: |
| Test Case 35 | `qualifiesForLongestRoad(5, -1)` | `IllegalArgumentException` | :x: |
| Test Case 36 | `qualifiesForLongestRoad(4, 0)` | `false` | :x: |
| Test Case 37 | `qualifiesForLongestRoad(5, 0)` | `true` | :x: |
| Test Case 38 | `qualifiesForLongestRoad(5, 5)` (tie with holder) | `false` | :x: |
| Test Case 39 | `qualifiesForLongestRoad(6, 5)` | `true` | :x: |

### Method under test: `qualifiesForLargestArmy(int knightCount, int holderKnightCount)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 40 | `qualifiesForLargestArmy(-1, 0)` | `IllegalArgumentException` | :x: |
| Test Case 41 | `qualifiesForLargestArmy(3, -1)` | `IllegalArgumentException` | :x: |
| Test Case 42 | `qualifiesForLargestArmy(2, 0)` | `false` | :x: |
| Test Case 43 | `qualifiesForLargestArmy(3, 0)` | `true` | :x: |
| Test Case 44 | `qualifiesForLargestArmy(3, 3)` (tie with holder) | `false` | :x: |
| Test Case 45 | `qualifiesForLargestArmy(4, 3)` | `true` | :x: |

### Method under test: `isWinner(int victoryPoints)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 46 | `isWinner(-1)` | `IllegalArgumentException` | :x: |
| Test Case 47 | `isWinner(9)` | `false` | :x: |
| Test Case 48 | `isWinner(10)` | `true` | :x: |
