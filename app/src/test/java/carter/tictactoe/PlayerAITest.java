package carter.tictactoe;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for the playerAI class
 */
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
    private Mark[][] test_board_1;
    private Mark[][] test_board_2;
    private Mark[][] test_board_3;
    private Mark[][] test_board_4;
    private Mark[][] test_board_5;
    private Mark[][] test_board_6;
    private Mark[][] test_board_7;
    private Thread thread;
    private int extra_check = 10;

    /**
     * Test for the constructor
     */
    @Test
    public void constructor_test() {
        new PlayerAI(easy,mark_o,game);
    }

    /**
     * Test for cloneBoard method
     */
    @Test
    public void clone_board_test() {
        CuT = new PlayerAI(easy, mark_x, game);
        board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        test_board_1 = CuT.cloneBoard(board);
        assertEquals(true, Arrays.deepEquals(board,test_board_1));
        board[row_top][col_middle] = mark_x;
        test_board_1 = CuT.cloneBoard(board);
        assertEquals(true, Arrays.deepEquals(board,test_board_1));
        board[row_top][col_right] = mark_o;
        test_board_1 = CuT.cloneBoard(board);
        assertEquals(true, Arrays.deepEquals(board,test_board_1));
        board[row_bottom][col_left] = mark_x;
        test_board_1 = CuT.cloneBoard(board);
        assertEquals(true, Arrays.deepEquals(board,test_board_1));
    }

    /**
     * test for oppositeMark method
     */
    @Test
    public void opposite_mark_test() {
        game = new TicTacToeModel();
        CuT = new PlayerAI(easy, mark_x, game);
        assertEquals(mark_x, CuT.oppositeMark(mark_o));
        assertEquals(mark_o, CuT.oppositeMark(mark_x));
        assertEquals(mark_blank, CuT.oppositeMark(mark_blank));
    }

    /**
     * Test for the easy AI, also tests thread interruption
     */
    @Test
    public void play_easy_test() {
        game = new TicTacToeModel();
        CuT = new PlayerAI(easy, mark_o, game);
        thread = new Thread(CuT);
        thread.start();
        thread.interrupt();
        while(thread.getState() != Thread.State.TERMINATED) {
        }
        assertEquals(true, thread.getState() == Thread.State.TERMINATED);
        for(int i=0;i<extra_check;i++) {
            play_easy_test_helper();
        }
    }

    /**
     * helper method for easy ai test, ran multiple times (since moves are slightly random)
     */
    private void play_easy_test_helper() {
        game = new TicTacToeModel();
        //look for lose move
        CuT = new PlayerAI(easy,mark_o,game);
        game.setTurn(mark_o);
        board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        board[row_bottom][col_left] = mark_x;
        game.setBoard(board);
        thread = new Thread(CuT);
        thread.start();
        while(game.getTurn()!=mark_x) {
        }
        test_board_1 = CuT.cloneBoard(board);
        test_board_2 = CuT.cloneBoard(board);
        test_board_3 = CuT.cloneBoard(board);
        test_board_4 = CuT.cloneBoard(board);
        test_board_5 = CuT.cloneBoard(board);
        test_board_6 = CuT.cloneBoard(board);
        test_board_7 = CuT.cloneBoard(board);
        board = game.getBoard();
        test_board_1[row_middle][col_left] = mark_o;
        test_board_2[row_top][col_left] = mark_o;
        test_board_3[row_top][col_middle] = mark_o;
        test_board_4[row_top][col_right] = mark_o;
        test_board_5[row_middle][col_right] = mark_o;
        test_board_6[row_bottom][col_right] = mark_o;
        test_board_7[row_bottom][col_middle] = mark_o;
        assertEquals(true, Arrays.deepEquals(board,test_board_1) ||
                Arrays.deepEquals(board,test_board_2) ||
                Arrays.deepEquals(board, test_board_3) ||
                Arrays.deepEquals(board, test_board_4) ||
                Arrays.deepEquals(board, test_board_5) ||
                Arrays.deepEquals(board, test_board_6) ||
                Arrays.deepEquals(board, test_board_7));
        CuT.stopRunning();
        synchronized (game) {
            game.notifyAll();
        }
        //tie move
        CuT = new PlayerAI(easy,mark_o,game);
        game.setTurn(mark_o);
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        board[row_top][col_left] = mark_x;
        board[row_bottom][col_left] = mark_x;
        board[row_bottom][col_middle] = mark_x;
        board[row_middle][col_right] = mark_x;
        board[row_middle][col_left] = mark_o;
        board[row_middle][col_middle] = mark_o;
        board[row_bottom][col_right] = mark_o;
        game.setBoard(board);
        thread = new Thread(CuT);
        thread.start();
        while(game.getTurn()!=mark_x) {
        }
        test_board_1 = CuT.cloneBoard(board);
        test_board_2 = CuT.cloneBoard(board);
        board = game.getBoard();
        test_board_1[row_top][col_middle] = mark_o;
        test_board_2[row_top][col_right] = mark_o;
        assertEquals(true, Arrays.deepEquals(board,test_board_1) ||
                Arrays.deepEquals(board,test_board_2));
        CuT.stopRunning();
        synchronized (game) {
            game.notifyAll();
        }
        //win move in one turn
        CuT = new PlayerAI(easy,mark_x,game);
        game.setTurn(mark_x);
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        board[row_top][col_left] = mark_x;
        board[row_top][col_middle] = mark_x;
        board[row_bottom][col_left] = mark_o;
        board[row_bottom][col_middle] = mark_o;
        game.setBoard(board);
        thread = new Thread(CuT);
        thread.start();
        while(game.getTurn()!=mark_o) {
        }
        test_board_1 = CuT.cloneBoard(board);
        board = game.getBoard();
        test_board_1[row_top][col_right] = mark_x;
        assertEquals(true, Arrays.deepEquals(board,test_board_1));
        CuT.stopRunning();
        synchronized (game) {
            game.notifyAll();
        }
    }

    /**
     * test for the medium AI
     */
    @Test
    public void play_medium_test() {
        //first turn lose
        game = new TicTacToeModel();
        CuT = new PlayerAI(medium,mark_o,game);
        game.setTurn(mark_o);
        board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        board[row_bottom][col_left] = mark_x;
        board[row_bottom][col_middle] = mark_x;
        board[row_middle][col_left] = mark_x;
        board[row_top][col_right] = mark_x;
        board[row_middle][col_middle] = mark_o;
        board[row_middle][col_right] = mark_o;
        board[row_bottom][col_right] = mark_o;
        game.setBoard(board);
        thread = new Thread(CuT);
        thread.start();
        while(game.getTurn()!=mark_x) {
        }
        test_board_1 = CuT.cloneBoard(board);
        board = game.getBoard();
        test_board_1[row_top][col_middle] = mark_o;
        assertEquals(true, Arrays.deepEquals(board,test_board_1));
        // win in 1 move
        board[row_top][col_middle] = mark_x;
        game.setBoard(board);
        game.setTurn(mark_o);
        synchronized (game) {
            game.notifyAll();
        }
        while(game.getTurn()!=mark_x) {
        }
        test_board_1 = CuT.cloneBoard(board);
        board = game.getBoard();
        test_board_1[row_top][col_left] = mark_o;
        assertEquals(true, Arrays.deepEquals(board,test_board_1));
        //block opponent one turn win move
        game = new TicTacToeModel();
        CuT = new PlayerAI(medium,mark_x,game);
        game.setTurn(mark_x);
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        game.setBoard(board);
        thread = new Thread(CuT);
        thread.start();
        while(game.getTurn()!=mark_o) {
        }
        board[row_top][col_left] = mark_o;
        board[row_middle][col_middle] = mark_o;
        board[row_middle][col_left] = mark_x;
        game.setBoard(board);
        game.setTurn(mark_x);
        synchronized (game) {
            game.notifyAll();
        }
        while(game.getTurn()!=mark_o) {
        }
        test_board_1 = CuT.cloneBoard(board);
        board = game.getBoard();
        test_board_1[row_bottom][col_right] = mark_x;
        assertEquals(true, Arrays.deepEquals(board,test_board_1));
        //tie move
        board[row_top][col_middle] = mark_x;
        board[row_middle][col_middle] = mark_x;
        board[row_middle][col_right] = mark_o;
        board[row_bottom][col_left] = mark_o;
        board[row_bottom][col_middle] = mark_o;
        game.setBoard(board);
        game.setTurn(mark_x);
        synchronized (game) {
            game.notifyAll();
        }
        while(game.getTurn()!=mark_o) {
        }
        test_board_1 = CuT.cloneBoard(board);
        board = game.getBoard();
        test_board_1[row_top][col_right] = mark_x;
        assertEquals(true, Arrays.deepEquals(board,test_board_1));
    }

    /**
     * helper method for hard ai test, ran multiple times (since moves are slightly random)
     */
    @Test
    public void play_hard_test() {
        for(int i=0;i<extra_check;i++) {
            play_hard_test_helper();
        }
    }

    private void play_hard_test_helper() {
        game = new TicTacToeModel();
        //look for win move
        CuT = new PlayerAI(hard,mark_x,game);
        game.setTurn(mark_x);
        board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        board[row_bottom][col_left] = mark_x;
        board[row_bottom][col_middle] = mark_o;
        game.setBoard(board);
        thread = new Thread(CuT);
        thread.start();
        while(game.getTurn()!=mark_o) {
        }
        test_board_1 = CuT.cloneBoard(board);
        test_board_2 = CuT.cloneBoard(board);
        test_board_3 = CuT.cloneBoard(board);
        board = game.getBoard();
        test_board_1[row_middle][col_middle] = mark_x;
        test_board_2[row_top][col_left] = mark_x;
        test_board_3[row_middle][col_left] = mark_x;
        assertEquals(true, Arrays.deepEquals(board,test_board_1) ||
                Arrays.deepEquals(board,test_board_2) ||
                Arrays.deepEquals(board, test_board_3));
        CuT.stopRunning();
        synchronized (game) {
            game.notifyAll();
        }
        //look for tie move
        CuT = new PlayerAI(hard,mark_o,game);
        game.setTurn(mark_o);
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        board[row_bottom][col_left] = mark_x;
        game.setBoard(board);
        thread = new Thread(CuT);
        thread.start();
        while(game.getTurn()!=mark_x) {
        }
        test_board_1 = CuT.cloneBoard(board);
        board = game.getBoard();
        test_board_1[row_middle][col_middle] = mark_o;
        assertEquals(true, Arrays.deepEquals(board,test_board_1));
        CuT.stopRunning();
        synchronized (game) {
            game.notifyAll();
        }
    }
}
