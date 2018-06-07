package carter.tictactoe;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class MoveTest {
    private int row = 3;
    private int col = 8;
    private Move CuT;
    private Coordinates coordinates;
    private Mark mark = Mark.X;

    @Before
    public void Setup() {
        coordinates = Mockito.mock(Coordinates.class);
        Mockito.when(coordinates.getRow()).thenReturn(row);
        Mockito.when(coordinates.getCol()).thenReturn(col);
        CuT = new Move(coordinates, mark);
    }

    @Test
    public void get_coordinates_test() {
        Coordinates test_coords = CuT.getCoordinates();
        assertEquals(row, test_coords.getRow());
        assertEquals(col, test_coords.getCol());
    }

    @Test
    public void get_mark_test() {
        Mark test_mark = CuT.getMark();
        assertEquals(mark, test_mark);
    }
}