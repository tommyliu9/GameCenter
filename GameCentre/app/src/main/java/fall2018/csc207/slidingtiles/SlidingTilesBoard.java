package fall2018.csc207.slidingtiles;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fall2018.csc207.game.CoordinatePair;
import fall2018.csc207.game.GameState;

/**
 * The sliding tiles board.
 */
public class SlidingTilesBoard extends GameState implements Iterable<SlidingTilesTile> {
    /**
     * The tiles on the board in row-major order.
     */
    private final List<List<SlidingTilesTile>> tiles;

    /**
     * The current maximum allowed undos. This is decremented when we undo, and incremented when we
     * make a canMove.
     */
    private int allowedUndos = getMaxUndos();

    /**
     * A stack of previous moved tiles. We only keep track of 1 tile's location because we know the
     * other must be blank.
     * <p>
     * This is transient because it cannot be serialized by Java (for some reason).
     */
    private final Stack<CoordinatePair> prevMoves = new Stack<>();


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles for the board
     */
    SlidingTilesBoard(List<List<SlidingTilesTile>> tiles) {
        // We start the score at 100, where 100 is a perfect (and unobtainable) score.
        setScore(100);
        this.tiles = tiles;
    }


    /**
     * Get the coordinate pair of the blank tile.
     *
     * @return The coordinate pair of the blank tile. first is row, second is col.
     */
    public CoordinatePair findBlankTile() {
        int blankId = this.numTiles();
        Iterator<SlidingTilesTile> iter = this.iterator();

        int blankRow = 0;
        int blankCol = 0;

        // Find the blank tile's row and col
        // For the loops, we assume the board is square.
        for (int rowIndex = 0; rowIndex < getDimensions(); rowIndex++) {
            for (int colIndex = 0; colIndex < getDimensions(); colIndex++) {
                if (iter.hasNext() && iter.next().getId() == blankId) {
                    blankRow = rowIndex;
                    blankCol = colIndex;
                }
            }
        }
        return new CoordinatePair(blankRow, blankCol);
    }

    /**
     * Sets the maximum number of undos. Overriden because we need to reset allowedUndos.
     *
     * @param maxUndos The number of max undos.
     */
    @Override
    public void setMaxUndos(int maxUndos) {
        super.setMaxUndos(maxUndos);
        allowedUndos = maxUndos;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return tiles.size() * tiles.get(0).size();
    }

    /**
     * Returns the dimensions of the board (assuming the board is square)
     *
     * @return The dimensions of the board.
     */
    private int getDimensions() {
        return tiles.size();
    }

    /**
     * Return the tile at coords.
     *
     * @param coords The coordinates of the tile to retrieve.
     * @return The tile at coords.
     */
    private SlidingTilesTile getTile(CoordinatePair coords) {
        return getTile(coords.getRow(), coords.getCol());
    }

    /**
     * Return the tile at (row, col).
     *
     * @param row The row of the tile to retrieve.
     * @param col The column of the tile to retrieve.
     * @return The tile at (row, col).
     */
    public SlidingTilesTile getTile(int row, int col) {
        return tiles.get(row).get(col);
    }

    /**
     * Swap first and second.
     *
     * @param f              The first tile to swap.
     * @param s              The tile to swap first with.
     * @param addToPrevMoves If we should add this canMove onto the stack holding previous moves.
     */
    public void swapTiles(CoordinatePair f, CoordinatePair s, boolean addToPrevMoves) {

        this.score -= 1;
        SlidingTilesTile first = getTile(f);
        SlidingTilesTile second = getTile(s);
        tiles.get(f.getRow()).set(f.getCol(), new SlidingTilesTile(second.getId(), second.getBackground()));
        tiles.get(s.getRow()).set(s.getCol(), new SlidingTilesTile(first.getId(), first.getBackground()));

        // We may not want this canMove to be recorded.
        if (addToPrevMoves) {
            // We want to add the non-blank tile to the stack.
            if (first.getId() == numTiles()) prevMoves.add(f);
            else prevMoves.add(s);

            // We made another canMove, so we can continue undoing.
            if (allowedUndos < getMaxUndos() && 0 <= allowedUndos) {
                allowedUndos++;
            }
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Returns a iterator for board.
     *
     * @return An iterator for board.
     */
    @NonNull
    @Override
    public Iterator<SlidingTilesTile> iterator() {
        return new BoardIterator();
    }

    /**
     * Revert to a past game state. This call always undos a canMove. To check if we're allowed to
     * undo that canMove, call canUndo().
     */
    @Override
    public void undo() {
        // If allowedUndos is less than 0, then we are allowing infinite undos.
        if (allowedUndos > 0)
            allowedUndos--;

        CoordinatePair prevMove = prevMoves.pop();
        CoordinatePair blankLocation = findBlankTile();
        if (blankLocation != null)
            swapTiles(prevMove, blankLocation, false);
    }

    /**
     * Determines whether w     e can undo a canMove.
     *
     * @return Whether we can undo a canMove.
     */
    @Override
    public boolean canUndo() {
        return !prevMoves.isEmpty() && allowedUndos != 0;
    }

    /**
     * Determines if the tiles are in Row-Major order.
     *
     * @return True if the tiles are in order, False otherwise.
     */
    public boolean isOver() {
        Iterator<SlidingTilesTile> iter = iterator();
        int counter = 1;
        while (iter.hasNext()) {
            int id = iter.next().getId();
            if (counter != id) {
                return false;
            }
            counter++;
        }
        return true;
    }

    @Override
    public String getGameName() {
        return "Sliding Tiles " + getDimensions() + "x" + getDimensions();
    }

    /**
     * A custom MinesweeperBoard iterator
     */
    private class BoardIterator implements Iterator<SlidingTilesTile> {
        /**
         * index indicates the position of the board
         */
        private int index;

        @Override
        public boolean hasNext() {
            return index != numTiles();
        }

        @Override
        public SlidingTilesTile next() {
            int col = index % getDimensions();
            int row = index / getDimensions();
            index++;

            return getTile(new CoordinatePair(row, col));
        }
    }
}
