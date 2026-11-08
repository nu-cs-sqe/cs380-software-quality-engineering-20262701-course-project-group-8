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
    public static final int MAX_ROADS = 15;
    public static final int MAX_SETTLEMENTS = 5;
    public static final int MAX_CITIES = 4;
    public static final int MIN_LONGEST_ROAD = 5;
    public static final int MIN_LARGEST_ARMY = 3;
    public static final int MAX_KNIGHTS = 14;
    public static final int MAX_VICTORY_POINT_CARDS = 5;
    public static final int WINNING_POINTS = 10;

    public boolean isValidPlayerCount(int playerCount)
    public int getDiscardCount(int handSize)
    public boolean canAfford(ResourceHand hand, BuildingType type)
    public void payCost(ResourceHand hand, Bank bank, BuildingType type)
    public boolean hasPieceAvailable(BuildingType type, int placedCount)
    public boolean canMoveRobber(Hex current, Hex target)
    public boolean shouldProduce(int bankSupply, int totalDemand, int receivingPlayers)
    public int getMaritimeTradeRatio(ResourceType give, boolean hasGenericHarbor, Set<ResourceType> specialHarbors)
    public boolean canMaritimeTrade(ResourceHand hand, Bank bank, ResourceType give, ResourceType receive, int ratio)
    public boolean canPlayDevelopmentCard(DevelopmentCard card, int currentTurn, boolean hasPlayedCardThisTurn)
    public boolean qualifiesForLongestRoad(int roadLength, int holderRoadLength)
    public boolean qualifiesForLargestArmy(int knightCount, int holderKnightCount)
    public int calculateVictoryPoints(int settlements, int cities, int victoryPointCards,
                                      boolean hasLongestRoad, boolean hasLargestArmy)
    public boolean isWinner(int victoryPoints, boolean isOwnTurn)
}
```
Rules are taken from the official CATAN base rules (2020), see `docs/requirements/game-rules.md`.
Any object or enum argument must not be `null` -> `NullPointerException`.
- `isValidPlayerCount()`: a game needs 3 to 4 players. Any other count returns `false`.
- `getDiscardCount()`: when a 7 is rolled, a player with more than 7 cards discards half, rounded down (p11). A player with 7 or fewer discards 0. `handSize` < 0 -> `IllegalArgumentException`.
- Building costs (p4-5):
  - `ROAD` = 1 `BRICK` + 1 `LUMBER`
  - `SETTLEMENT` = 1 `BRICK` + 1 `LUMBER` + 1 `WOOL` + 1 `GRAIN`
  - `CITY` = 3 `ORE` + 2 `GRAIN`
  - `DEVELOPMENT_CARD` = 1 `ORE` + 1 `WOOL` + 1 `GRAIN`
- `payCost()` moves the cost from the hand back to the bank. If the hand cannot afford it -> `IllegalStateException`, and neither the hand nor the bank changes.
- `hasPieceAvailable()`: each player has 15 roads, 5 settlements and 4 cities (p4). `placedCount` must be in [0, limit] -> otherwise `IllegalArgumentException`. `DEVELOPMENT_CARD` is not a piece -> `IllegalArgumentException`.
- `canMoveRobber()`: the robber must move to a different hex. The desert is a legal target (p11).
- `shouldProduce()`: if the bank cannot supply everyone's production of one resource, nobody gets that resource, unless only 1 player is affected; that player gets what is left (p10). `bankSupply` must be in [0, 19], `totalDemand` >= 1, `receivingPlayers` in [1, 4] -> otherwise `IllegalArgumentException`.
- `getMaritimeTradeRatio()`: 4:1 is always allowed, a generic harbor gives 3:1, a special harbor gives 2:1 only for its own resource (p9).
- `canMaritimeTrade()`: the player must hold `ratio` cards of `give`, `give` and `receive` must be different (p14), and the bank must still hold at least 1 `receive`. `ratio` must be in [2, 4] -> otherwise `IllegalArgumentException`.
- `canPlayDevelopmentCard()`: at most 1 development card per turn (p7). The card itself must also be playable (`DevelopmentCard.canBePlayed()`: not bought this turn, not played before, not a victory point card).
- `qualifiesForLongestRoad()`: needs at least 5 road segments and must be strictly longer than the current holder (p9). `holderRoadLength = 0` means nobody holds the card. Both values must be in [0, 15] -> otherwise `IllegalArgumentException`.
- `qualifiesForLargestArmy()`: needs at least 3 knights and must be strictly more than the current holder (p8). `holderKnightCount = 0` means nobody holds the card. Both values must be in [0, 14] -> otherwise `IllegalArgumentException`.
- `calculateVictoryPoints()`: settlement = 1, city = 2, victory point card = 1, Longest Road = 2, Largest Army = 2 (p14). `settlements` in [0, 5], `cities` in [0, 4], `victoryPointCards` in [0, 5] -> otherwise `IllegalArgumentException`.
- `isWinner()`: a player wins with 10 or more points, but only during their own turn (p7). `victoryPoints` < 0 -> `IllegalArgumentException`.

## Step 1-3: Analysis

### Method: `isValidPlayerCount(int playerCount)`
|        | Description   | Category        | Boundary Values |
| ------ | ------------- | --------------- | --------------- |
| Input  | `playerCount` | Interval [3, 4] | 2, 3, 4, 5      |
| Output |               | Boolean         | `true`, `false` |

### Method: `getDiscardCount(int handSize)`
|        | Description                | Category                    | Boundary Values                  |
| ------ | -------------------------- | --------------------------- | -------------------------------- |
| Input  | `handSize`                 | Interval [0, ∞)             | -1, 0                            |
| Input  | `handSize`                 | Interval [0, 7] / [8, ∞)    | 7, 8                             |
| Input  | `handSize`                 | Cases                       | even, odd (8, 9)                 |
| Output | number of cards to discard | Interval [0, ∞) / exception | 0, 4, `IllegalArgumentException` |

### Method: `canAfford(ResourceHand hand, BuildingType type)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `hand` | Pointer | `null`, non-null |
| Input | `type` | Pointer | `null`, non-null |
| Input | `type` | Cases | `ROAD`, `SETTLEMENT`, `CITY`, `DEVELOPMENT_CARD` |
| Input | count of each required resource | Interval [cost, ∞) | cost - 1, cost |
| Input | `hand` | Collection | empty, exactly the cost |
| Output | | Boolean / exception | `true`, `false`, `NullPointerException` |

