# BVA Analysis: `Dice`

## Design Under Test

```java
public class Dice {
    public Dice(Random random)
    public int roll()
    private int rollOneDie()
}
```

- `Dice(Random random)` rejects `null` with `Objects.requireNonNull(random)`, which throws `NullPointerException`.
- `roll()` rolls two dice by calling `random.nextInt(6)` twice and returns the sum. Each die value is `nextInt(6) + 1`, so each die is in [1, 6] and the sum is in [2, 12].
- `rollOneDie()` is private, so it has no separate BVA; covered through `roll()`.
- In tests, `Random` is replaced by an EasyMock mock. Each test records the values `nextInt(6)` should return, in order, and ends with `verify()`, which checks that `nextInt(6)` was called exactly as expected (no extra calls, no other bound).

## Step 1-3: Analysis

### Method: `Dice(Random random)`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `random` | Pointer | `null`, non-null |
| Output | a new `Dice` object, or an exception | Cases | object created, `NullPointerException` |

### Method: `roll()`
| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | value returned by `random.nextInt(6)` for each die | Interval [0, 5] | 0, 1, 4, 5 |
| Output | sum of the two dice | Interval [2, 12] | 2, 3, 11, 12 |

## Step 4: Test Cases

### Method under test: `Dice(Random random)`
|             | State of the System | Expected output | Implemented? |
|-------------|---------------------|-----------------|--------------|
| Test Case 1 | `random` is `null` | `NullPointerException` is thrown | :white_check_mark: |
| Test Case 2 | `random` is a valid `Random` object | A `Dice` object is created without exception | :white_check_mark: |

### Method under test: `roll()`
|             | State of the System | Expected output | Implemented? |
|-------------|---------------------|-----------------|--------------|
| Test Case 3 | Mock returns 0, 0 (both dice at minimum) | Returns 2 | :x: |
| Test Case 4 | Mock returns 5, 5 (both dice at maximum) | Returns 12 | :x: |
| Test Case 5 | Mock returns 0, 5 (one die at minimum, one at maximum) | Returns 7 | :x: |
| Test Case 6 | Mock returns 0, 1 (one die at minimum, one at min+1) | Returns 3 | :x: |
| Test Case 7 | Mock returns 4, 5 (one die at max-1, one at maximum) | Returns 11 | :x: |
| Test Case 8 | Mock expects `nextInt(6)` exactly twice (returns 0, 0); `verify()` is called after `roll()` | `nextInt` is called exactly 2 times per roll, each with bound 6 | :x: |
| Test Case 9 | Mock returns 0, 0, then 5, 5; `roll()` is called twice | First call returns 2, second call returns 12 (each roll uses new random values) | :x: |