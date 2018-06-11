package carter.tictactoe;
import java.util.Random;

/**
 * Class that represents the opponent AI player
 */
public class PlayerAI implements Runnable{
    private Difficulty difficulty;
    private Mark mark;
    private final TicTacToeModel game;
    private TicTacToeModel testGame = new TicTacToeModel();
    private volatile boolean exit = false;
    private boolean firstMove = true;

    /**
     * constructor
     * @param difficulty level the AI plays at
     * @param mark what mark the AI is
     * @param game the game that will be played on
     */
    PlayerAI(Difficulty difficulty, Mark mark, TicTacToeModel game) {
        this.difficulty = difficulty;
        this.mark = mark;
        this.game = game;
    }

    /**
     * run method for the AI thread
     */
    @Override
    public void run() {
        while(!exit && !game.gameCompleted()) {
            while (!exit && game.getTurn() != mark) {
                synchronized (game) {
                    try {
                        game.wait();
                    }
                    catch(InterruptedException e) {
                        stopRunning();
                    }
                }
            }
            if(!exit) {
                takeTurn();
            }
        }
    }

    /**
     * sets exit to true, when the AI thread is next updated it will exit the game
     */
    public void stopRunning() {
        exit = true;
    }

    /**
     * helper method that gives the opposite mark of the one given
     * @param mark mark that will be reversed
     * @return X if O is given, O if X is given, BLANK if BLANK is given.
     */
    public Mark oppositeMark(Mark mark) {
        if(mark == Mark.X) {
            return Mark.O;
        }
        else if(mark == Mark.O) {
            return Mark.X;
        }
        else {
            return Mark.BLANK;
        }
    }

    /**
     * helper method that takes the move for the AI for one move
     */
    private void takeTurn() {
        Mark[][] board = game.getBoard();
        Coordinates nextMove;
        Move move;
        switch(difficulty) {
            case EASY:
                nextMove = oneTurnWinMove(mark);
                if(nextMove.getRow() == -1) {
                    nextMove = loseMove(board, mark);
                }
                if(nextMove.getRow() == -1) {
                    nextMove = tieMove(board, mark);
                }
                /**
                 * I don't think this is possible
                if(nextMove.getRow() == -1) {
                    nextMove = winMove(board, mark);
                }
                 **/
                move = new Move(nextMove, mark);
                game.makeMove(move);
                break;
            case MEDIUM:
                nextMove = oneTurnWinMove(mark);
                if(firstMove) {
                    nextMove = loseMove(board, mark);
                    firstMove = false;
                }
                if(nextMove.getRow() == -1) {
                    nextMove = oneTurnWinMove(oppositeMark(mark));
                }
                if(nextMove.getRow() == -1) {
                    nextMove = tieMove(board, mark);
                }
                /**
                 * I don't think this is possible
                if(nextMove.getRow() == -1) {
                    nextMove = loseMove(board, mark);
                }
                if(nextMove.getRow() == -1) {
                    nextMove = winMove(board, mark);
                }
                 **/
                move = new Move(nextMove, mark);
                game.makeMove(move);
                break;
            case HARD:
                nextMove = winMove(board, mark);
                if(nextMove.getRow() == -1) {
                    nextMove = tieMove(board, mark);
                }
                //the hard AI doesnt need to check for losing moves
                move = new Move(nextMove, mark);
                game.makeMove(move);
                break;
        }
    }

    /**
     * Helper method that clones a given board
     * @param board board to be cloned
     * @return clone of the board given
     */
    protected Mark[][] cloneBoard(Mark[][] board) {
        Mark[][] clone = new Mark[TicTacToeModel.BOARD_SIZE][TicTacToeModel.BOARD_SIZE];
        for(int row = 0;row<TicTacToeModel.BOARD_SIZE;row++) {
            clone[row] = board[row].clone();
        }
        return clone;
    }

