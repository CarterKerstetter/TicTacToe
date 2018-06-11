package carter.tictactoe;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;

/**
 * Unit test for the TicTacToeModel class
 */
public class TicTacToeModelTest {
    private int row_top = 0;
    private int row_middle = 1;
    private int row_bottom = 2;
    private int col_left = 0;
    private int col_middle = 1;
    private int col_right = 2;
    private Coordinates coordinates = Mockito.mock(Coordinates.class);;
    private Move move = Mockito.mock(Move.class);
    private TicTacToeModel CuT;
    private Mark mark_x = Mark.X;
    private Mark mark_o = Mark.O;
    private Mark mark_blank = Mark.BLANK;
    private Mark[][] board;
    private Mark[][] test_board;

    /**
     * test for the constructor
     */
    @Test
    public void constructor_test() {
        new TicTacToeModel();
    }

    /**
     * test for the getTurn method
     */
    @Test
    public void get_turn_test() {
        CuT = new TicTacToeModel();
        assertEquals(mark_x, CuT.getTurn());
        Mockito.when(coordinates.getRow()).thenReturn(row_top);
        Mockito.when(coordinates.getCol()).thenReturn(col_left);
        Mockito.when(move.getCoordinates()).thenReturn(coordinates);
        Mockito.when(move.getMark()).thenReturn(mark_o);
        //o moves on x's turn
        CuT.makeMove(move);
        assertEquals(mark_x, CuT.getTurn());
        Mockito.when(move.getMark()).thenReturn(mark_x);
        //normal move
        CuT.makeMove(move);
        assertEquals(mark_o, CuT.getTurn());
        Mockito.when(coordinates.getCol()).thenReturn(col_middle);
        Mockito.when(move.getCoordinates()).thenReturn(coordinates);
        //wrong person makes a move
        CuT.makeMove(move);
        assertEquals(mark_o, CuT.getTurn());
        Mockito.when(move.getMark()).thenReturn(mark_o);
        Mockito.when(coordinates.getCol()).thenReturn(col_left);
        Mockito.when(move.getCoordinates()).thenReturn(coordinates);
        //move on an already occupied space
        CuT.makeMove(move);
        assertEquals(mark_o, CuT.getTurn());
        Mockito.when(coordinates.getCol()).thenReturn(col_middle);
        Mockito.when(move.getCoordinates()).thenReturn(coordinates);
        //correct move
        CuT.makeMove(move);
        assertEquals(mark_x, CuT.getTurn());
    }

    /**
     * test for the setTurn method
     */
    @Test
    public void set_turn_test() {
        CuT = new TicTacToeModel();
        CuT.setTurn(mark_x);
        assertEquals(mark_x,CuT.getTurn());
        CuT.setTurn(mark_o);
        assertEquals(mark_o,CuT.getTurn());
        CuT.setTurn(mark_blank);
        assertEquals(mark_blank,CuT.getTurn());
    }

