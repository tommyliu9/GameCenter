package fall2018.csc207.game;

import java.io.Serializable;

/**
 * A (row, col) coordinate pair.
 * <p>
 * We use this class instead of a Pair<Integer, Integer> because Pair isn't serializable.
 */
public class CoordinatePair implements Serializable {
    private final int row;
    private final int col;

    /**
     * Create a new Coordinate Pair
     * @param row The first coordinate
     * @param col The second coordinate
     */
    public CoordinatePair(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row value of this coordinate pair.
     *
     * @return The row value of this coordinate pair.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the col value of this coordinate pair.
     *
     * @return The col value of this coordinate pair.
     */
    public int getCol() {
        return col;
    }

    /**
     * Determines if CoordinatePair is equal to o.
     *
     * @param o The object to est.
     * @return Whether this is equal to o.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatePair other = (CoordinatePair) o;
        return row == other.row &&
                col == other.col;
    }
}
