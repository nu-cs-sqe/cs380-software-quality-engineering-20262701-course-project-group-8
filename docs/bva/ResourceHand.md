# BVA Analysis: `ResourceHand`

## Design Under Test

```java
public class ResourceHand {
    public ResourceHand()
    public int getCount(ResourceType type)
    public int getTotal()
    public void add(ResourceType type, int amount)
    public void remove(ResourceType type, int amount)
    public boolean has(ResourceType type, int amount)
    public ResourceType removeRandom(Random random)
}
```

- A `ResourceHand` tracks how many cards of each `ResourceType` a player or the bank holds. A new hand has 0 of every type.
- `type` must not be `null` in any method -> `NullPointerException`
- `amount` must be >= 1 in `add`, `remove` and `has` -> `IllegalArgumentException`
- `remove()` with `amount` greater than the current count -> `IllegalStateException`, and the count does not change.
- Each type is counted separately: changing one type does not change the others.
- `removeRandom()` removes one card picked at random and returns its type. Every card has the same chance: the cards are lined up in `ResourceType` order (`BRICK`, `LUMBER`, `ORE`, `GRAIN`, `WOOL`) and `random.nextInt(getTotal())` picks the index. An empty hand -> `IllegalStateException`. Used for stealing with the robber.

## Step 1-3: Analysis

### Method: `ResourceHand()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Output | new hand | Cases | every type has count 0 |

### Method: `getCount(ResourceType type)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `type` | Pointer | `null`, non-null |
| Input | `type` | Cases | `BRICK`, `LUMBER`, `ORE`, `GRAIN`, `WOOL` |
| Output | count of that type | Interval [0, ∞) / exception | 0, `NullPointerException` |

### Method: `getTotal()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | cards in the hand | Collection | empty, cards of more than one type |
| Output | sum of all counts | Interval [0, ∞) | 0, sum > 0 |

### Method: `add(ResourceType type, int amount)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `type` | Pointer | `null`, non-null |
| Input | `amount` | Interval [1, ∞) | 0, 1 |
| Input | current count of `type` | Interval [0, ∞) | 0, > 0 |
| Output | state change or exception | Cases | count increases by `amount`, `NullPointerException`, `IllegalArgumentException` |

### Method: `remove(ResourceType type, int amount)` (hand holds 3 `BRICK`)
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `type` | Pointer | `null`, non-null |
| Input | `amount` | Interval [1, 3] | 0, 2, 3, 4 |
| Output | state change or exception | Cases | count decreases by `amount`, `NullPointerException`, `IllegalArgumentException`, `IllegalStateException` |

### Method: `has(ResourceType type, int amount)` (hand holds 3 `BRICK`)
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `type` | Pointer | `null`, non-null |
| Input | `amount` | Interval [1, ∞) | 0, 3, 4 |
| Output | | Boolean / exception | `true`, `false`, `NullPointerException`, `IllegalArgumentException` |

### Method: `removeRandom(Random random)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | total cards in the hand | Interval [1, ∞) | 0, 1, many |
| Input | types in the hand | Cases | one type, several types with an empty type between them |
| Input | index from `random.nextInt(total)` | Interval [0, total - 1] | 0, last card of a type, first card of the next type, total - 1 |
| Output | removed type | Cases / exception | the type at that index, `IllegalStateException` |

## Step 4: Test Cases

### Method under test: `ResourceHand()`, `getCount(ResourceType type)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 1  | New hand, `getCount` called with each of the 5 types | `0` | :white_check_mark: |
| Test Case 2  | New hand, `getCount(null)` | `NullPointerException` | :white_check_mark: |

### Method under test: `getTotal()`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 3  | New hand | `0` | :white_check_mark: |
| Test Case 4  | Hand holds 2 `BRICK` and 3 `ORE` | `5` | :white_check_mark: |

### Method under test: `add(ResourceType type, int amount)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 5  | New hand, `add(null, 1)` | `NullPointerException` | :white_check_mark: |
| Test Case 6  | New hand, `add(BRICK, 0)` | `IllegalArgumentException`, `getCount(BRICK)` stays `0` | :white_check_mark: |
| Test Case 7  | New hand, `add(BRICK, 1)` | `getCount(BRICK)` is `1` | :white_check_mark: |
| Test Case 8  | Hand holds 2 `BRICK`, `add(BRICK, 3)` | `getCount(BRICK)` is `5` | :white_check_mark: |
| Test Case 9  | New hand, `add(BRICK, 2)` | `getCount` of the other 4 types is still `0` | :white_check_mark: |

### Method under test: `remove(ResourceType type, int amount)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 10 | Hand holds 3 `BRICK`, `remove(null, 1)` | `NullPointerException` | :white_check_mark: |
| Test Case 11 | Hand holds 3 `BRICK`, `remove(BRICK, 0)` | `IllegalArgumentException`, `getCount(BRICK)` stays `3` | :white_check_mark: |
| Test Case 12 | Hand holds 3 `BRICK`, `remove(BRICK, 1)` | `getCount(BRICK)` is `2` | :white_check_mark: |
| Test Case 13 | Hand holds 3 `BRICK`, `remove(BRICK, 3)` | `getCount(BRICK)` is `0` | :white_check_mark: |
| Test Case 14 | Hand holds 3 `BRICK`, `remove(BRICK, 4)` | `IllegalStateException`, `getCount(BRICK)` stays `3` | :white_check_mark: |

### Method under test: `has(ResourceType type, int amount)`
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 15 | Hand holds 3 `BRICK`, `has(null, 1)` | `NullPointerException` | :white_check_mark: |
| Test Case 16 | Hand holds 3 `BRICK`, `has(BRICK, 0)` | `IllegalArgumentException` | :white_check_mark: |
| Test Case 17 | Hand holds 3 `BRICK`, `has(BRICK, 3)` | `true` | :white_check_mark: |
| Test Case 18 | Hand holds 3 `BRICK`, `has(BRICK, 4)` | `false` | :white_check_mark: |

### Method under test: `removeRandom(Random random)` (`random` is a mock; "index k" means `random.nextInt(total)` returns k)
|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 19 | New hand | `IllegalStateException`, `getTotal()` stays `0` | :white_check_mark: |
| Test Case 20 | Hand holds 1 `BRICK`, index 0 | Returns `BRICK`, `getTotal()` is `0` | :white_check_mark: |
| Test Case 21 | Hand holds 2 `BRICK` and 3 `ORE`, index 0 | Returns `BRICK`; 1 `BRICK`, 3 `ORE` left | :x: |
| Test Case 22 | Hand holds 2 `BRICK` and 3 `ORE`, index 1 (last `BRICK`) | Returns `BRICK`; 1 `BRICK`, 3 `ORE` left | :x: |
| Test Case 23 | Hand holds 2 `BRICK` and 3 `ORE`, index 2 (first `ORE`, skipping the empty `LUMBER`) | Returns `ORE`; 2 `BRICK`, 2 `ORE` left | :x: |
| Test Case 24 | Hand holds 2 `BRICK` and 3 `ORE`, index 4 (last card) | Returns `ORE`; 2 `BRICK`, 2 `ORE` left | :x: |
