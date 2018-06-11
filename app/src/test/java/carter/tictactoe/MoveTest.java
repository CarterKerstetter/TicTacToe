package carter.tictactoe;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;

/**
 * Unit test for the move class
 */
public class MoveTest {
    private int row = 3;
    private int col = 8;
    private Move CuT;
    private Coordinates coordinates;
    private Mark mark = Mark.X;

    /**
     * setup method
     */
    @Before
    public void Setup() {
        coordinates = Mockito.mock(Coordinates.class);
        Mockito.when(coordinates.getRow()).thenReturn(row);
        Mockito.when(coordinates.getCol()).thenReturn(col);
        CuT = new Move(coordinates, mark);
    }

    /**
     * test for the getCoordinates method
     */
    @Test
    public void get_coordinates_test() {
        coordinates = CuT.getCoordinates();
        assertEquals(row, coordinates.getRow());
        assertEquals(col, coordinates.getCol());
    }

    /**
     * test for the getMark method
     */
    @Test
    public void get_mark_test() {
        Mark test_mark = CuT.getMark();
        assertEquals(mark, test_mark);
    }
}