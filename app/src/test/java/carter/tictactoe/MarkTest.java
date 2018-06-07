package carter.tictactoe;

import org.junit.Test;

import static org.junit.Assert.*;

public class MarkTest {
    Mark x = Mark.X;
    Mark o = Mark.O;
    Mark blank = Mark.BLANK;

    @Test
    public void x_test() {
        assertEquals(Mark.X, x);
    }

    @Test
    public void o_test() {
        assertEquals(Mark.O, o);
    }

    @Test
    public void blank_test() {
        assertEquals(Mark.BLANK, blank);
    }
}