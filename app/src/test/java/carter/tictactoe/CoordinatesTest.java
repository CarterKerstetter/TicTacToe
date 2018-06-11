package carter.tictactoe;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for the Coordinates class
 */
public class CoordinatesTest {
    int row = 7;
    int col = 2;
    Coordinates CuT;

    /**
     * Setup for the Coordinates
     */
    @Before
    public void Setup() {
        CuT = new Coordinates(row, col);
    }

    /**
     * test for the getRow method
     */
    @Test
    public void row_test() {
        assertEquals(row, CuT.getRow());
    }

    /**
     * test for the getCol method
     */
    @Test
    public void col_test() {
        assertEquals(col, CuT.getCol());
    }
}