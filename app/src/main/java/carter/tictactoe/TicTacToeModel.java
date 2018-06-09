/**
 * author: Carter Kerstetter
 */

package carter.tictactoe;

public class TicTacToeModel {
    private Mark[][] board;
    private Mark turn = Mark.X;
    public static int BOARD_SIZE = 3;


    public TicTacToeModel() {
        board = new Mark[BOARD_SIZE][BOARD_SIZE];
        gameSetup();
        //this.player = player;
        //this.playerAI = playerAI;
    }

    public synchronized void setBoard(Mark[][] board) {
        this.board = board;
    }


    private synchronized void gameSetup() {
        for(int row=0;row<BOARD_SIZE;row++) {
            for(int col=0;col<BOARD_SIZE;col++) {
                board[row][col] = Mark.BLANK;
            }
        }
        turn = Mark.X;
    }

    public synchronized void newGame() {
        gameSetup();
        notifyAll();
    }

    //may want to return void and use notifyAll if there is 2 player
    public synchronized boolean makeMove(Move move) {
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
                    return true;
                }
            }
        }
        return false;
    }

    private synchronized void swapTurns() {
        if(turn == Mark.X) {
            turn = Mark.O;
        }
        else {
            turn = Mark.X;
        }
    }

    public synchronized Mark[][] getBoard() {
        return board;
    }

    public synchronized Mark getTurn() {
        return turn;
    }

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
