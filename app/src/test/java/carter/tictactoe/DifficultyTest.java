package carter.tictactoe;

import org.junit.Test;

import static org.junit.Assert.*;

public class DifficultyTest {
    Difficulty easy = Difficulty.EASY;
    Difficulty medium = Difficulty.MEDIUM;
    Difficulty hard = Difficulty.HARD;

    @Test
    public void easy_test() {
        assertEquals(Difficulty.EASY, easy);
    }

    @Test
    public void medium_test() {
        assertEquals(Difficulty.MEDIUM, medium);
    }

    @Test
    public void hard_test() {
        assertEquals(Difficulty.HARD, hard);
    }
}