# Tic-Tac-Toe Scoping

## About game:
Tic-tac-toe is played on a three-by-three grid by two players, who alternately place the marks X and O in one of the nine spaces in the grid.

## Rules:
1. You are X, your friend (or the computer in this case) is O. Players take turns putting their marks in empty squares. 
2. The first player to get 3 of his/her marks in a row (up, down, across, or diagonally) is the winner.
3. When all 9 squares are full, the game is over. If no player has 3 marks in a row, the game ends in a tie. 

## Core features for MVP:
1. Two people should be able to put marks on the 3*3 board. 
2. Ask/toggle between the players to mark on the board after his/her turn.
3. Anounce the winner if he/she get 3 of his/her marks in a row (up, down, across, or diagonally).
4. Anounce draw when all 9 squares on board are full. 
   
## Implementation of MVP:
- initialiseBoard() function which should initialise the matrix 3*3 size with "-1" and return.
- playNextMove(board,row,col,player) function should mark the matrix. If it is player1 then it should mark 1 which corresponds to "X" and similarly player2 "O".
- isNextMoveValid() function should return true if board is fully marked else false.
- getWinner() function should return the winner(player1 or player2) or tie. 

## Advanced Features/Addons:
1. Single player match that is between player and computer.
2. Predict which is the best move to player.


## Implementation of Advanced Features/Addons:
- getNextBestMove(Board,Player) function will predict and return the best move using mini-max algorithm. 

