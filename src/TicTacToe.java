import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {

    static final int PLAYER_ONE = 1;
    static final int PLAYER_TWO = 2;

    private static ArrayList<List<Integer>> board;

    static class Move {
        int row, col;
    };

    /*
        Initialise the board as a two-dimensional matrix with value as -1 and size as 3 * 3.
    */
    static void initialiseBoard() {
        board = new ArrayList<List<Integer>>();
        for (int i = 0; i < 3; i++) {
            board.add(Arrays.asList(-1, -1, -1));
        }
    }

    /*
        Checks if the user input is valid or not.
        Input is valid if position is inside the board, and it has not been marked yet.
    */
    static boolean isNextMoveValid(int row, int col) {
        if (row > 2 || row < 0 || col > 2 || col < 0) {
            return false;
        }
        if (board.get(row).get(col) == -1) {
            return true;
        }
        return false;
    }

    /*
        Sets the position of the player in the board.
        If all positions are marked or there is a winner it displays the winner.
        Returns if it is possible to continue game or not.
    */
    static int playNextMove(int player, int row, int col) {
        int canGameContinue = 1;
        board.get(row).set(col, player);
        int playerWon = getWinner();
        if (playerWon != 0 || !checkAnyMoveLeft()) {
            if (playerWon == PLAYER_ONE) {
                System.out.println("Winner is Player 1");
            } else if (playerWon == PLAYER_TWO) {
                System.out.println("Winner is player 2");
            } else {
                System.out.println("No one won, the match is tie");
            }
            canGameContinue = 0;
            displayBoard();
        }
        return canGameContinue;
    }

    /*
       If there is a winning pattern then return the winner else return tie.
    */
    static int getWinner() {
        int TIE = 0;
        for (int i = 0; i < 3; i++) {
            if (board.get(i).get(0) == 1 && board.get(i).get(1) == 1 && board.get(i).get(2) == 1) {
                return PLAYER_ONE;
            }
            if (board.get(i).get(0) == 2 && board.get(i).get(1) == 2 && board.get(i).get(2) == 2) {
                return PLAYER_TWO;
            }
            if (board.get(0).get(i) == 1 && board.get(1).get(i) == 1 && board.get(2).get(i) == 1) {
                return PLAYER_ONE;
            }
            if (board.get(0).get(i) == 2 && board.get(1).get(i) == 2 && board.get(2).get(i) == 2) {
                return PLAYER_TWO;
            }
        }
        if (board.get(0).get(0) == 1 && board.get(1).get(1) == 1 && board.get(2).get(2) == 1) {
            return PLAYER_ONE;
        }
        if (board.get(0).get(0) == 2 && board.get(1).get(1) == 2 && board.get(2).get(2) == 2) {
            return PLAYER_TWO;
        }
        if (board.get(2).get(0) == 1 && board.get(1).get(1) == 1 && board.get(0).get(2) == 1) {
            return PLAYER_ONE;
        }
        if (board.get(2).get(0) == 2 && board.get(1).get(1) == 2 && board.get(0).get(2) == 2) {
            return PLAYER_TWO;
        }
        return TIE;
    }

    /*
        Returns true if there is any position left to mark else false.
    */
    static boolean checkAnyMoveLeft() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) == -1) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
        Initialises the board with -1 same as the pre-starting state of the game.
    */
    static void reInitialiseBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.get(i).set(j, -1);
            }
        }
    }

    /*
        Minimax Algorithm which finds the best possible move.
    */
    static int miniMax(int player, boolean checkIsMaxValue) {
        int playerWon = getWinner();
        if (playerWon == PLAYER_TWO) {
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

    /*
        Predicts the next best move using minimax algorithm.
    */
    static Move predictNextBestMove(int player) {
        Move bestMove = new Move();
        int tempBestScore = -1000;
        int row = -1, col = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) == -1) {
                    board.get(i).set(j, player);
                    int nextBestMoveScore = miniMax(player, false);
                    board.get(i).set(j, -1);
                    if (nextBestMoveScore > tempBestScore) {
                        tempBestScore = nextBestMoveScore;
                        row = i;
                        col = j;
                    }
                }
            }
        }
        bestMove.row = row + 1;
        bestMove.col = col + 1;
        return bestMove;
    }

    /*
        Suggest the best possible move for the player.
    */
    static Move autoSuggestNextBestMove(int player) {
        Move suggestedNextBestMove = predictNextBestMove(player);
        board.get(suggestedNextBestMove.row).set(suggestedNextBestMove.col, player);
        System.out.println("Preview of the suggested move.");
        displayBoard();
        return predictNextBestMove(player);
    }

    /*
        Displays the board in the console with "_" as unmarked position-
        and "X" for PLAYER_ONE position and "O" for PLAYER_TWO.
    */
    static void displayBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) == -1) {
                    System.out.print("_ ");
                }
                if (board.get(i).get(j) == PLAYER_ONE) {
                    System.out.print("X ");
                }
                if (board.get(i).get(j) == PLAYER_TWO) {
                    System.out.print("O ");
                }
            }
        }
        System.out.println();
    }

    /*
        Starts playing the game.
    */
    static void playGame() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to play with Computer? If Yes then press 1 else 0");
        boolean isPlayerTwoAi = sc.nextInt() == 1 ? true : false;
        System.out.println("'_' is marked as unmarked position and 'X' for player 1 and 'O' for player 2.");
        int player = PLAYER_ONE;
        boolean shouldGameContinue = true;
        do {
            displayBoard();
            int row, col;
            if (player == PLAYER_ONE) {
                System.out.println("Player 1's turn, to get auto suggestion for the move press 1 if you don't need press 2");
                if (sc.nextInt() == 1) {
                    Move suggestedNextBestMove = autoSuggestNextBestMove(player);
                    System.out.println("Do You want to continue with the suggestion if 'Yes' press 1 if 'No' press 2.");
                    if (sc.nextInt() == 1) {
                        row = suggestedNextBestMove.row;
                        col = suggestedNextBestMove.col;
                    } else {
                        System.out.println("Enter a valid position in the format row column , the value should be greater" +
                                " than 0 and less than 4. ");
                        row = sc.nextInt();
                        col = sc.nextInt();
                    }
                } else {
                    row = sc.nextInt();
                    col = sc.nextInt();
                }
            } else if (!isPlayerTwoAi && player == PLAYER_TWO) {
                System.out.println("Player 2's turn, to get auto suggestion for the move press 1 if you don't need press 2");
                if (sc.nextInt() == 1) {
                    Move suggestedNextBestMove = autoSuggestNextBestMove(player);
                    System.out.println("Do You want to continue with the suggestion if 'Yes' press 1 if 'No' press 2.");
                    if (sc.nextInt() == 1) {
                        row = suggestedNextBestMove.row;
                        col = suggestedNextBestMove.col;
                    } else {
                        System.out.println("Enter a valid position in the format row column , the value should be greater" +
                                " than 0 and less than 4. ");
                        row = sc.nextInt();
                        col = sc.nextInt();
                    }
                } else {
                    row = sc.nextInt();
                    col = sc.nextInt();
                }
            } else {
                System.out.println("Player 2 Computer's Turn");
                Move nextBestPredictedMove = predictNextBestMove(PLAYER_TWO);
                row = nextBestPredictedMove.row;
                col = nextBestPredictedMove.col;
            }
            if (!isNextMoveValid(row - 1, col - 1)) {
                while (true) {
                    System.out.println("You've entered an invalid position, please enter a valid position in the" +
                            "\nformat row column which should be less than 4 and greater than 0 " +
                            "and it shouldn't be a marked position.");
                    row = sc.nextInt();
                    col = sc.nextInt();
                    if (isNextMoveValid(row - 1, col - 1)) {
                        break;
                    }
                }
            }
            if (playNextMove(player, row - 1, col - 1) == 0) {
                System.out.println("Do you want to continue the game? Press 1 to play game once again else press 0.");
                if (sc.nextInt() == 1) {
                    System.out.println("Do you want to play with computer? If Yes press 1 else 0.");
                    isPlayerTwoAi = sc.nextInt() == 1 ? true : false;
                    reInitialiseBoard();
                } else {
                    shouldGameContinue = false;
                }
            }
            player = player == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;
        } while (shouldGameContinue);

        sc.close();
    }

    public static void main(String[] args) {
        initialiseBoard();
        playGame();
    }
}
