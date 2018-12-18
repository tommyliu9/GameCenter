package fall2018.csc207.slidingtiles;

import java.io.Serializable;

import fall2018.csc207.game.BoardController;
import fall2018.csc207.game.CoordinatePair;

/**
 * Manage the board state by processing taps.
 */
public class SlidingTilesController extends BoardController<SlidingTilesBoard> implements Serializable {

    /**
     * The dimension of the board
     */
    private final int dimensions;

    /**
     * Manage a slidingTilesBoard that has been pre-populated.
     *
     * @param slidingTilesBoard the slidingTilesBoard
     */
    SlidingTilesController(SlidingTilesBoard slidingTilesBoard) {
        super(slidingTilesBoard);
        dimensions = (int)Math.sqrt(this.gameState.numTiles());
    }

    /**
     * Processes user input and updates tile game accordingly.
     * @param position The position of the input.
     */
    @Override
    public void updateGame(int position) {
        touchMove(position);
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    @Override
    protected boolean isValidTap(int position) {
        int row = position / dimensions;
        int col = position % dimensions;
        CoordinatePair blank = gameState.findBlankTile();
        return isTileAdjacent(new CoordinatePair(row, col), blank); // The blank tile is adjacent to our tap
    }

    /**
     * Determine if two tiles are adjacent.
     * @param first The coordinates of the first tile.
     * @param second The coordinates of the second tile.
     * @return Whether the two are adjacent.
     */
    private boolean isTileAdjacent(CoordinatePair first, CoordinatePair second) {
        return first != null && second != null &&
                Math.abs(first.getRow() - second.getRow() + second.getCol() - first.getCol()) == 1;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position.
     */
    private void touchMove(int position) {
        int row = position / dimensions;
        int col = position % dimensions;

        // tiles is the blank tile, swap by calling MinesweeperBoard's swap method.
        CoordinatePair blankCoord = gameState.findBlankTile();

        // We are precisely 1 tile away from the blank tile, which means we're adjacent to it
        if (isTileAdjacent(new CoordinatePair(row, col), blankCoord))
            gameState.swapTiles(blankCoord, new CoordinatePair(row, col), true);
    }
}