### Method: `payCost(ResourceHand hand, Bank bank, BuildingType type)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `hand`, `bank`, `type` | Pointer | `null`, non-null |
| Input | `hand` | Cases | can afford, cannot afford |
| Input | `hand` | Cases | exactly the cost, more than the cost |
| Output | state change or exception | Cases | hand decreases by the cost and bank increases by the cost, `NullPointerException`, `IllegalStateException` |

### Method: `hasPieceAvailable(BuildingType type, int placedCount)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `type` | Pointer | `null`, non-null |
| Input | `type` | Cases | `ROAD`, `SETTLEMENT`, `CITY`, `DEVELOPMENT_CARD` |
| Input | `placedCount` (`ROAD`) | Interval [0, 15] | -1, 0, 14, 15, 16 |
| Input | `placedCount` (`SETTLEMENT`) | Interval [0, 5] | 4, 5, 6 |
| Input | `placedCount` (`CITY`) | Interval [0, 4] | 3, 4, 5 |
| Output | | Boolean / exception | `true`, `false`, `NullPointerException`, `IllegalArgumentException` |

### Method: `canMoveRobber(Hex current, Hex target)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `current`, `target` | Pointer | `null`, non-null |
| Input | `target` | Cases | same hex as `current`, a different hex, the desert |
| Output | | Boolean / exception | `true`, `false`, `NullPointerException` |

### Method: `shouldProduce(int bankSupply, int totalDemand, int receivingPlayers)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `bankSupply` | Interval [0, 19] | -1, 0, 19, 20 |
| Input | `totalDemand` | Interval [1, ∞) | 0, 1 |
| Input | `receivingPlayers` | Interval [1, 4] | 0, 1, 2, 4, 5 |
| Input | `bankSupply` compared with `totalDemand` | Interval | supply = demand, supply = demand - 1 |
| Output | | Boolean / exception | `true`, `false`, `IllegalArgumentException` |

### Method: `getMaritimeTradeRatio(ResourceType give, boolean hasGenericHarbor, Set<ResourceType> specialHarbors)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `give` | Pointer | `null`, non-null |
| Input | `specialHarbors` | Pointer | `null`, non-null |
| Input | `hasGenericHarbor` | Boolean | `true`, `false` |
| Input | `specialHarbors` | Collection | empty, contains `give`, contains only other types |
| Output | trade ratio | Cases | 4, 3, 2, `NullPointerException` |

