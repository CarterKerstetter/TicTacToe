/**
 * author: Carter Kerstetter
 */

package carter.tictactoe;

import android.util.Log;

import java.util.Random;

public class PlayerAI implements Runnable{
    private Difficulty difficulty;
    private Mark mark;
    private final TicTacToeModel game;
    private TicTacToeModel testGame = new TicTacToeModel();
    private volatile boolean exit = false;
    private boolean firstMove = true;

    public PlayerAI(Difficulty difficulty, Mark mark, TicTacToeModel game) {
        this.difficulty = difficulty;
        this.mark = mark;
        this.game = game;
    }

    @Override
    public void run() {
        while(!exit && !game.gameCompleted()) {
            while (!exit && game.getTurn() != mark) {
                synchronized (game) {
                    try {
                        game.wait();
                    }
                    catch(InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
            if(!exit) {
                takeTurn();
            }
        }
    }

    public void stopRunning() {
        exit = true;
    }

    private void takeTurn() {
        Mark[][] board = game.getBoard();
        Coordinates nextMove;
        switch(difficulty) {
            case EASY:
                nextMove = oneTurnWinMove(mark);
                if(nextMove.getRow() == -1) {
                    nextMove = loseMove(board, mark);
                }
                if(nextMove.getRow() == -1) {
                    nextMove = tieMove(board, mark);
                }
                if(nextMove.getRow() == -1) {
                    nextMove = winMove(board, mark);
                }
                game.makeMove(new Move(nextMove, mark));
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
                if(nextMove.getRow() == -1) {
                    nextMove = loseMove(board, mark);
                }
                if(nextMove.getRow() == -1) {
                    nextMove = winMove(board, mark);
                }
                game.makeMove(new Move(nextMove, mark));
                break;
            case HARD:
                nextMove = winMove(board, mark);
                if(nextMove.getRow() == -1) {
                    nextMove = tieMove(board, mark);
                }
                //the hard AI doesnt need to check for losing moves
                game.makeMove(new Move(nextMove, mark));
                break;
        }
        //game.makeMove(mark,nextMove);
    }

    private Mark[][] cloneBoard(Mark[][] board) {
        Mark[][] clone = new Mark[TicTacToeModel.BOARD_SIZE][TicTacToeModel.BOARD_SIZE];
        for(int row = 0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for (int col = 0; col < TicTacToeModel.BOARD_SIZE; col++) {
                clone[row][col] = board[row][col];
            }
        }
        return clone;
    }

    private Mark oppositeMark(Mark mark) {
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

    private Coordinates winMove(Mark[][] board, Mark mark) {
        Mark[][] clone;
        boolean winExists = false;
        boolean[] wins = new boolean[TicTacToeModel.BOARD_SIZE*TicTacToeModel.BOARD_SIZE];
        for(int i=0;i<TicTacToeModel.BOARD_SIZE*TicTacToeModel.BOARD_SIZE;i++) {
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
            int check = Math.abs(random.nextInt())%9;
            for(;!wins[check];check++) {
                if(check >= 8) {
                    check = -1;
                }
            }
            return new Coordinates(check/TicTacToeModel.BOARD_SIZE,check%TicTacToeModel.BOARD_SIZE);
        }
        else {
            return new Coordinates(-1, -1);
        }
    }

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

    private Coordinates tieMove(Mark[][] board, Mark mark) {
        Mark[][] clone;
        boolean tieExists = false;
        boolean[] ties = new boolean[TicTacToeModel.BOARD_SIZE*TicTacToeModel.BOARD_SIZE];
        for(int i=0;i<TicTacToeModel.BOARD_SIZE*TicTacToeModel.BOARD_SIZE;i++) {
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
            int check = Math.abs(random.nextInt())%9;
            for(;!ties[check];check++) {
                if(check >= 8) {
                    check = -1;
                }
            }
            return new Coordinates(check/TicTacToeModel.BOARD_SIZE,check%TicTacToeModel.BOARD_SIZE);
        }
        else {
            return new Coordinates(-1, -1);
        }
    }

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

    private Coordinates loseMove(Mark[][] board, Mark mark) {
        Mark[][] clone;
        boolean lossExists = false;
        boolean[] losses = new boolean[TicTacToeModel.BOARD_SIZE*TicTacToeModel.BOARD_SIZE];
        for(int i=0;i<TicTacToeModel.BOARD_SIZE*TicTacToeModel.BOARD_SIZE;i++) {
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
                    //if the game is complete and you lose, the move is a lose move
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
            int check = Math.abs(random.nextInt())%9;
            for(;!losses[check];check++) {
                if(check >= 8) {
                    check = -1;
                }
            }
            return new Coordinates(check/TicTacToeModel.BOARD_SIZE,check%TicTacToeModel.BOARD_SIZE);
        }
        else {
            return new Coordinates(-1, -1);
        }
    }

    private boolean willWin(Mark[][] board, Mark mark) {
        Mark[][] clone;
        boolean winExists = false;
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
                        else {
                            return false;
                        }
                    }
                    //the game is not over, but the other player will lose, than you can make
                    //a winning move
                    else if (willLose(clone, oppositeMark(mark))) {
                        return true;
                    }
                    //if the other play must tie, this is a tie move
                    else {
                        return false;
                    }
                }
            }
        }
        if(game.getWinner() == mark) {
            return true;
        }
        else {
            return false;
        }
    }

    private Coordinates oneTurnWinMove(Mark mark) {
        Mark[][] clone;
        Mark[][] board = game.getBoard();
        boolean winExists = false;
        boolean[] wins = new boolean[TicTacToeModel.BOARD_SIZE*TicTacToeModel.BOARD_SIZE];
        for(int i=0;i<TicTacToeModel.BOARD_SIZE*TicTacToeModel.BOARD_SIZE;i++) {
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
            int check = Math.abs(random.nextInt())%9;
            for(;!wins[check];check++) {
                if(check >= 8) {
                    check = -1;
                }
            }
            return new Coordinates(check/TicTacToeModel.BOARD_SIZE,check%TicTacToeModel.BOARD_SIZE);
        }
        else {
            return new Coordinates(-1, -1);
        }
    }

}
