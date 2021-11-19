# TicTacToe

## About Game:
Tic-tac-toe is played on a three-by-three grid by two players, who alternately place the marks X and O in one of the nine spaces in the grid.

## Rules:
1. You are X, your friend (or the computer in this case) is O. Players take turns putting their marks in empty squares.
2. The first player to get 3 of his/her marks in a row (up, down, across, or diagonally) is the winner.
3. When all 9 squares are full, the game is over. If no player has 3 marks in a row, the game ends in a tie. 

## Features:
1. Two people are able to put marks on the 3*3 board.
2. Asking the players to mark on the board after his/her turn.
3. Announce the winner if the player get 3 marks in a row (up, down, across, or diagonally).
4. Announce draw when all 9 squares on board are full.
5. Playing game with an unbeatable AI. Used minimax algorithm for this.
6. Player can play with computer in two modes.
   1. EASY MODE - Computer/AI won't compete very hard.
   2. HARD MODE - Computer/AI will compete very hard (Hardly impossible to beat).
7. Player can get auto-suggestion for best possible move based on current state of the board.
8. Player statistics(No of wins/loss/ties) of both the players are displayed after a draw/win/loss condition in a game.
9. Game Can be continued many times as the players/player willing to play.

## Implementation Core Functions:
- Function initialiseBoard:Initialise the 3*3 board (ArrayList) with -1. Where '-1' stands for unmarked position in board.(For
  PLAYER_ONE it is 1 and for PLAYER_TWO it is 2)
  ```java
    static void initialiseBoard()
  ```

- Function playNextMove: Marks the value based on the player and position in the board. If there is a tie/win/loss it declares the winner and returns 0. This is used to halt the game.
    ```java
    static int playNextMove(int player, int row, int col, Player playerOne, Player playerTwo, Player playerThree, boolean isPlayerTwoAi)
    ```
  
- Function predictNextBestMove: Takes the player value(for PLAYER_ONE it is 1 and for PLAYER_TWO it is 2). Iterates through all the unmarked positions
and calls minimax algorithm to get best move for the player. By comparing the score given by minimax algorithm the largest score value position is taken as the best move.
  ```java
  static Move predictNextBestMove(int player)
  ```
  - Function miniMax: Will consider all the possible ways the game can go and returns the best value for that move, assuming the opponent also plays optimally Takes the player value with a boolean variable, which is used to toggle to get max/min scores at respective states of when played optimally.
    ```java
    static int miniMax(int player, boolean checkIsMaxValue) {
        int playerWon = getWinner();
        if (playerWon == player) {
            return 10;
        }
        if (playerWon != 0) {
            return -10;
        }
        if (!checkAnyMoveLeft()) {
            return 0;
        }
        if (checkIsMaxValue) {
            int bestScore = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.get(i).get(j) == -1) {
                        board.get(i).set(j, player);
                        bestScore = Math.max(bestScore, miniMax(player,
                                !checkIsMaxValue));
                        board.get(i).set(j, -1);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.get(i).get(j) == -1) {
                        board.get(i).set(j, player == PLAYER_TWO ? PLAYER_ONE : PLAYER_TWO);
                        bestScore = Math.min(bestScore, miniMax(player,
                                !checkIsMaxValue));
                        board.get(i).set(j, -1);
                    }
                }
            }
            return bestScore;
        }
    }
    ```
    
- Function autoSuggestNextBestMove:Returns the best possible move for the player called and displays a preview in the console. It calls predictNextBestMove function to get best move for the player.
  ```java
    static Move autoSuggestNextBestMove(int player) 
  ```
  
- Function getWinner:If there is a winning pattern for a player then it returns the player value(for PLAYER_ONE it is 1 and PLAYER_TWO it is 2) or else it returns tie.
  ```java
    static int getWinner()
  ```

- Function playGame: Starts playing the game. Game can be played in either with the CPU/Ai(Single mode) or manually with two players.
Player can RESET the game if at anytime he wants and continue playing game as long as the player decides.
   ```java
    static void playGame() 
  ```
  
- Function displayGameScore: Display the game statistics like number of wins,losses,ties of 
the players. 
  ```java
  static void displayGameScore(Player playerOne, Player playerTwo, Player playerThree, boolean isPlayerTwoAi)
  ```

## Future Scope:
- Predict the game is going to be tied, if there is no way for both the players to win.
- Players data can be stored on a database 
  - So entire player statistics associated with certain player could be displayed.
- Medium mode of the game with the computer/Ai can be added.
- Instead of console for input/output can be changed to GUI.
