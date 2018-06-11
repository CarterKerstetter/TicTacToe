package carter.tictactoe;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for Mark enumeration
 */
public class MarkTest {
    Mark x = Mark.X;
    Mark o = Mark.O;
    Mark blank = Mark.BLANK;

    /**
     * test for the x enumeration
     */
    @Test
    public void x_test() {
        assertEquals(Mark.X, x);
    }

    /**
     * test for the o enumeration
     */
    @Test
    public void o_test() {
        assertEquals(Mark.O, o);
    }

    /**
     * test for the blank enumeration
     */
    @Test
    public void blank_test() {
        assertEquals(Mark.BLANK, blank);
    }
}