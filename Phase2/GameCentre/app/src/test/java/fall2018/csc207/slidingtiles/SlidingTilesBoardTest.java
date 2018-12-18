package fall2018.csc207.slidingtiles;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc207.game.CoordinatePair;

public class SlidingTilesBoardTest {
    private SlidingTilesBoard board3x3;
    private SlidingTilesBoard board4x4;
    private SlidingTilesBoard board5x5;

    /**
     * Setups the different boards (solved) for each test.
     */
    @Before
    public void setup() {
        board3x3 = getSolvedBoard(3);
        board4x4 = getSolvedBoard(4);
        board5x5 = getSolvedBoard(5);
    }

    private SlidingTilesBoard getSolvedBoard(int dimensions) {
        List<List<SlidingTilesTile>> slidingTilesTiles = new ArrayList<>();
        for (int i = 0; i != dimensions; i++) {
            slidingTilesTiles.add(new ArrayList<SlidingTilesTile>());
            for (int j = 0; j != dimensions; j++) {
                slidingTilesTiles.get(i).add(new SlidingTilesTile(i * dimensions + j));
            }
        }
        slidingTilesTiles.get(dimensions - 1).set(
                dimensions - 1,
                new SlidingTilesTile(dimensions * dimensions, R.drawable.blanktile));
        return new SlidingTilesBoard(slidingTilesTiles);
    }

    /**
     * Determines if we can find the blank tile correctly.
     */
    @Test
    public void testFindBlankTile() {
        assert board3x3.findBlankTile().equals(new CoordinatePair(2, 2));
        assert board4x4.findBlankTile().equals(new CoordinatePair(3, 3));
        assert board5x5.findBlankTile().equals(new CoordinatePair(4, 4));
        board3x3.swapTiles(new CoordinatePair(2, 2), new CoordinatePair(1, 2), true);
        assert board3x3.findBlankTile().equals(new CoordinatePair(1, 2));
    }

    /**
     * Determines if numTiles returns the right values.
     */
    @Test
    public void testNumTiles() {
        assert board3x3.numTiles() == 9;
        assert board4x4.numTiles() == 16;
        assert board5x5.numTiles() == 25;
    }

    /**
     * Tests if tiles are swapped correctly.
     */
    @Test
    public void testSwapTiles() {
        board3x3.swapTiles(new CoordinatePair(2, 2), new CoordinatePair(1, 2), true);
        assert board3x3.getTile(1, 2).getId() == 9;
    }

    /**
     * Tests if undo correctly undos the board.
     */
    @Test
    public void testUndo() {
        board3x3.setMaxUndos(1);
        board3x3.swapTiles(new CoordinatePair(2, 2), new CoordinatePair(1, 2), true);
        assert board3x3.canUndo();

        board3x3.undo();
        assert board3x3.getTile(2, 2).getId() == 9;
        assert board3x3.getTile(1, 2).getId() == 6;
        assert !board3x3.canUndo();
    }

    /**
     * Tests if isOver correctly detects a finished game state.
     */
    @Test
    public void testIsOver() {
        assert board3x3.isOver();
        board3x3.swapTiles(new CoordinatePair(0, 1), new CoordinatePair(1, 1), true);
        assert !board3x3.isOver();
        board3x3.swapTiles(new CoordinatePair(0, 1), new CoordinatePair(1, 1), true);
        assert board3x3.isOver();
    }

    /**
     * Tests if different board sizes give different names.
     */
    @Test
    public void testGetGameName() {
        assert board3x3.getGameName().equals("Sliding Tiles 3x3");
        assert board4x4.getGameName().equals("Sliding Tiles 4x4");
        assert board5x5.getGameName().equals("Sliding Tiles 5x5");
    }

    /**
     * Tests if the iterator iterates correctly over the boards.
     */
    @Test
    public void testIterator() {
        int row = 0;
        int col = 0;
        for (SlidingTilesTile tile : board3x3) {
            assert tile.getId() == board3x3.getTile(row, col).getId();
            if (col > 1) {
                col = 0;
                row++;
            } else {
                col++;
            }
        }
        row = 0;
        col = 0;
        for (SlidingTilesTile tile : board4x4) {
            assert tile.getId() == board4x4.getTile(row, col).getId();
            if (col > 2) {
                col = 0;
                row++;
            } else {
                col++;
            }
        }
        row = 0;
        col = 0;
        for (SlidingTilesTile tile : board5x5) {
            assert tile.getId() == board5x5.getTile(row, col).getId();
            if (col > 3) {
                col = 0;
                row++;
            } else {
                col++;
            }
        }
    }

    @Test
    public void testSetMaxUndos() {
        board3x3.setMaxUndos(0);
        board3x3.swapTiles(new CoordinatePair(0, 1), new CoordinatePair(1, 1), true);
        assert !board3x3.canUndo();
        board3x3.setMaxUndos(-1);
        board3x3.swapTiles(new CoordinatePair(0, 1), new CoordinatePair(1, 1), true);
        assert board3x3.canUndo();
    }
}
