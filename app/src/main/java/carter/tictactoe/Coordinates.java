package carter.tictactoe;

/**
 * Class used to send coordinates.
 */
public class Coordinates {
    private int row;
    private int col;

    /**
     * Constructor, sets the row and column for the coordinates.
     * @param row the row of the coordinates.
     * @param col the column of the coordinates.
     */
    Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Getter function for the row of the coordinates.
     * @return the row of the coordinates.
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter for the column.
     * @return the column of the coordinates.
     */
    public int getCol() {
        return col;
    }
}
