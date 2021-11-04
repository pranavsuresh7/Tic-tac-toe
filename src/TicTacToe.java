import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {

    static int PLAYER_ONE = 1;
    static int PLAYER_TWO = 2;

    private static ArrayList<List<Integer>> board;

    static void initialiseBoard() {
        board = new ArrayList<List<Integer>>();
        for (int i = 0; i < 3; i++) {
            board.add(Arrays.asList(-1, -1, -1));
        }
    }

    static boolean isNextMoveValid(int row, int col) {
        if (row > 2 || row < 0 || col > 2 || col < 0) {
            return false;
        }
        if (board.get(row).get(col) == -1) {
            return true;
        }
        return false;
    }

    static int playNextMove(int player, int row, int col) {
        int NEXT_MOVE_POSSIBLE = 1;
        board.get(row).set(col, player);
        int playerWon = getWinner();
        if (playerWon != 0) {
            NEXT_MOVE_POSSIBLE = 0;
        }
        return NEXT_MOVE_POSSIBLE;
    }

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
    }

    static void playGame() {
        Scanner sc = new Scanner(System.in);
        System.out.println("_ is marked as unmarked position and 'X' for player 1 and 'O' for player 2");
        int player = PLAYER_ONE;
        while (true) {
            displayBoard();
            if (player == PLAYER_ONE) {
                System.out.println("Player 1 turn, enter valid position in the format row column");
            } else {
                System.out.println("Player 2 turn type position");
            }
            int row = sc.nextInt();
            int col = sc.nextInt();
            if (!isNextMoveValid(row, col)) {
                while (true) {
                    System.out.println("Enter valid position in the format row column");
                    row = sc.nextInt();
                    col = sc.nextInt();
                    if (isNextMoveValid(row, col)) {
                        break;
                    }
                }
            }
            if (playNextMove(player, row, col) == 0 || !checkAnyMoveLeft()) {
                break;
            }
            player = player == 1 ? 2 : 1;
        }

        if (getWinner() == PLAYER_ONE) {
            System.out.println("Winner is Player 1");
        } else if (getWinner() == PLAYER_TWO) {
            System.out.println("Winner is player 2");
        } else {
            System.out.println("No one won, the match is tie");
        }
    }

    public static void main(String[] args) {
        initialiseBoard();
        playGame();
    }
}