### Method: `canMaritimeTrade(ResourceHand hand, Bank bank, ResourceType give, ResourceType receive, int ratio)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `hand`, `bank`, `give`, `receive` | Pointer | `null`, non-null |
| Input | `ratio` | Interval [2, 4] | 1, 2, 4, 5 |
| Input | count of `give` in `hand` | Interval [ratio, ∞) | ratio - 1, ratio |
| Input | `give` compared with `receive` | Cases | same type, different type |
| Input | count of `receive` in `bank` | Interval [1, 19] | 0, 1 |
| Output | | Boolean / exception | `true`, `false`, `NullPointerException`, `IllegalArgumentException` |

### Method: `canPlayDevelopmentCard(DevelopmentCard card, int currentTurn, boolean hasPlayedCardThisTurn)` (card bought on turn 3)
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `card` | Pointer | `null`, non-null |
| Input | `hasPlayedCardThisTurn` | Boolean | `true`, `false` |
| Input | `currentTurn` | Interval [3, ∞) | 2, 3, 4 |
| Input | card state | Cases | not played, already played |
| Input | card type | Cases | `KNIGHT`, `VICTORY_POINT` |
| Output | | Boolean / exception | `true`, `false`, `NullPointerException`, `IllegalArgumentException` |

### Method: `qualifiesForLongestRoad(int roadLength, int holderRoadLength)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `roadLength` | Interval [0, 15] | -1, 4, 5, 15, 16 |
| Input | `holderRoadLength` | Interval [0, 15] | -1, 0, 5 |
| Input | `roadLength` compared with `holderRoadLength` | Interval | equal, longer by 1 |
| Output | | Boolean / exception | `true`, `false`, `IllegalArgumentException` |

### Method: `qualifiesForLargestArmy(int knightCount, int holderKnightCount)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `knightCount` | Interval [0, 14] | -1, 2, 3, 14, 15 |
| Input | `holderKnightCount` | Interval [0, 14] | -1, 0, 3 |
| Input | `knightCount` compared with `holderKnightCount` | Interval | equal, more by 1 |
| Output | | Boolean / exception | `true`, `false`, `IllegalArgumentException` |

### Method: `calculateVictoryPoints(int settlements, int cities, int victoryPointCards, boolean hasLongestRoad, boolean hasLargestArmy)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `settlements` | Interval [0, 5] | -1, 0, 5, 6 |
| Input | `cities` | Interval [0, 4] | -1, 0, 4, 5 |
| Input | `victoryPointCards` | Interval [0, 5] | -1, 0, 5, 6 |
| Input | `hasLongestRoad` | Boolean | `true`, `false` |
| Input | `hasLargestArmy` | Boolean | `true`, `false` |
| Output | total points | Interval [0, 22] / exception | 0, 22, `IllegalArgumentException` |

### Method: `isWinner(int victoryPoints, boolean isOwnTurn)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `victoryPoints` | Interval [0, ∞) | -1 |
| Input | `victoryPoints` | Interval [10, ∞) | 9, 10, 11 |
| Input | `isOwnTurn` | Boolean | `true`, `false` |
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
| Test Case 5  | `getDiscardCount(-1)` | `IllegalArgumentException` | :x: |
| Test Case 6  | `getDiscardCount(0)` | `0` | :x: |
| Test Case 7  | `getDiscardCount(7)` | `0` | :x: |
| Test Case 8  | `getDiscardCount(8)` | `4` | :x: |
| Test Case 9  | `getDiscardCount(9)` | `4` (rounded down) | :x: |

