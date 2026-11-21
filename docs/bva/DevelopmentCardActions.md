# BVA Analysis: `DevelopmentCardActions`

## Design Under Test

```java
public class DevelopmentCardActions {
    public DevelopmentCardActions(GameRules rules, Bank bank)
    public DevelopmentCard buyDevelopmentCard(ResourceHand hand, int currentTurn)
    public void playYearOfPlenty(DevelopmentCard card, int currentTurn, ResourceHand hand,
                                 ResourceType first, ResourceType second)
    public int playMonopoly(DevelopmentCard card, int currentTurn, ResourceHand hand,
                            List<ResourceHand> otherHands, ResourceType type)
}
```

- `null` arguments -> `NullPointerException`.
- Buying costs 1 ORE, 1 GRAIN, 1 WOOL (from `GameRules`). The payment goes back to the Bank and the top card is drawn with `turnPurchased = currentTurn`.
- `currentTurn` < 0 when buying -> `IllegalArgumentException`.
- Buying fails without changing anything if the hand cannot afford it or the deck is empty -> `IllegalStateException`.
- Playing a card of the wrong type -> `IllegalArgumentException`.
- Playing a card that cannot be played this turn -> `IllegalStateException`.
- A failed play changes nothing: no resources move and the card is not marked as played.
- Year of Plenty: take any 2 resources from the Bank (they may be the same type).
- Monopoly: take every card of `type` from each other hand and return the total taken. `otherHands` must not contain `hand` -> `IllegalArgumentException`.

## Step 1-3: Analysis

### Method: `DevelopmentCardActions(GameRules rules, Bank bank)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `rules` | Pointer | `null`, non-null |
| Input | `bank` | Pointer | `null`, non-null |
| Output | new object, or exception | Cases | created, `NullPointerException` |

### Method: `buyDevelopmentCard(ResourceHand hand, int currentTurn)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `hand` | Pointer | `null`, non-null |
| Input | `currentTurn` | Interval [0, ∞) | -1, 0 |
| Input | hand resources | Cases | one short of the cost, exactly the cost, more than the cost |
| Input | deck size | Interval [0, 25] | 0, 1 |
| Output | | Cases | card returned, `NullPointerException`, `IllegalArgumentException`, `IllegalStateException` |

### Method: `playYearOfPlenty(DevelopmentCard card, int currentTurn, ResourceHand hand, ResourceType first, ResourceType second)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `card`, `hand`, `first`, `second` | Pointer | `null`, non-null |
| Input | `card` type | Cases | `YEAR_OF_PLENTY`, other |
| Input | card playable | Boolean | playable, not playable (same turn as purchase) |
| Input | `first`, `second` | Cases | same type, different types |
| Input | Bank count of the requested type | Interval [needed, 19] | needed - 1, needed |
| Output | | Cases | hand +2, `NullPointerException`, `IllegalArgumentException`, `IllegalStateException` |

### Method: `playMonopoly(DevelopmentCard card, int currentTurn, ResourceHand hand, List<ResourceHand> otherHands, ResourceType type)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `card`, `hand`, `otherHands`, `type` | Pointer | `null`, non-null |
| Input | `card` type | Cases | `MONOPOLY`, other |
| Input | card playable | Boolean | playable, not playable (same turn as purchase) |
| Input | `otherHands` | Collection | empty, 1 hand, many hands, contains `hand` |
| Input | other hands' count of `type` | Interval [0, ∞) | 0, 1, many |
| Output | total taken | Interval [0, ∞) / exception | 0, 1, many, `NullPointerException`, `IllegalArgumentException`, `IllegalStateException` |

## Step 4: Test Cases

### Method under test: `DevelopmentCardActions(GameRules rules, Bank bank)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 1  | `rules = null` or `bank = null` | `NullPointerException` | :white_check_mark: |

