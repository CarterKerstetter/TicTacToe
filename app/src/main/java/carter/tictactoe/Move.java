/**
 * author: Carter Kerstetter
 */

package carter.tictactoe;

public class Move {
    private Coordinates coordinates;
    private Mark mark;

    public Move(Coordinates coordinates, Mark mark) {
        this.coordinates = coordinates;
        this.mark = mark;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Mark getMark() {
        return mark;
    }
}