### Method under test: `canAfford(ResourceHand hand, BuildingType type)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 10 | `canAfford(null, ROAD)` | `NullPointerException` | :x: |
| Test Case 11 | Empty hand, `canAfford(hand, null)` | `NullPointerException` | :x: |
| Test Case 12 | Hand: 1 `BRICK`, 1 `LUMBER`; `canAfford(hand, ROAD)` | `true` | :x: |
| Test Case 13 | Hand: 1 `BRICK`; `canAfford(hand, ROAD)` | `false` | :x: |
| Test Case 14 | Hand: 1 `BRICK`, 1 `LUMBER`, 1 `WOOL`, 1 `GRAIN`; `canAfford(hand, SETTLEMENT)` | `true` | :x: |
| Test Case 15 | Hand: 1 `BRICK`, 1 `LUMBER`, 1 `WOOL`; `canAfford(hand, SETTLEMENT)` | `false` | :x: |
| Test Case 16 | Hand: 3 `ORE`, 2 `GRAIN`; `canAfford(hand, CITY)` | `true` | :x: |
| Test Case 17 | Hand: 2 `ORE`, 2 `GRAIN`; `canAfford(hand, CITY)` | `false` | :x: |
| Test Case 18 | Hand: 3 `ORE`, 1 `GRAIN`; `canAfford(hand, CITY)` | `false` | :x: |
| Test Case 19 | Hand: 1 `ORE`, 1 `WOOL`, 1 `GRAIN`; `canAfford(hand, DEVELOPMENT_CARD)` | `true` | :x: |
| Test Case 20 | Hand: 1 `ORE`, 1 `GRAIN`; `canAfford(hand, DEVELOPMENT_CARD)` | `false` | :x: |
| Test Case 21 | Empty hand; `canAfford` called with each of the 4 types | `false` | :x: |

### Method under test: `payCost(ResourceHand hand, Bank bank, BuildingType type)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 22 | `payCost` called with `hand = null`, `bank = null`, or `type = null` (one at a time) | `NullPointerException` | :x: |
| Test Case 23 | Bank gave out 3 `ORE` and 2 `GRAIN` (holds 16 `ORE`, 17 `GRAIN`); hand: 3 `ORE`, 2 `GRAIN`; `payCost(hand, bank, CITY)` | Hand has 0 `ORE`, 0 `GRAIN`; bank has 19 `ORE`, 19 `GRAIN` | :x: |
| Test Case 24 | Bank gave out 2 `BRICK` and 2 `LUMBER`; hand: 2 `BRICK`, 2 `LUMBER`; `payCost(hand, bank, ROAD)` | Hand has 1 `BRICK`, 1 `LUMBER`; bank has 18 `BRICK`, 18 `LUMBER` | :x: |
| Test Case 25 | Bank gave out 1 `BRICK`, 1 `LUMBER`, 1 `WOOL`; hand: 1 `BRICK`, 1 `LUMBER`, 1 `WOOL`; `payCost(hand, bank, SETTLEMENT)` | `IllegalStateException`; hand and bank counts do not change | :x: |

### Method under test: `hasPieceAvailable(BuildingType type, int placedCount)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 26 | `hasPieceAvailable(null, 0)` | `NullPointerException` | :x: |
| Test Case 27 | `hasPieceAvailable(DEVELOPMENT_CARD, 0)` | `IllegalArgumentException` | :x: |
| Test Case 28 | `hasPieceAvailable(ROAD, -1)` | `IllegalArgumentException` | :x: |
| Test Case 29 | `hasPieceAvailable(ROAD, 0)` | `true` | :x: |
| Test Case 30 | `hasPieceAvailable(ROAD, 14)` | `true` | :x: |
| Test Case 31 | `hasPieceAvailable(ROAD, 15)` | `false` | :x: |
| Test Case 32 | `hasPieceAvailable(ROAD, 16)`, `hasPieceAvailable(SETTLEMENT, 6)`, `hasPieceAvailable(CITY, 5)` | `IllegalArgumentException` | :x: |
| Test Case 33 | `hasPieceAvailable(SETTLEMENT, 4)` | `true` | :x: |
| Test Case 34 | `hasPieceAvailable(SETTLEMENT, 5)` | `false` | :x: |
| Test Case 35 | `hasPieceAvailable(CITY, 3)` | `true` | :x: |
| Test Case 36 | `hasPieceAvailable(CITY, 4)` | `false` | :x: |

### Method under test: `canMoveRobber(Hex current, Hex target)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 37 | `canMoveRobber(null, hex)` and `canMoveRobber(hex, null)` | `NullPointerException` | :x: |
| Test Case 38 | Robber on a `FORESTS` hex; `canMoveRobber(forest, forest)` (same object) | `false` | :x: |
| Test Case 39 | Robber on a `FORESTS` hex; target is a different `HILLS` hex | `true` | :x: |
| Test Case 40 | Robber on a `FORESTS` hex; target is the `DESERTS` hex | `true` | :x: |

