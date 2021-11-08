import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {

    static int PLAYER_ONE = 1;
    static int PLAYER_TWO = 2;

    private static ArrayList<List<Integer>> board;

    static void initialiseBoard() {
        /*
            Initialises board as two-dimensional ArrayList with value as -1 and size as 3 * 3.
        */
        board = new ArrayList<List<Integer>>();
        for (int i = 0; i < 3; i++) {
            board.add(Arrays.asList(-1, -1, -1));
        }
    }

    static boolean isNextMoveValid(int row, int col) {
        /*
            Checks if the input is valid or not. By going through following steps.
            1. If the position is out of the board or not.
            2. If it is already marked or not.
        */
        if (row > 2 || row < 0 || col > 2 || col < 0) {
            return false;
        }
        if (board.get(row).get(col) == -1) {
            return true;
        }
        return false;
    }

    static int playNextMove(int player, int row, int col) {
        /*
            Marks the board with respective player his/her position.
            If the game is fully marked or there is a winner returns false else true.
        */
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
        }
        return canGameContinue;
    }

    static int getWinner() {
        /*
            Checks if there is pattern where PLAYER_ONE or PLAYER_TWO consecutive appears in a row or column or
            diagonally. Returns PLAYER_ONE or PLAYER_TWO based on the consecutive pattern appearance as above-mentioned
            condition and TIE if no one wins.
        */
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

    static boolean checkAnyMoveLeft() {
        /*
            Returns true if there is any position left to mark else false.
        */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j) == -1) {
                    return true;
                }
            }
        }
        return false;
    }

    static void reInitialiseBoard() {
        /*
            Initialise the board with -1 as the pre-starting state of the game.
        */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.get(i).set(j, -1);
            }
        }
    }

    static void displayBoard() {
        /*
            Displays the board in the console with "_" as unmarked position-
            and "X" for PLAYER_ONE position and "O" for PLAYER_TWO.
        */
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

    static void playGame() {
        /*
            Starts playing the game.
        */
        Scanner sc = new Scanner(System.in);
        System.out.println("_ is marked as unmarked position and 'X' for player 1 and 'O' for player 2");
        int player = PLAYER_ONE;
        boolean shouldGameContinue = true;
        do {
            displayBoard();
            if (player == PLAYER_ONE) {
                System.out.println("Player 1 turn, enter valid position in the format row column , the value should  greater than 0 and less than 4");
            } else {
                System.out.println("Player 2 turn type position, the value should  greater than 0 and less than 4");
            }
            int row = sc.nextInt();
            int col = sc.nextInt();
            if (!isNextMoveValid(row - 1, col - 1)) {
                while (true) {
                    System.out.println("You've entered invalid position, please enter valid position in the format row column which should be lesser than 4 and greater than 0");
                    row = sc.nextInt();
                    col = sc.nextInt();
                    if (isNextMoveValid(row - 1, col - 1)) {
                        break;
                    }
                }
            }
            if (playNextMove(player, row - 1, col - 1) == 0) {
                System.out.println("Do you want to continue the game? Press 1 to play game once again else press 0");
                if (sc.nextInt() == 1) {
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
