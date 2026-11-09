# BVA Analysis: `Game`

## Design Under Test

```java
public class Game {
    public Game(List<Player> players)
    public void startGame()
    public GamePhase getPhase()
    public Player getCurrentPlayer()
    public void advanceTurn()
    public void endGame(Player winner)
    public Player getWinner()
}
```

- `Game(List<Player> players)` initializes a new game with the supplied players. The player list must not be `null` and must contain a valid number of players. For this analysis, assume a game requires 2–4 players. Invalid player counts or a `null` list throw `IllegalArgumentException`.
- `startGame()` starts the game. It can only be called when the game has not started. It transitions the game into the setup phase and initializes the first player's turn.
- `getPhase()` returns the current game phase. The possible phases are `NOT_STARTED`, `SETUP`, `PLAYING`, and `FINISHED`.
- `getCurrentPlayer()` returns the player whose turn it currently is. Before the game starts, there is no current player.
- `advanceTurn()` advances the current turn to the next player. Turns proceed in player order and wrap around from the last player to the first player. It can only be called while the game is in the `PLAYING` phase.
- `endGame(Player winner)` ends the game and records the winning player. The winner must belong to the game. The game must already have started and must not already be finished.
- `getWinner()` returns the winning player after the game ends. Before the game ends, it returns `null`.
- `GamePhase` is an enum representing the lifecycle of the game: `NOT_STARTED`, `SETUP`, `PLAYING`, and `FINISHED`.
- Invalid lifecycle operations throw `IllegalStateException`. Invalid player arguments throw `IllegalArgumentException`.

## Step 1-3: Analysis

### Method: `Game(List<Player> players)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `players` | Pointer | `null`, non-null |
| Input | number of players | Interval [2, 4] | 1, 2, 3, 4, 5 |
| Output | a new `Game` object, or an exception | Cases | object created, `IllegalArgumentException` |

### Method: `startGame()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | current game phase | Enum | `NOT_STARTED`, `SETUP`, `PLAYING`, `FINISHED` |
| Output | updated game phase, or an exception | Cases | transitions to `SETUP`, `IllegalStateException` |
| Output | current player | Pointer | first player initialized, no player initialized |

### Method: `getPhase()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | current game state | Enum | `NOT_STARTED`, `SETUP`, `PLAYING`, `FINISHED` |
| Output | current game phase | Enum | `NOT_STARTED`, `SETUP`, `PLAYING`, `FINISHED` |

### Method: `getCurrentPlayer()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | current game phase | Enum | `NOT_STARTED`, `SETUP`, `PLAYING`, `FINISHED` |
| Input | current player index | Interval [0, numberOfPlayers - 1] | 0, 1, middle index, last index |
| Output | current player | Pointer | valid player, `null` |

### Method: `advanceTurn()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | current game phase | Enum | `NOT_STARTED`, `SETUP`, `PLAYING`, `FINISHED` |
| Input | current player index | Interval [0, numberOfPlayers - 1] | 0, 1, middle index, last index |
| Input | number of players | Interval [2, 4] | 2, 3, 4 |
| Output | current player | Pointer | next player, first player after wraparound |
| Output | updated game state, or an exception | Cases | turn advances, `IllegalStateException` |

### Method: `endGame(Player winner)`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | `winner` | Pointer | `null`, valid player, external player |
| Input | current game phase | Enum | `NOT_STARTED`, `SETUP`, `PLAYING`, `FINISHED` |
| Output | updated game phase, or an exception | Cases | transitions to `FINISHED`, `IllegalArgumentException`, `IllegalStateException` |
| Output | winner | Pointer | valid winner recorded |

### Method: `getWinner()`

| | Description | Category | Boundary Values |
|---|---|---|---|
| Input | current game phase | Enum | `NOT_STARTED`, `SETUP`, `PLAYING`, `FINISHED` |
| Output | winner | Pointer | `null`, valid winning player |

## Step 4: Test Cases

### Method under test: `Game(List<Player> players)`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 1 | `players` is `null` | `IllegalArgumentException` is thrown | |
| Test Case 2 | Player list contains 1 player | `IllegalArgumentException` is thrown | |
| Test Case 3 | Player list contains 2 players | A `Game` object is created successfully | |
| Test Case 4 | Player list contains 3 players | A `Game` object is created successfully | |
| Test Case 5 | Player list contains 4 players | A `Game` object is created successfully | |
| Test Case 6 | Player list contains 5 players | `IllegalArgumentException` is thrown | |

### Method under test: `startGame()`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 7 | Game has been constructed but not started | Game phase changes to `SETUP` | |
| Test Case 8 | Game has been constructed with 2 players | First player is assigned as the current player | |
| Test Case 9 | Game has already entered `SETUP` | `IllegalStateException` is thrown | |
| Test Case 10 | Game is already in `PLAYING` | `IllegalStateException` is thrown | |
| Test Case 11 | Game is already `FINISHED` | `IllegalStateException` is thrown | |

### Method under test: `getPhase()`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 12 | Game has just been constructed | Returns `NOT_STARTED` | |
| Test Case 13 | `startGame()` has been called | Returns `SETUP` | |
| Test Case 14 | Setup has been completed and game has entered active play | Returns `PLAYING` | |
| Test Case 15 | `endGame()` has been called successfully | Returns `FINISHED` | |

### Method under test: `getCurrentPlayer()`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 16 | Game has not started | Returns `null` | |
| Test Case 17 | Game has started with 2 players | Returns the first player | |
| Test Case 18 | Game is in `PLAYING` and current player is the first player | Returns the first player | |
| Test Case 19 | Game is in `PLAYING` and current player is the last player | Returns the last player | |

### Method under test: `advanceTurn()`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 20 | Game has not started | `IllegalStateException` is thrown | |
| Test Case 21 | Game is in `SETUP` | `IllegalStateException` is thrown | |
| Test Case 22 | Game is in `PLAYING` with 2 players; first player is current | Current player changes to the second player | |
| Test Case 23 | Game is in `PLAYING` with 2 players; second player is current | Current player wraps around to the first player | |
| Test Case 24 | Game is in `PLAYING` with 3 players; first player is current | Current player changes to the second player | |
| Test Case 25 | Game is in `PLAYING` with 4 players; second player is current | Current player changes to the third player | |
| Test Case 26 | Game is in `PLAYING` with 4 players; last player is current | Current player wraps around to the first player | |
| Test Case 27 | Game is `FINISHED` | `IllegalStateException` is thrown | |

### Method under test: `endGame(Player winner)`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 28 | `winner` is `null` | `IllegalArgumentException` is thrown | |
| Test Case 29 | `winner` is a player belonging to the game, and game is in `PLAYING` | Game phase changes to `FINISHED` and winner is recorded | |
| Test Case 30 | `winner` is a player not belonging to the game | `IllegalArgumentException` is thrown | |
| Test Case 31 | Game has not started | `IllegalStateException` is thrown | |
| Test Case 32 | Game is in `SETUP` | `IllegalStateException` is thrown | |
| Test Case 33 | Game is already `FINISHED` | `IllegalStateException` is thrown | |

### Method under test: `getWinner()`

| | State of the System | Expected output | Implemented? |
|---|---|---|---|
| Test Case 34 | Game has not started | Returns `null` | |
| Test Case 35 | Game is in `PLAYING` | Returns `null` | |
| Test Case 36 | Game has ended with the first player as winner | Returns the first player | |
| Test Case 37 | Game has ended with the last player as winner | Returns the last player | |