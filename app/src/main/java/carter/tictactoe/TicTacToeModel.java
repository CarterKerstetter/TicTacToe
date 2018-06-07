package carter.tictactoe;

public class TicTacToeModel {
    Mark[][] board;
    Mark turn = Mark.X;
    private static int BOARD_SIZE = 3;
    private Game player;
    private Game playerAI;


    public TicTacToeModel(Game player, Game playerAI) {
        board = new Mark[BOARD_SIZE][BOARD_SIZE];
        newGame();
        this.player = player;
        this.playerAI = playerAI;
    }

    public void newGame() {
        for(int row=0;row<BOARD_SIZE;row++) {
            for(int col=0;col<BOARD_SIZE;col++) {
                board[row][col] = Mark.BLANK;
            }
        }
        turn = Mark.X;
    }

    //may want to return void and use notifyAll if there is 2 player
    public synchronized void makeMove(Mark mark, Coordinates coordinates) {
        if(mark==turn) {
            int row = coordinates.getRow();
            int col = coordinates.getCol();
            board[row][col] = mark;
            player.updateBoard();
            playerAI.updateBoard();
        }
    }

    public Mark[][] getBoard() {
        return board;
    }

    public boolean gameCompleted() {
        if(getWinner()==Mark.BLANK) {
            return false;
        }
        return true;
    }

    public Mark getWinner() {
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