### Method under test: `buyDevelopmentCard(ResourceHand hand, int currentTurn)` (the Bank first gives the hand its resources, so the Bank never holds more than 19 of a type)
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 2  | `hand = null`, `currentTurn = 0` | `NullPointerException` | :white_check_mark: |
| Test Case 3  | Hand has 1 ORE, 1 GRAIN, 1 WOOL, `currentTurn = -1` | `IllegalArgumentException`, hand and deck unchanged | :white_check_mark: |
| Test Case 4  | Hand has 1 ORE, 1 GRAIN, 0 WOOL, `currentTurn = 0` | `IllegalStateException`, hand unchanged, deck still 25 | :white_check_mark: |
| Test Case 5  | Hand has 1 ORE, 1 GRAIN, 1 WOOL, `currentTurn = 0` | Returns a card with `turnPurchased = 0`; hand empty; Bank back to 19 of each; deck 24 | :white_check_mark: |
| Test Case 6  | Hand has 2 ORE, 2 GRAIN, 2 WOOL, `currentTurn = 0` | Hand left with 1 ORE, 1 GRAIN, 1 WOOL | :white_check_mark: |
| Test Case 7  | Deck has 1 card, hand has 1 ORE, 1 GRAIN, 1 WOOL | Returns a card, deck 0 | :white_check_mark: |
| Test Case 8  | Deck empty, hand has 1 ORE, 1 GRAIN, 1 WOOL | `IllegalStateException`, hand unchanged | :white_check_mark: |

### Method under test: `playYearOfPlenty(DevelopmentCard card, int currentTurn, ResourceHand hand, ResourceType first, ResourceType second)` (card bought on turn 3, `currentTurn = 4`, full Bank, empty hand unless stated)
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 9  | Any one of `card`, `hand`, `first`, `second` is `null` | `NullPointerException` | :white_check_mark: |
| Test Case 10 | `card` is `MONOPOLY` | `IllegalArgumentException`, nothing changes | :white_check_mark: |
| Test Case 11 | `YEAR_OF_PLENTY`, `currentTurn = 3` | `IllegalStateException`, nothing changes, card not played | :white_check_mark: |
| Test Case 12 | `YEAR_OF_PLENTY`, `BRICK` + `ORE` | Hand 1 BRICK, 1 ORE; Bank 18 BRICK, 18 ORE; card played | :white_check_mark: |
| Test Case 13 | `YEAR_OF_PLENTY`, `BRICK` + `BRICK` | Hand 2 BRICK; Bank 17 BRICK; card played | :white_check_mark: |
| Test Case 14 | `YEAR_OF_PLENTY`, Bank has exactly 2 BRICK, `BRICK` + `BRICK` | Hand 2 BRICK; Bank 0 BRICK; card played | :white_check_mark: |
| Test Case 15 | `YEAR_OF_PLENTY`, Bank has 1 BRICK, `BRICK` + `BRICK` | `IllegalStateException`, nothing changes, card not played | :x: |
| Test Case 16 | `YEAR_OF_PLENTY`, Bank has 0 ORE, `BRICK` + `ORE` | `IllegalStateException`, no BRICK given either, card not played | :x: |

### Method under test: `playMonopoly(DevelopmentCard card, int currentTurn, ResourceHand hand, List<ResourceHand> otherHands, ResourceType type)` (card bought on turn 3, `currentTurn = 4`, `type = WOOL`, empty hand unless stated)
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 17 | Any one of `card`, `hand`, `otherHands`, `type` is `null` | `NullPointerException` | :x: |
| Test Case 18 | `card` is `YEAR_OF_PLENTY` | `IllegalArgumentException`, nothing moves | :x: |
| Test Case 19 | `MONOPOLY`, `currentTurn = 3` | `IllegalStateException`, nothing moves, card not played | :x: |
| Test Case 20 | `MONOPOLY`, `otherHands` contains `hand` | `IllegalArgumentException`, nothing moves, card not played | :x: |
| Test Case 21 | `MONOPOLY`, `otherHands` empty | Returns 0, card played | :x: |
| Test Case 22 | `MONOPOLY`, 1 other hand with 0 WOOL | Returns 0, hand unchanged, card played | :x: |
| Test Case 23 | `MONOPOLY`, 1 other hand with 1 WOOL | Returns 1; other hand 0 WOOL; hand 1 WOOL; card played | :x: |
| Test Case 24 | `MONOPOLY`, hand has 1 WOOL, 3 other hands with 0, 2, 5 WOOL and 1 BRICK each | Returns 7; other hands 0 WOOL and still 1 BRICK; hand 8 WOOL; card played | :x: |
