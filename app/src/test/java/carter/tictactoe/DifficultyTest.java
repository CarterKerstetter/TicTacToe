package carter.tictactoe;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for the Difficulty enumeration
 */
public class DifficultyTest {
    Difficulty easy = Difficulty.EASY;
    Difficulty medium = Difficulty.MEDIUM;
    Difficulty hard = Difficulty.HARD;

    /**
     * test for the easy enumeration
     */
    @Test
    public void easy_test() {
        assertEquals(Difficulty.EASY, easy);
    }

    /**
     * test for the medium enumeration
     */
    @Test
    public void medium_test() {
        assertEquals(Difficulty.MEDIUM, medium);
    }

    /**
     * test for the hard enumeration
     */
    @Test
    public void hard_test() {
        assertEquals(Difficulty.HARD, hard);
    }
}