package carter.tictactoe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinatesTest {
    int row = 7;
    int col = 2;
    Coordinates CuT;

    @Before
    public void Setup() {
        CuT = new Coordinates(row, col);
    }

    @Test
    public void row_test() {
        assertEquals(row, CuT.getRow());
    }

    @Test
    public void col_test() {
        assertEquals(col, CuT.getCol());
    }
}