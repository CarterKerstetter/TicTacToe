package carter.tictactoe;

/**
 * Model for the tic tac toe game
 */
public class TicTacToeModel {
    private Mark[][] board;
    private Mark turn = Mark.X;
    public static final int BOARD_SIZE = 3;
    public static final int BOARD_SPACES = 9;
    public static final int WIN_PATHS = 8;

    /**
     * constructor
     */
    TicTacToeModel() {
        board = new Mark[BOARD_SIZE][BOARD_SIZE];
        gameSetup();
    }

    /**
     * Setter for the board of the game
     * @param board a 2D array of Mark that represents the game board
     */
    public synchronized void setBoard(Mark[][] board) {
        this.board = board;
    }

    /**
     * helper method that sets up the game
     */
    private synchronized void gameSetup() {
        for(int row=0;row<BOARD_SIZE;row++) {
            for(int col=0;col<BOARD_SIZE;col++) {
                board[row][col] = Mark.BLANK;
            }
        }
        turn = Mark.X;
    }

    /**
     * method that resets the board and starts a new game
     */
    public synchronized void newGame() {
        gameSetup();
        notifyAll();
    }


    /**
     * Method that is called to make a move on the game board
     * @param move object representing a move in tic tac toe
     */
    public synchronized void makeMove(Move move) {
        Mark mark = move.getMark();
        Coordinates coordinates = move.getCoordinates();
        if(!gameCompleted()) {
            if (mark == turn) {
                int row = coordinates.getRow();
                int col = coordinates.getCol();
                if (board[row][col] == Mark.BLANK) {
                    board[row][col] = mark;
                    swapTurns();
                    notifyAll();
                }
            }
        }
    }

    /**
     * helper method that changes the turn
     */
    private synchronized void swapTurns() {
        if(turn == Mark.X) {
            turn = Mark.O;
        }
        else {
            turn = Mark.X;
        }
    }

    /**
     * Getter for the game board
     * @return the game board
     */
    public synchronized Mark[][] getBoard() {
        return board;
    }

    /**
     * Getter for the turn
     * @return who the current turn belongs to
     */
    public synchronized Mark getTurn() {
        return turn;
    }

    /**
     * tells whether the game is completed or not
     * @return true if the game is done, false otherwise
     */
    public synchronized boolean gameCompleted() {
        if (getWinner() == Mark.BLANK) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (board[row][col] == Mark.BLANK) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * setter for tohe turn
     * @param turn turn to be set
     */
    public synchronized void setTurn(Mark turn) {
        this.turn = turn;
    }

    /**
     * shows the winning path if one exists (expects there is only one)
     * @return an integer representing the winning path:
     * 0 = top row
     * 1 = middle row
     * 2 = bottom row
     * 3 = left column
     * 4 = middle column
     * 5 = right column
     * 6 = top left to bottom right diagonal
     * 7 = top right to bottom left diagonal
     * 8 = no winning path exists
     */
    public synchronized int getWinPath() {
        //check for wins across a row
        for(int row = 0;row<BOARD_SIZE;row++) {
            if(board[row][0] == board[row][1] &&
                    board[row][1] == board[row][2] &&
                    board[row][0] != Mark.BLANK) {
                return row;
            }
        }
        //check for wins across a column
        for(int col = 0;col<BOARD_SIZE;col++) {
            if(board[0][col] == board[1][col] &&
                    board[1][col] == board[2][col] &&
                    board[0][col] != Mark.BLANK) {
                return col + 3;
            }
        }
        //check diagonals
        Mark topLeft = board[0][0];
        Mark topRight = board[0][2];
        Mark bottomLeft = board[2][0];
        Mark bottomRight = board[2][2];
        Mark middle = board[1][1];
        if(topLeft == middle && middle == bottomRight & middle != Mark.BLANK) {
            return 6;
        }
        if(bottomLeft == middle && middle == topRight && middle != Mark.BLANK) {
            return 7;
        }
        return 8;

    }

    /**
     * returns the Mark of the winner
     * @return the mark of the winning player (BLANK if there is none)
     */
    public synchronized Mark getWinner() {
        //check for wins across a row
        for(int row = 0;row<BOARD_SIZE;row++) {
            if(board[row][0] == board[row][1] &&
                    board[row][1] == board[row][2] &&
                    board[row][0] != Mark.BLANK) {
                return board[row][0];
            }
        }
        //check for wins across a column
        for(int col = 0;col<BOARD_SIZE;col++) {
            if(board[0][col] == board[1][col] &&
                    board[1][col] == board[2][col] &&
                    board[0][col] != Mark.BLANK) {
                return board[0][col];
            }
        }
        //check diagonals
        Mark topLeft = board[0][0];
        Mark topRight = board[0][2];
        Mark bottomLeft = board[2][0];
        Mark bottomRight = board[2][2];
        Mark middle = board[1][1];
        if(((topLeft == middle && middle == bottomRight) ||
                (bottomLeft == middle && middle == topRight)) &&
                //middle not being equal to blank is redundant, but I kept it in case I made changes.
                middle != Mark.BLANK) {
            return middle;
        }
        return Mark.BLANK;
    }
}