    /**
     * test for the getBoard method
     */
    @Test
    public void get_board_test() {
        CuT = new TicTacToeModel();
        board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        CuT.setBoard(board);
        test_board = CuT.getBoard();
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(board[row][col], test_board[row][col]);
            }
        }
        board[row_middle][col_left] = mark_x;
        test_board[row_middle][col_left] = mark_x;
        CuT.setBoard(board);
        test_board = CuT.getBoard();
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(board[row][col], test_board[row][col]);
            }
        }
        board[row_bottom][col_middle] = mark_o;
        test_board[row_bottom][col_middle] = mark_o;
        CuT.setBoard(board);
        test_board = CuT.getBoard();
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(board[row][col], test_board[row][col]);
            }
        }
    }

    /**
     * test for the setTurn method
     */
    @Test
    public void set_board_test() {
        CuT = new TicTacToeModel();
        board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        CuT.setBoard(board);
        test_board = CuT.getBoard();
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(board[row][col], test_board[row][col]);
            }
        }
        board[row_middle][col_left] = mark_x;
        test_board[row_middle][col_left] = mark_x;
        CuT.setBoard(board);
        test_board = CuT.getBoard();
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(board[row][col], test_board[row][col]);
            }
        }
        board[row_bottom][col_middle] = mark_o;
        test_board[row_bottom][col_middle] = mark_o;
        CuT.setBoard(board);
        test_board = CuT.getBoard();
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(board[row][col], test_board[row][col]);
            }
        }
    }

    /**
     * test for the newGame method
     */
    @Test
    public void new_game_test() {
        CuT = new TicTacToeModel();
        board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_x;
            }
        }
        CuT.setBoard(board);
        CuT.newGame();
        board = CuT.getBoard();
        assertEquals(TicTacToeModel.BOARD_SIZE, board.length);
        for(int i=0;i<TicTacToeModel.BOARD_SIZE;i++) {
            assertEquals(TicTacToeModel.BOARD_SIZE, board[i].length);
        }
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(mark_blank, board[row][col]);
            }
        }
    }

    /**
     * test for the makeMove method
     */
    @Test
    public void make_move_test() {
        CuT = new TicTacToeModel();
        assertEquals(mark_x, CuT.getTurn());
        Mockito.when(coordinates.getRow()).thenReturn(row_bottom);
        Mockito.when(coordinates.getCol()).thenReturn(col_right);
        Mockito.when(move.getCoordinates()).thenReturn(coordinates);
        Mockito.when(move.getMark()).thenReturn(mark_o);
        //o moves on x's turn
        CuT.makeMove(move);
        assertEquals(mark_x, CuT.getTurn());
        board = CuT.getBoard();
        assertEquals(TicTacToeModel.BOARD_SIZE, board.length);
        for(int i=0;i<TicTacToeModel.BOARD_SIZE;i++) {
            assertEquals(TicTacToeModel.BOARD_SIZE, board[i].length);
        }
        test_board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                test_board[row][col] = mark_blank;
            }
        }
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(test_board[row][col], board[row][col]);
            }
        }
        Mockito.when(move.getMark()).thenReturn(mark_x);
        //normal move
        CuT.makeMove(move);
        assertEquals(mark_o, CuT.getTurn());
        board = CuT.getBoard();
        assertEquals(TicTacToeModel.BOARD_SIZE, board.length);
        for(int i=0;i<TicTacToeModel.BOARD_SIZE;i++) {
            assertEquals(TicTacToeModel.BOARD_SIZE, board[i].length);
        }
        test_board[row_bottom][col_right] = mark_x;
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(test_board[row][col], board[row][col]);
            }
        }
        Mockito.when(coordinates.getCol()).thenReturn(col_middle);
        Mockito.when(move.getCoordinates()).thenReturn(coordinates);
        //wrong person makes a move
        CuT.makeMove(move);
        assertEquals(mark_o, CuT.getTurn());
        board = CuT.getBoard();
        assertEquals(TicTacToeModel.BOARD_SIZE, board.length);
        for(int i=0;i<TicTacToeModel.BOARD_SIZE;i++) {
            assertEquals(TicTacToeModel.BOARD_SIZE, board[i].length);
        }
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(test_board[row][col], board[row][col]);
            }
        }
        Mockito.when(move.getMark()).thenReturn(mark_o);
        Mockito.when(coordinates.getCol()).thenReturn(col_right);
        Mockito.when(move.getCoordinates()).thenReturn(coordinates);
        //move on an already occupied space
        CuT.makeMove(move);
        assertEquals(mark_o, CuT.getTurn());
        board = CuT.getBoard();
        assertEquals(TicTacToeModel.BOARD_SIZE, board.length);
        for(int i=0;i<TicTacToeModel.BOARD_SIZE;i++) {
            assertEquals(TicTacToeModel.BOARD_SIZE, board[i].length);
        }
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(test_board[row][col], board[row][col]);
            }
        }
        Mockito.when(coordinates.getCol()).thenReturn(col_middle);
        Mockito.when(move.getCoordinates()).thenReturn(coordinates);
        //correct move
        CuT.makeMove(move);
        assertEquals(mark_x, CuT.getTurn());
        board = CuT.getBoard();
        assertEquals(TicTacToeModel.BOARD_SIZE, board.length);
        for(int i=0;i<TicTacToeModel.BOARD_SIZE;i++) {
            assertEquals(TicTacToeModel.BOARD_SIZE, board[i].length);
        }
        test_board[row_bottom][col_middle] = mark_o;
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                assertEquals(test_board[row][col], board[row][col]);
            }
        }
    }

    /**
     * test for the gameCompleted method
     */
    @Test
    public void game_completed_test() {
        CuT = new TicTacToeModel();
        board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        CuT.setBoard(board);
        assertEquals(false,CuT.gameCompleted());
        board[row_top][col_left] = mark_x;
        CuT.setBoard(board);
        assertEquals(false,CuT.gameCompleted());
        board[row_top][col_middle] = mark_x;
        board[row_top][col_right] = mark_x;
        //x wins across top
        CuT.setBoard(board);
        assertEquals(true,CuT.gameCompleted());
        board[row_top][col_left] = mark_o;
        CuT.setBoard(board);
        assertEquals(false,CuT.gameCompleted());
        board[row_middle][col_left] = mark_o;
        board[row_bottom][col_left] = mark_o;
        //o wins across left side
        CuT.setBoard(board);
        CuT.setBoard(board);
        assertEquals(true,CuT.gameCompleted());
        board[row_bottom][col_left] = mark_x;
        CuT.setBoard(board);
        assertEquals(false,CuT.gameCompleted());
        board[row_middle][col_middle] = mark_x;
        //x wins diagonal
        CuT.setBoard(board);
        assertEquals(true,CuT.gameCompleted());
        board[row_middle][col_middle] = mark_o;
        CuT.setBoard(board);
        assertEquals(false,CuT.gameCompleted());
        board[row_bottom][col_right] = mark_o;
        //o wins diagonal
        CuT.setBoard(board);
        assertEquals(true,CuT.gameCompleted());
        board[row_bottom][col_right] = mark_x;
        board[row_bottom][col_middle] = mark_o;
        CuT.setBoard(board);
        assertEquals(false,CuT.gameCompleted());
        board[row_top][col_right] = mark_o;
        CuT.setBoard(board);
        assertEquals(false,CuT.gameCompleted());
        board[row_middle][col_right] = mark_x;
        //board filled tie game
        CuT.setBoard(board);
        assertEquals(true,CuT.gameCompleted());
    }

    /**
     * test for the getWinner method
     */
    @Test
    public void get_winner_test() {
        CuT = new TicTacToeModel();
        board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        CuT.setBoard(board);
        assertEquals(mark_blank,CuT.getWinner());
        board[row_top][col_left] = mark_o;
        CuT.setBoard(board);
        assertEquals(mark_blank,CuT.getWinner());
        board[row_top][col_middle] = mark_o;
        board[row_top][col_right] = mark_o;
        //o wins across top
        CuT.setBoard(board);
        assertEquals(mark_o,CuT.getWinner());
        board[row_top][col_left] = mark_x;
        CuT.setBoard(board);
        assertEquals(mark_blank,CuT.getWinner());
        board[row_middle][col_left] = mark_x;
        board[row_bottom][col_left] = mark_x;
        //x wins across left side
        CuT.setBoard(board);
        CuT.setBoard(board);
        assertEquals(mark_x,CuT.getWinner());
        board[row_bottom][col_left] = mark_o;
        CuT.setBoard(board);
        assertEquals(mark_blank,CuT.getWinner());
        board[row_middle][col_middle] = mark_o;
        //o wins diagonal
        CuT.setBoard(board);
        assertEquals(mark_o,CuT.getWinner());
        board[row_middle][col_middle] = mark_x;
        CuT.setBoard(board);
        assertEquals(mark_blank,CuT.getWinner());
        board[row_bottom][col_right] = mark_x;
        //x wins diagonal
        CuT.setBoard(board);
        assertEquals(mark_x,CuT.getWinner());
        board[row_bottom][col_right] = mark_o;
        board[row_bottom][col_middle] = mark_x;
        CuT.setBoard(board);
        assertEquals(mark_blank,CuT.getWinner());
        board[row_top][col_right] = mark_x;
        CuT.setBoard(board);
        assertEquals(mark_blank,CuT.getWinner());
        board[row_middle][col_right] = mark_o;
        //board filled tie game
        CuT.setBoard(board);
        assertEquals(mark_blank,CuT.getWinner());
    }

    /**
     * test for the getWinPath method
     */
    @Test
    public void get_win_path_test() {
        CuT = new TicTacToeModel();
        board = new Mark[3][3];
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col = 0;col<TicTacToeModel.BOARD_SIZE;col++) {
                board[row][col] = mark_blank;
            }
        }
        CuT.setBoard(board);
        assertEquals(8,CuT.getWinPath());
        board[row_top][col_left] = mark_x;
        board[row_top][col_middle] = mark_x;
        board[row_top][col_right] = mark_x;
        CuT.setBoard(board);
        assertEquals(0,CuT.getWinPath());
        board[row_top][col_right] = mark_o;
        board[row_middle][col_left] = mark_o;
        board[row_middle][col_middle] = mark_o;
        board[row_middle][col_right] = mark_o;
        CuT.setBoard(board);
        assertEquals(1,CuT.getWinPath());
        board[row_middle][col_right] = mark_x;
        board[row_bottom][col_left] = mark_x;
        board[row_bottom][col_middle] = mark_x;
        board[row_bottom][col_right] = mark_x;
        CuT.setBoard(board);
        assertEquals(2,CuT.getWinPath());
        board[row_bottom][col_middle] = mark_o;
        board[row_middle][col_left] = mark_x;
        CuT.setBoard(board);
        assertEquals(3,CuT.getWinPath());
        board[row_middle][col_left] = mark_o;
        board[row_top][col_middle] = mark_o;
        CuT.setBoard(board);
        assertEquals(4,CuT.getWinPath());
        board[row_bottom][col_left] = mark_o;
        board[row_bottom][col_middle] = mark_x;
        board[row_top][col_right] = mark_x;
        CuT.setBoard(board);
        assertEquals(5,CuT.getWinPath());
        board[row_top][col_right] = mark_o;
        board[row_middle][col_middle] = mark_x;
        CuT.setBoard(board);
        assertEquals(6,CuT.getWinPath());
        board[row_middle][col_middle] = mark_o;
        CuT.setBoard(board);
        assertEquals(7,CuT.getWinPath());
        board[row_middle][col_middle] = mark_blank;
        CuT.setBoard(board);
        assertEquals(8,CuT.getWinPath());
    }
}