### Method under test: `shouldProduce(int bankSupply, int totalDemand, int receivingPlayers)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 41 | `shouldProduce(-1, 1, 1)` | `IllegalArgumentException` | :x: |
| Test Case 42 | `shouldProduce(20, 1, 1)` | `IllegalArgumentException` | :x: |
| Test Case 43 | `shouldProduce(19, 0, 1)` | `IllegalArgumentException` | :x: |
| Test Case 44 | `shouldProduce(19, 1, 0)` | `IllegalArgumentException` | :x: |
| Test Case 45 | `shouldProduce(19, 1, 5)` | `IllegalArgumentException` | :x: |
| Test Case 46 | `shouldProduce(19, 1, 1)` | `true` | :x: |
| Test Case 47 | `shouldProduce(4, 4, 2)` (supply = demand) | `true` | :x: |
| Test Case 48 | `shouldProduce(3, 4, 2)` (supply = demand - 1, 2 players) | `false` | :x: |
| Test Case 49 | `shouldProduce(3, 4, 1)` (supply = demand - 1, 1 player) | `true` | :x: |
| Test Case 50 | `shouldProduce(0, 4, 4)` | `false` | :x: |

### Method under test: `getMaritimeTradeRatio(ResourceType give, boolean hasGenericHarbor, Set<ResourceType> specialHarbors)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 51 | `getMaritimeTradeRatio(null, false, {})` | `NullPointerException` | :x: |
| Test Case 52 | `getMaritimeTradeRatio(ORE, false, null)` | `NullPointerException` | :x: |
| Test Case 53 | `getMaritimeTradeRatio(ORE, false, {})` | `4` | :x: |
| Test Case 54 | `getMaritimeTradeRatio(ORE, true, {})` | `3` | :x: |
| Test Case 55 | `getMaritimeTradeRatio(ORE, false, {ORE})` | `2` | :x: |
| Test Case 56 | `getMaritimeTradeRatio(ORE, false, {WOOL})` | `4` | :x: |
| Test Case 57 | `getMaritimeTradeRatio(ORE, true, {ORE})` | `2` | :x: |
| Test Case 58 | `getMaritimeTradeRatio(ORE, true, {WOOL})` | `3` | :x: |

### Method under test: `canMaritimeTrade(ResourceHand hand, Bank bank, ResourceType give, ResourceType receive, int ratio)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 59 | `hand`, `bank`, `give` or `receive` is `null` (one at a time) | `NullPointerException` | :x: |
| Test Case 60 | Hand: 4 `ORE`, new bank; `canMaritimeTrade(hand, bank, ORE, WOOL, 1)` | `IllegalArgumentException` | :x: |
| Test Case 61 | Hand: 4 `ORE`, new bank; `canMaritimeTrade(hand, bank, ORE, WOOL, 5)` | `IllegalArgumentException` | :x: |
| Test Case 62 | Hand: 4 `ORE`, new bank; `canMaritimeTrade(hand, bank, ORE, WOOL, 4)` | `true` | :x: |
| Test Case 63 | Hand: 3 `ORE`, new bank; `canMaritimeTrade(hand, bank, ORE, WOOL, 4)` | `false` | :x: |
| Test Case 64 | Hand: 2 `ORE`, new bank; `canMaritimeTrade(hand, bank, ORE, WOOL, 2)` | `true` | :x: |
| Test Case 65 | Hand: 4 `ORE`, new bank; `canMaritimeTrade(hand, bank, ORE, ORE, 4)` | `false` | :x: |
| Test Case 66 | Hand: 4 `ORE`, bank has 0 `WOOL`; `canMaritimeTrade(hand, bank, ORE, WOOL, 4)` | `false` | :x: |
| Test Case 67 | Hand: 4 `ORE`, bank has 1 `WOOL`; `canMaritimeTrade(hand, bank, ORE, WOOL, 4)` | `true` | :x: |