    /**
     * Returns the coordinates of a win move if it exists
     * @param board the current game
     * @param mark the mark that is playing now
     * @return a winning move if it exists
     */
    private Coordinates winMove(Mark[][] board, Mark mark) {
        Mark[][] clone;
        boolean winExists = false;
        boolean[] wins = new boolean[TicTacToeModel.BOARD_SPACES];
        for(int i=0;i<TicTacToeModel.BOARD_SPACES;i++) {
            wins[i] = false;
        }
        //for each possible move
        for(int row = 0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col < TicTacToeModel.BOARD_SIZE;col++) {
                //if a move can be made on a space
                if(board[row][col] == Mark.BLANK) {
                    clone = cloneBoard(board);
                    clone[row][col] = mark;
                    testGame.setBoard(clone);
                    //if the game is complete and you win, the move is a winning move
                    if(testGame.gameCompleted()) {
                        if(testGame.getWinner() == mark) {
                            wins[row*TicTacToeModel.BOARD_SIZE+col] = true;
                            winExists = true;
                        }
                    }
                    //if the enemy is forced into a loss, the move is a winning move
                    else {
                        if(willLose(clone, oppositeMark(mark))) {
                            wins[row*TicTacToeModel.BOARD_SIZE+col] = true;
                            winExists = true;
                        }
                    }
                }
            }
        }
        //randomize moves so the AI isn't the same every game.
        if(winExists) {
            Random random = new Random();
            int check = Math.abs(random.nextInt())%TicTacToeModel.BOARD_SPACES;
            for(;!wins[check];check++) {
                if(check >= TicTacToeModel.BOARD_SPACES-1) {
                    check = -1;
                }
            }
            int row = check/TicTacToeModel.BOARD_SIZE;
            int col = check%TicTacToeModel.BOARD_SIZE;
            return new Coordinates(row, col);
        }
        else {
            return new Coordinates(-1, -1);
        }
    }

    /**
     * Finds out if the player will lose if tkhe enemy plays correctly
     * @param board board configuration
     * @param mark mark that will move now
     * @return true if the player in this situation will lose
     */
    private boolean willLose(Mark[][] board, Mark mark) {
        Mark[][] clone;
        //for each move
        for(int row = 0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col < TicTacToeModel.BOARD_SIZE;col++) {
                //if the move is available
                if(board[row][col] == Mark.BLANK) {
                    clone = cloneBoard(board);
                    clone[row][col] = mark;
                    testGame.setBoard(clone);
                    //if the game ends and the other play doesn't win, you did not lose
                    if(testGame.gameCompleted()) {
                        if(testGame.getWinner() != oppositeMark(mark)) {
                            return false;
                        }
                    }
                    //the game is not over, but the other player does not have a winning move means
                    // that we did not lose
                    else if(winMove(clone, oppositeMark(mark)).getRow() == -1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * returns a tie move if one exists
     * @param board board configuration
     * @param mark mark moving now
     * @return a tie move if it exists
     */
    private Coordinates tieMove(Mark[][] board, Mark mark) {
        Mark[][] clone;
        boolean tieExists = false;
        boolean[] ties = new boolean[TicTacToeModel.BOARD_SPACES];
        for(int i=0;i<TicTacToeModel.BOARD_SPACES;i++) {
            ties[i] = false;
        }
        //for each possible move
        for(int row = 0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col < TicTacToeModel.BOARD_SIZE;col++) {
                //if a move can be made on a space
                if(board[row][col] == Mark.BLANK) {
                    clone = cloneBoard(board);
                    clone[row][col] = mark;
                    testGame.setBoard(clone);
                    //if the game is complete and you tie, the move is a tie move
                    if(testGame.gameCompleted()) {
                        if(testGame.getWinner() == Mark.BLANK) {
                            ties[row*TicTacToeModel.BOARD_SIZE+col] = true;
                            tieExists = true;
                        }
                    }
                    //if the enemy is forced into a tie, the move is a tie move
                    else {
                        if(willTie(clone, oppositeMark(mark))) {
                            ties[row*TicTacToeModel.BOARD_SIZE+col] = true;
                            tieExists = true;
                        }
                    }
                }
            }
        }
        //randomize moves so the AI isn't the same every game.
        if(tieExists) {
            Random random = new Random();
            int check = Math.abs(random.nextInt())%TicTacToeModel.BOARD_SPACES;
            for(;!ties[check];check++) {
                if(check >= TicTacToeModel.BOARD_SPACES-1) {
                    check = -1;
                }
            }
            int row = check/TicTacToeModel.BOARD_SIZE;
            int col = check%TicTacToeModel.BOARD_SIZE;
            return new Coordinates(row, col);
        }
        //should never happen with the current AI
        else {
            return new Coordinates(-1, -1);
        }
    }

    /**
     * returns whether or not the current player will tie at best
     * @param board board configuration
     * @param mark mark moving now
     * @return true if the player will tie at best, false otherwise
     */
    private boolean willTie(Mark[][] board, Mark mark) {
        Mark[][] clone;
        boolean tieExists = false;
        //for each move
        for(int row = 0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col < TicTacToeModel.BOARD_SIZE;col++) {
                //if the move is available
                if(board[row][col] == Mark.BLANK) {
                    clone = cloneBoard(board);
                    clone[row][col] = mark;
                    testGame.setBoard(clone);
                    //if the game ends and you win, you did not tie
                    if(testGame.gameCompleted()) {
                        if(testGame.getWinner() == mark) {
                            return false;
                        }
                        else if(testGame.getWinner() == Mark.BLANK) {
                            tieExists = true;
                        }
                    }
                    //the game is not over, but the other player will lose, than you can make
                    //a winning move
                    else if(willLose(clone, oppositeMark(mark))) {
                        return false;
                    }
                    //if the other play must tie, this is a tie move
                    else if (willTie(clone, oppositeMark(mark))) {
                        tieExists = true;
                    }
                }
            }
        }
        return tieExists;
    }

    /**
     * returns coordinates for a losing move if one exists
     * @param board board configuration
     * @param mark mark moving now
     * @return a losing move if one exists
     */
    private Coordinates loseMove(Mark[][] board, Mark mark) {
        Mark[][] clone;
        boolean lossExists = false;
        boolean[] losses = new boolean[TicTacToeModel.BOARD_SPACES];
        for(int i=0;i<TicTacToeModel.BOARD_SPACES;i++) {
            losses[i] = false;
        }
        //for each possible move
        for(int row = 0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col < TicTacToeModel.BOARD_SIZE;col++) {
                //if a move can be made on a space
                if(board[row][col] == Mark.BLANK) {
                    clone = cloneBoard(board);
                    clone[row][col] = mark;
                    testGame.setBoard(clone);
                    //if the game is not complete and enemy will win, move is a lose move
                    if(!testGame.gameCompleted()) {
                        if(willWin(clone, oppositeMark(mark))) {
                            losses[row*TicTacToeModel.BOARD_SIZE+col] = true;
                            lossExists = true;
                        }
                    }
                }
            }
        }
        //randomize moves so the AI isn't the same every game.
        if(lossExists) {
            Random random = new Random();
            int check = Math.abs(random.nextInt())%TicTacToeModel.BOARD_SPACES;
            for(;!losses[check];check++) {
                if(check >= TicTacToeModel.BOARD_SPACES-1) {
                    check = -1;
                }
            }
            int row = check/TicTacToeModel.BOARD_SIZE;
            int col = check%TicTacToeModel.BOARD_SIZE;
            return new Coordinates(row, col);
        }
        else {
            return new Coordinates(-1, -1);
        }
    }

    /**
     * Returns true if the current player will win, false otherwise
     * @param board board configuration
     * @param mark current players mark
     * @return true if the player will win, false otherwise
     */
    private boolean willWin(Mark[][] board, Mark mark) {
        Mark[][] clone;
        //for each move
        for(int row = 0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col < TicTacToeModel.BOARD_SIZE;col++) {
                //if the move is available
                if(board[row][col] == Mark.BLANK) {
                    clone = cloneBoard(board);
                    clone[row][col] = mark;
                    testGame.setBoard(clone);
                    //if the game ends and you win, you can win
                    if(testGame.gameCompleted()) {
                        if(testGame.getWinner() == mark) {
                            return true;
                        }
                    }
                    //the game is not over, but the other player will lose, than you can make
                    //a winning move
                    else if(willLose(clone, oppositeMark(mark))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * returns the coordinates to a one turn win move if it exists
     * @param mark the current player
     * @return a one turn win move if it exists
     */
    private Coordinates oneTurnWinMove(Mark mark) {
        Mark[][] clone;
        Mark[][] board = game.getBoard();
        boolean winExists = false;
        boolean[] wins = new boolean[TicTacToeModel.BOARD_SPACES];
        for(int i=0;i<TicTacToeModel.BOARD_SPACES;i++) {
            wins[i] = false;
        }
        //for each possible move
        for(int row = 0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col < TicTacToeModel.BOARD_SIZE;col++) {
                //if a move can be made on a space
                if(board[row][col] == Mark.BLANK) {
                    clone = cloneBoard(board);
                    clone[row][col] = mark;
                    testGame.setBoard(clone);
                    //if the game is complete and you win, the move is a winning move
                    if(testGame.getWinner() == mark) {
                        wins[row*TicTacToeModel.BOARD_SIZE+col] = true;
                        winExists = true;
                    }
                }
            }
        }
        //randomize moves so the AI isn't the same every game.
        if(winExists) {
            Random random = new Random();
            int check = Math.abs(random.nextInt())%TicTacToeModel.BOARD_SPACES;
            for(;!wins[check];check++) {
                if(check >= TicTacToeModel.BOARD_SPACES-1) {
                    check = -1;
                }
            }
            int row = check/TicTacToeModel.BOARD_SIZE;
            int col = check%TicTacToeModel.BOARD_SIZE;
            return new Coordinates(row, col);
        }
        else {
            return new Coordinates(-1, -1);
        }
    }

}
