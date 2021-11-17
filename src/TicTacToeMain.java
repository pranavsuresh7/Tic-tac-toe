import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TicTacToeMain {

    static final int PLAYER_ONE = 1;
    static final int PLAYER_TWO = 2;
    static int GAME_MODE = 1;

    private static ArrayList<List<Integer>> gameBoard;

    static class Move {
        int row = -1, col = -1;
    };

    /*
        Initialise the board as a two-dimensional matrix with value as -1 and size as 3 * 3.
    */
    static void initialiseBoard() {
        gameBoard = new ArrayList<List<Integer>>();
        for (int i = 0; i < 3; i++) {
            gameBoard.add(Arrays.asList(-1, -1, -1));
        }
    }

    /*
        Checks if the user input is valid or not.
        Input is valid if position is inside the board, and it has not been marked yet.
    */
    static boolean isNextMoveValid(String[] move) {
        if (move.length != 2) return false;
        if (move[0].length() > 1 || move[1].length() > 1) return false;
        char rowValue = move[0].charAt(0);
        char colValue = move[1].charAt(0);
        if ((rowValue > '0') && (rowValue < '4') && (colValue > '0') && (colValue < '4')) {
            int row = rowValue - '0';
            int col = colValue - '0';
            if (gameBoard.get(row - 1).get(col - 1) == -1) {
                return true;
            }
        }
        return false;
    }

    /*
        Sets the position of the player in the board.
        If all positions are marked or there is a winner it displays the winner.
        Returns if it is possible to continue game or not.
    */
    static int playNextMove(int player, int row, int col,
                            Player playerOne, Player playerTwo, Player playerThree, boolean isPlayerTwoAi) {
        int canGameContinue = 1;
        gameBoard.get(row).set(col, player);
        int playerWon = getWinner();
        if (playerWon != 0 || !checkAnyMoveLeft()) {
            if (playerWon == PLAYER_ONE) {
                playerOne.setNumberOfWins(1);
            } else if (playerWon == PLAYER_TWO) {
                if (isPlayerTwoAi) {
                    playerThree.setNumberOfWins(1);
                } else {
                    playerTwo.setNumberOfWins(1);
                }
            } else {
                playerOne.setNumberOfTies(1);
                if (isPlayerTwoAi) {
                    playerThree.setNumberOfTies(1);
                } else {
                    playerTwo.setNumberOfTies(1);
                }
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
            if (gameBoard.get(i).get(0) == 1 && gameBoard.get(i).get(1) == 1 && gameBoard.get(i).get(2) == 1) {
                return PLAYER_ONE;
            }
            if (gameBoard.get(i).get(0) == 2 && gameBoard.get(i).get(1) == 2 && gameBoard.get(i).get(2) == 2) {
                return PLAYER_TWO;
            }
            if (gameBoard.get(0).get(i) == 1 && gameBoard.get(1).get(i) == 1 && gameBoard.get(2).get(i) == 1) {
                return PLAYER_ONE;
            }
            if (gameBoard.get(0).get(i) == 2 && gameBoard.get(1).get(i) == 2 && gameBoard.get(2).get(i) == 2) {
                return PLAYER_TWO;
            }
        }
        if (gameBoard.get(0).get(0) == 1 && gameBoard.get(1).get(1) == 1 && gameBoard.get(2).get(2) == 1) {
            return PLAYER_ONE;
        }
        if (gameBoard.get(0).get(0) == 2 && gameBoard.get(1).get(1) == 2 && gameBoard.get(2).get(2) == 2) {
            return PLAYER_TWO;
        }
        if (gameBoard.get(2).get(0) == 1 && gameBoard.get(1).get(1) == 1 && gameBoard.get(0).get(2) == 1) {
            return PLAYER_ONE;
        }
        if (gameBoard.get(2).get(0) == 2 && gameBoard.get(1).get(1) == 2 && gameBoard.get(0).get(2) == 2) {
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
                if (gameBoard.get(i).get(j) == -1) {
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
                gameBoard.get(i).set(j, -1);
            }
        }
    }

    /*
        Minimax Algorithm which finds the best possible move.
    */
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
                    if (gameBoard.get(i).get(j) == -1) {
                        gameBoard.get(i).set(j, player);
                        bestScore = Math.max(bestScore, miniMax(player,
                                !checkIsMaxValue));
                        gameBoard.get(i).set(j, -1);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameBoard.get(i).get(j) == -1) {
                        gameBoard.get(i).set(j, player == PLAYER_TWO ? PLAYER_ONE : PLAYER_TWO);
                        bestScore = Math.min(bestScore, miniMax(player,
                                !checkIsMaxValue));
                        gameBoard.get(i).set(j, -1);
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
        if(GAME_MODE == 2 && player == PLAYER_TWO){
            ArrayList<Move> unmarkedMoves = new ArrayList<>();
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(gameBoard.get(i).get(j)==-1){
                        Move unmarkedMove = new Move();
                        unmarkedMove.row = i+1;
                        unmarkedMove.col = j+1;
                        unmarkedMoves.add(unmarkedMove);
                    }
                }
            }
            int randomUnmarkedMovesIndex = ThreadLocalRandom.current().nextInt(0,unmarkedMoves.size());
            return unmarkedMoves.get(randomUnmarkedMovesIndex);
        }

        Move bestMove = new Move();
        int tempBestScore = -1000;
        int row = -1, col = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard.get(i).get(j) == -1) {
                    gameBoard.get(i).set(j, player);
                    int nextBestMoveScore = miniMax(player, false);
                    gameBoard.get(i).set(j, -1);
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
        gameBoard.get(suggestedNextBestMove.row - 1).set(suggestedNextBestMove.col - 1, player);
        System.out.println("Preview of the suggested move.");
        displayBoard();
        gameBoard.get(suggestedNextBestMove.row - 1).set(suggestedNextBestMove.col - 1, -1);
        return suggestedNextBestMove;
    }

    /*
        Displays the board in the console with "_" as unmarked position-
        and "X" for PLAYER_ONE position and "O" for PLAYER_TWO.
    */
    static void displayBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
            for (int j = 0; j < 3; j++) {
                if (gameBoard.get(i).get(j) == -1) {
                    System.out.print("_ ");
                }
                if (gameBoard.get(i).get(j) == PLAYER_ONE) {
                    System.out.print("X ");
                }
                if (gameBoard.get(i).get(j) == PLAYER_TWO) {
                    System.out.print("O ");
                }
            }
        }
        System.out.println();
    }

    static int getValidGameModeInputFromUser(Scanner sc) {
        int gameMode = -1;
        while (true) {
            System.out.println("GAME MODE " + "\n1.HARD press 1" + "\n2.EASY press 2");
            String gameModeInput = sc.nextLine();
            if (gameModeInput.equals("1")) {
                gameMode = 1;
                break;
            } else if (gameModeInput.equals("2")) {
                gameMode = 2;
                break;
            } else {
                continue;
            }
        }
        return gameMode;
    }

    static int getValidYesOrNoInputFromUser(Scanner sc, String message) {
        int validInput;
        while (true) {
            System.out.println(message);
            String gameModeInput = sc.nextLine();
            if (gameModeInput.equals("1")) {
                validInput = 1;
                break;
            } else if (gameModeInput.equals("0")) {
                validInput = 0;
                break;
            } else {
                continue;
            }
        }
        return validInput;
    }

    static void displayScoreOfTheGame(Player playerOne, Player playerTwo, Player playerThree, boolean isPlayerTwoAi) {
        System.out.println("Game Statistics: ");
        System.out.println(playerOne.getName() + "->");
        System.out.println("  #Wins: " + playerOne.getNumberOfWins());
        System.out.println("  #Losses: " + playerOne.getNumberOfLoss());
        System.out.println("  #Ties: " + playerOne.getNumberOfTies());
        if (isPlayerTwoAi) {
            System.out.println("\n" + playerThree.getName() + "->");
            System.out.println("  #Wins: " + playerThree.getNumberOfWins());
            System.out.println("  #Losses: " + playerThree.getNumberOfLoss());
            System.out.println("  #Ties: " + playerThree.getNumberOfTies());

        } else {
            System.out.println("\n" + playerTwo.getName() + "->");
            System.out.println("  #Wins: " + playerTwo.getNumberOfWins());
            System.out.println("  #Losses: " + playerTwo.getNumberOfLoss());
            System.out.println("  #Ties: " + playerTwo.getNumberOfTies());
        }
    }

    static Move getNextMoveFromUser(Scanner sc, Player currentPlayer, Player playerTwo,
                                    Player playerThree, int player, Boolean isPlayerTwoAi) {
        Move nextMoveFromUser = new Move();
        System.out.println(currentPlayer.getName() + "'s turn,Enter a valid position in the format row column, \n" +
                "the value should be greater than 0 and less than 4. Examples of valid positions - 1 2, 3 3, 2 3." +
                "\nType HELP for autosuggestion. " +
                "\nType RESET for resetting the game.");
        String inputFromPlayer = sc.nextLine();
        if (inputFromPlayer.equalsIgnoreCase("HELP")) {
            Move suggestedNextBestMove = autoSuggestNextBestMove(player);

            if (getValidYesOrNoInputFromUser(sc,
                    "Do You want to continue with the suggestion if 'Yes' press 1 if 'No' press 0.") == 1) {
                nextMoveFromUser.row = suggestedNextBestMove.row;
                nextMoveFromUser.col = suggestedNextBestMove.col;
            } else {
                System.out.println("Enter a valid position in the format row column , the value should be greater" +
                        " than 0 and less than 4. ");
                String[] input = sc.nextLine().split(" ");
                while (!isNextMoveValid(input)) {
                    System.out.println("Enter a valid position in the format row column , the value should be greater" +
                            " than 0 and less than 4. Examples of valid positions - 1 2, 3 3, 2 3.");
                    input = sc.nextLine().split(" ");
                }
                nextMoveFromUser.row = Integer.parseInt(input[0]);
                nextMoveFromUser.col = Integer.parseInt(input[1]);
            }
        } else if (inputFromPlayer.equalsIgnoreCase("RESET")) {
            reInitialiseBoard();
            currentPlayer.setNumberOfTies(1);
            if (isPlayerTwoAi) {
                playerThree.setNumberOfTies(1);
            } else {
                playerTwo.setNumberOfTies(1);
            }
            displayScoreOfTheGame(currentPlayer, playerTwo, playerThree, isPlayerTwoAi);
            nextMoveFromUser.row = -2;
            nextMoveFromUser.col = -2;
        } else {
            String[] input = inputFromPlayer.split(" ");
            while (!isNextMoveValid(input)) {
                System.out.println("Enter a valid position in the format row column , the value should be greater" +
                        " than 0 and less than 4. Examples of valid positions - 1 2, 3 3, 2 3 ");
                input = sc.nextLine().split(" ");
            }
            nextMoveFromUser.row = Integer.parseInt(input[0]);
            nextMoveFromUser.col = Integer.parseInt(input[1]);
        }
        return nextMoveFromUser;
    }

    /*
        Starts playing the game.
    */
    static void playGame() {
        Scanner sc = new Scanner(System.in);
        Player playerOne = new Player();
        Player playerTwo = new Player();
        Player playerThree = new Player();
        System.out.println("Player 1 enter name.");
        playerOne.setName(sc.nextLine());
        System.out.println("Player 2 enter name.");
        playerTwo.setName(sc.nextLine());
        playerThree.setName("CPU");
        while (true) {
            playerOne.setNumberOfGames(1);
            boolean isPlayerTwoAi = getValidYesOrNoInputFromUser(sc
                    ,"Do you want to play with Computer? If 'Yes' then press 1 or press 0 for 'No'") == 1;
            if (!isPlayerTwoAi) {
                playerTwo.setNumberOfGames(1);
                GAME_MODE = 1;
            } else {
                playerThree.setNumberOfGames(1);
                GAME_MODE = getValidGameModeInputFromUser(sc);
            }
            System.out.println("'_' is marked as unmarked position and" + "'X' for " + playerOne.getName() +
                    " and 'O' for " + (isPlayerTwoAi ? playerThree.getName() : playerTwo.getName()));
            int player = PLAYER_ONE;
            boolean shouldGameContinue = true;
            do {
                displayBoard();
                int row, col;
                if (player == PLAYER_ONE) {
                    Move nextMoveFromUser = getNextMoveFromUser(sc, playerOne, playerTwo
                            , playerThree, player, isPlayerTwoAi);
                    row = nextMoveFromUser.row;
                    col = nextMoveFromUser.col;
                    if (nextMoveFromUser.row == -2 && nextMoveFromUser.col == -2) {
                        break;
                    }
                } else if (!isPlayerTwoAi && player == PLAYER_TWO) {
                    Move nextMoveFromUser = getNextMoveFromUser(sc, playerTwo
                            , playerOne, playerThree, player, false);
                    row = nextMoveFromUser.row;
                    col = nextMoveFromUser.col;
                    if (nextMoveFromUser.row == -2 && nextMoveFromUser.col == -2) {
                        break;
                    }
                } else {
                    System.out.println("CPU's Turn");
                    Move nextBestPredictedMove = predictNextBestMove(PLAYER_TWO);
                    row = nextBestPredictedMove.row;
                    col = nextBestPredictedMove.col;
                }
                if (!isNextMoveValid(new String[]{String.valueOf(row), String.valueOf(col)})) {
                    while (true) {
                        System.out.println("You've entered an invalid position, please enter a valid position in the" +
                                "\nformat row column which should be less than 4 and greater than 0 " +
                                "and it shouldn't be a marked position.");
                        String[] move = sc.nextLine().split(" ");
                        if (isNextMoveValid(move)) {
                            row = Integer.parseInt(move[0]);
                            col = Integer.parseInt(move[1]);
                            break;
                        }
                    }
                }
                if (playNextMove(player, row - 1, col - 1, playerOne, playerTwo, playerThree, isPlayerTwoAi) == 0) {
                    displayScoreOfTheGame(playerOne, playerTwo, playerThree, isPlayerTwoAi);
                    if (getValidYesOrNoInputFromUser(sc, "Do you want to continue the game? Press 1 to " +
                            "play game once again and for EXIT press 0.")==1) {
                        reInitialiseBoard();
                        break;
                    } else {
                        shouldGameContinue = false;
                    }
                }
                player = player == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;
            } while (shouldGameContinue);

            if (!shouldGameContinue) {
                break;
            }
        }

        sc.close();
    }

    public static void main(String[] args) {
        initialiseBoard();
        playGame();
    }
}

