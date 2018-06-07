package carter.tictactoe;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

public class PlayerAITest {
    private int row_top = 0;
    private int row_middle = 1;
    private int row_bottom = 2;
    private int col_left = 0;
    private int col_middle = 1;
    private int col_right = 2;
    private Coordinates coordinates = Mockito.mock(Coordinates.class);;
    private Move move = Mockito.mock(Move.class);
    private TicTacToeModel game = Mockito.mock(TicTacToeModel.class);
    private PlayerAI CuT;
    private Mark mark_x = Mark.X;
    private Mark mark_o = Mark.O;
    private Mark mark_blank = Mark.BLANK;
    private Difficulty easy = Difficulty.EASY;
    private Difficulty medium = Difficulty.MEDIUM;
    private Difficulty hard = Difficulty.HARD;
    private Mark[][] board;
    private Mark[][] test_board;

    @Test
    public void constructor_test() {
        new PlayerAI(easy,mark_o,game);
    }

    /**
    @Test
    public void play_easy_test() {

    }


    @Test
    public void play_medium_test() {

    }
     **/
/**
    @Test
    public void play_hard_test() {
        //Mockito.when(game.gameCompleted()).thenReturn(false);
        //Mockito.when(game.getTurn()).thenReturn(mark_x);
        game = new TicTacToeModel();
        CuT = new PlayerAI(hard,mark_o,game);
        Mark[][] justTest = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                justTest[row][col] = mark_blank;
            }
        }
        justTest[0][2] = Mark.O;
        justTest[1][1] = Mark.X;
        //justTest[0][2] = Mark.O;
        game.setBoard(justTest);
        Thread thread = new Thread(CuT);
        thread.start();
        game.makeMove(new Move(new Coordinates(2,2), mark_x));
        try {
            TimeUnit.MINUTES.sleep(1);
        }
        catch(InterruptedException e) {

        }
        //Mockito.when(game.getTurn()).thenReturn(mark_o);
        //game.notifyAll();
    }
**/

}
