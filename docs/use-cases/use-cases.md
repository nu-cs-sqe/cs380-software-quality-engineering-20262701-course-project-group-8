# User Story: Initialize a New Catan Game

As a player of Catan, I want the game board to be automatically initialized and players to be assigned their starting resources, settlements, and roads, so that everyone can begin playing without manually setting up the board.

Acceptance Criteria:
1. The game must not start unless there are 3 to 4 players.
2. The game board must be generated with the correct number and distribution of terrain hexes, including forests, hills, mountains, fields, pastures, and desert.
3. Each terrain hex must be assigned the appropriate resource type:
   - Forest → Lumber
   - Hills → Brick
   - Mountains → Ore
   - Fields → Grain
   - Pastures → Wool
   - Desert → No resources
4. Each non-desert hex must be assigned a number token between 2 and 12, excluding 7.
5. Each player must receive a unique color.
6. Each player must be able to place exactly 2 settlements and 2 roads during the initial setup phase.
7. Each settlement must be connected to one road.
8. Settlements must follow the distance rule: no settlement can be placed on an intersection adjacent to another settlement.
9. Each player must receive starting resources based on the terrain hexes adjacent to their second settlement.
10. The initial turn order must be randomized, and the setup phase must follow the correct placement order (first round in turn order, second round in reverse turn order).
11. Once all players have placed their starting settlements and roads, the game must transition into the main gameplay phase.

## Use Case 1: Start New Catan Game

- Actor: Player

- Preconditions:
  - The Catan game application is launched.
  - All players are ready to join the game.

- Main Flow:
    1. Player clicks "Start Game".
    2. System asks for the total number of players.
    3. Players enter the number of participants.
    4. System validates that the number of players is between 3 and 4.
    5. System generates the Catan game board with the correct terrain hexes.
    6. System assigns resource types and number tokens to the appropriate hexes.
    7. System places the desert hex and assigns the Robber to it.
    8. System assigns each player a unique color.
    9. System randomly determines the initial turn order.
    10. System begins the initial placement phase.
    11. Each player places their first settlement and an adjacent road in turn order.
    12. Once every player has placed their first settlement and road, the system reverses the turn order.
    13. Each player places their second settlement and an adjacent road.
    14. System distributes starting resources to each player based on the terrain hexes adjacent to their second settlement.
    15. System transitions the game into the main gameplay phase.

- Alternate Flows:
    - 4.a If the player enters an invalid number of players, the system displays an error message explaining that 3 to 4 players are required.
    - 4.b The system prompts the player to enter the number of players again.
    - 11.a If a player attempts to place a settlement next to another settlement, the system rejects the placement and prompts the player to choose another intersection.
    - 11.b If a player attempts to place a road that is not connected to their newly placed settlement, the system rejects the placement.
    - 13.a If a player attempts an invalid second settlement placement, the system rejects the placement and asks the player to select another valid location.
    - 14.a The system distributes resources only after all players have successfully completed their second settlement placement.

- Postconditions:
    - The game board is fully generated with the correct terrain types and number tokens.
    - Each player has exactly 2 settlements and 2 roads placed on the board.
    - All settlements and roads follow the placement rules.
    - Each player has received their starting resources based on their second settlement.
    - The Robber is initially located on the desert hex.
    - The game is ready for the first player's turn.