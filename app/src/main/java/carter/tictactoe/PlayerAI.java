package carter.tictactoe;

import java.util.Random;

public class PlayerAI implements Game {
    Difficulty difficulty;
    Mark mark;
    TicTacToeModel game;

    public void playerAI(Difficulty difficulty, Mark mark, TicTacToeModel game) {
        this.difficulty = difficulty;
        this.mark = mark;
        this.game = game;
        if(game.getTurn() == mark) {
            takeTurn();
        }
    }

    private void takeTurn() {
        Mark[][] board = game.getBoard();
        Coordinates nextMove;
        switch(difficulty) {
            case EASY:
                //TODO
                //game.makeMove(mark,nextMove);
                break;
            case MEDIUM:
                //TODO
                //game.makeMove(mark,nextMove);
                break;
            case HARD:
                nextMove = winMove(board, mark);
                /**
                if(nextMove.getRow() == -1) {
                    //TODO
                    nextMove = tieMove(board, mark);
                }
                //this should not happen
                if(nextMove.getRow() == -1) {
                    nextMove = loseMove(board, mark);
                }
                 **/
                game.makeMove(mark,nextMove);
                break;
        }
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
                    //if the game is complete and you win, the move is a winning move
                    if(game.gameCompleted(clone)) {
                        if(game.getWinner(clone) == mark) {
                            wins[row*TicTacToeModel.BOARD_SIZE+col] = true;
                            winExists = true;
                        }
                    }
                    //if the enemy is forced into a loss, the move is a winning move
                    else {
                        if(willLose(board, oppositeMark(mark))) {
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
            int check = random.nextInt()%9;
            for(;wins[check]==false;check++) {
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
                    //if the game ends and the other play doesn't win, you did not lose
                    if(game.gameCompleted(clone)) {
                        if(game.getWinner(clone) != oppositeMark(mark)) {
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

    public void updateBoard() {
        if(game.getTurn() == mark &&
                !game.gameCompleted()) {
            takeTurn();
        }
    }
}