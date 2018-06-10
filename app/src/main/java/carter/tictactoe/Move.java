package carter.tictactoe;

/**
 * Used to indicate what kind of move was made on the tic tac toe board.
 */
public class Move {
    private Coordinates coordinates;
    private Mark mark;

    /**Constructor for move which needs coordinates and a mark.
     * @param coordinates Coordinates corresponding to where the move is to be made.
     * @param mark The mark the move will be made with.
     */
    Move(Coordinates coordinates, Mark mark) {
        this.coordinates = coordinates;
        this.mark = mark;
    }

    /**
     * Getter for the coordinates.
     * @return The coordinates.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Getter for the mark.
     * @return The mark.
     */
    public Mark getMark() {
        return mark;
    }
}