### Method under test: `canPlayDevelopmentCard(DevelopmentCard card, int currentTurn, boolean hasPlayedCardThisTurn)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 68 | `canPlayDevelopmentCard(null, 4, false)` | `NullPointerException` | :x: |
| Test Case 69 | `KNIGHT` bought on turn 3; `canPlayDevelopmentCard(card, 2, false)` | `IllegalArgumentException` | :x: |
| Test Case 70 | `KNIGHT` bought on turn 3; `canPlayDevelopmentCard(card, 3, false)` | `false` | :x: |
| Test Case 71 | `KNIGHT` bought on turn 3; `canPlayDevelopmentCard(card, 4, false)` | `true` | :x: |
| Test Case 72 | `KNIGHT` bought on turn 3; `canPlayDevelopmentCard(card, 4, true)` | `false` | :x: |
| Test Case 73 | `KNIGHT` bought on turn 3 and already played on turn 4; `canPlayDevelopmentCard(card, 5, false)` | `false` | :x: |
| Test Case 74 | `VICTORY_POINT` bought on turn 3; `canPlayDevelopmentCard(card, 4, false)` | `false` | :x: |

### Method under test: `qualifiesForLongestRoad(int roadLength, int holderRoadLength)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 75 | `qualifiesForLongestRoad(-1, 0)` | `IllegalArgumentException` | :x: |
| Test Case 76 | `qualifiesForLongestRoad(16, 0)` | `IllegalArgumentException` | :x: |
| Test Case 77 | `qualifiesForLongestRoad(5, -1)` | `IllegalArgumentException` | :x: |
| Test Case 78 | `qualifiesForLongestRoad(4, 0)` | `false` | :x: |
| Test Case 79 | `qualifiesForLongestRoad(5, 0)` | `true` | :x: |
| Test Case 80 | `qualifiesForLongestRoad(15, 0)` | `true` | :x: |
| Test Case 81 | `qualifiesForLongestRoad(5, 5)` (tie with holder) | `false` | :x: |
| Test Case 82 | `qualifiesForLongestRoad(6, 5)` | `true` | :x: |

### Method under test: `qualifiesForLargestArmy(int knightCount, int holderKnightCount)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 83 | `qualifiesForLargestArmy(-1, 0)` | `IllegalArgumentException` | :x: |
| Test Case 84 | `qualifiesForLargestArmy(15, 0)` | `IllegalArgumentException` | :x: |
| Test Case 85 | `qualifiesForLargestArmy(3, -1)` | `IllegalArgumentException` | :x: |
| Test Case 86 | `qualifiesForLargestArmy(2, 0)` | `false` | :x: |
| Test Case 87 | `qualifiesForLargestArmy(3, 0)` | `true` | :x: |
| Test Case 88 | `qualifiesForLargestArmy(14, 0)` | `true` | :x: |
| Test Case 89 | `qualifiesForLargestArmy(3, 3)` (tie with holder) | `false` | :x: |
| Test Case 90 | `qualifiesForLargestArmy(4, 3)` | `true` | :x: |

### Method under test: `calculateVictoryPoints(int settlements, int cities, int victoryPointCards, boolean hasLongestRoad, boolean hasLargestArmy)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 91 | `calculateVictoryPoints(0, 0, 0, false, false)` | `0` | :x: |
| Test Case 92 | `calculateVictoryPoints(2, 0, 0, false, false)` | `2` | :x: |
| Test Case 93 | `calculateVictoryPoints(0, 1, 0, false, false)` | `2` | :x: |
| Test Case 94 | `calculateVictoryPoints(0, 0, 1, false, false)` | `1` | :x: |
| Test Case 95 | `calculateVictoryPoints(0, 0, 0, true, false)` | `2` | :x: |
| Test Case 96 | `calculateVictoryPoints(0, 0, 0, false, true)` | `2` | :x: |
| Test Case 97 | `calculateVictoryPoints(5, 4, 5, true, true)` | `22` | :x: |
| Test Case 98 | `settlements` is `-1` or `6` | `IllegalArgumentException` | :x: |
| Test Case 99 | `cities` is `-1` or `5` | `IllegalArgumentException` | :x: |
| Test Case 100 | `victoryPointCards` is `-1` or `6` | `IllegalArgumentException` | :x: |

### Method under test: `isWinner(int victoryPoints, boolean isOwnTurn)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 101 | `isWinner(-1, true)` | `IllegalArgumentException` | :x: |
| Test Case 102 | `isWinner(9, true)` | `false` | :x: |
| Test Case 103 | `isWinner(10, true)` | `true` | :x: |
| Test Case 104 | `isWinner(11, true)` | `true` | :x: |
| Test Case 105 | `isWinner(10, false)` | `false` | :x: |