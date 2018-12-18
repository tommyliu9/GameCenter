package fall2018.csc207.twentyfortyeight;

import org.junit.Test;

import java.util.ArrayList;

import fall2018.csc207.slidingtiles.R;

public class TwentyFortyEightBoardTest {

    private ArrayList<TwentyFortyEightTile> populateEmptyBoard(int dimensions) {
        ArrayList<TwentyFortyEightTile> tiles = new ArrayList<>(16);

        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                TwentyFortyEightTile newTile = new TwentyFortyEightTile(0);
                tiles.add(newTile);
            }
        }
        return tiles;
    }

    @Test
    public void contructorTest() {
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(4);
        assert twentyFortyEightBoard.getNumActiveTiles() == 2;
    }

    @Test
    public void getGameNameTest() {
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(4);
        assert twentyFortyEightBoard.getGameName().equals("2048");
    }

    @Test
    public void contructorGivenTilesTest() {
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        assert twentyFortyEightBoard.getNumActiveTiles() == 0;
    }

    @Test
    public void moveLeftTest() {
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        tiles.set(0, new TwentyFortyEightTile(2));
        tiles.set(1, new TwentyFortyEightTile(2));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        assert twentyFortyEightBoard.getNumActiveTiles() == 2;
        twentyFortyEightBoard.moveLeft();
        assert twentyFortyEightBoard.getNumActiveTiles() == 1;
    }

    @Test
    public void moveRightTest() {
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        tiles.set(0, new TwentyFortyEightTile(4));
        tiles.set(3, new TwentyFortyEightTile(4));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        assert twentyFortyEightBoard.getNumActiveTiles() == 2;
        twentyFortyEightBoard.moveRight();
        assert twentyFortyEightBoard.getNumActiveTiles() == 1;
    }

    @Test
    public void moveUpTest() {
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        tiles.set(0, new TwentyFortyEightTile(8));
        tiles.set(4, new TwentyFortyEightTile(8));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        assert twentyFortyEightBoard.getNumActiveTiles() == 2;
        twentyFortyEightBoard.moveUp();
        assert twentyFortyEightBoard.getNumActiveTiles() == 1;
    }

    @Test
    public void moveDownTest() {
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        tiles.set(0, new TwentyFortyEightTile(64));
        tiles.set(12, new TwentyFortyEightTile(64));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        assert twentyFortyEightBoard.getNumActiveTiles() == 2;
        twentyFortyEightBoard.moveDown();
        assert twentyFortyEightBoard.getNumActiveTiles() == 1;
    }

    @Test
    public void isOverTest() {
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(2);
        tiles.set(0, new TwentyFortyEightTile(4));
        tiles.set(1, new TwentyFortyEightTile(16));
        tiles.set(2, new TwentyFortyEightTile(32));
        tiles.set(3, new TwentyFortyEightTile(64));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 2);

        assert twentyFortyEightBoard.getNumActiveTiles() == 4;
        assert twentyFortyEightBoard.isOver();
    }

    @Test
    public void afterMoveActionsTest() {
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(4);
        twentyFortyEightBoard.moveLeft();
        twentyFortyEightBoard.afterMoveActions(true);
        assert twentyFortyEightBoard.getNumActiveTiles() == 3;
    }

    @Test
    public void undoTest() {
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(4);
        tiles.set(0, new TwentyFortyEightTile(64));
        tiles.set(12, new TwentyFortyEightTile(64));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 4);
        twentyFortyEightBoard.moveDown();
        assert twentyFortyEightBoard.getNumActiveTiles() == 1;
        twentyFortyEightBoard.undo();
        assert twentyFortyEightBoard.getNumActiveTiles() == 2;
    }

    @Test
    public void canUndoTest() {
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(4);
        assert !twentyFortyEightBoard.canUndo();
    }

    @Test
    public void iteratorTest() {
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(4);
        int row = 0, col = 0;

        for (TwentyFortyEightTile tile : twentyFortyEightBoard) {
            assert tile == twentyFortyEightBoard.getTile(row, col);
            if (col > 2) {
                col = 0;
                row++;
            } else {
                col++;
            }
        }
    }

    @Test
    public void tileBackgroundTest(){
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(2);
        tiles.set(0, new TwentyFortyEightTile(4));
        tiles.set(1, new TwentyFortyEightTile(16));
        tiles.set(2, new TwentyFortyEightTile(32));
        tiles.set(3, new TwentyFortyEightTile(64));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 2);
        assert twentyFortyEightBoard.getTile(0,0).getBackground() == R.color.four;
    }

    @Test
    public void hasMergableNeighborTest(){
        ArrayList<TwentyFortyEightTile> tiles = populateEmptyBoard(2);
        tiles.set(0, new TwentyFortyEightTile(2));
        tiles.set(1, new TwentyFortyEightTile(2));
        tiles.set(2, new TwentyFortyEightTile(2));
        tiles.set(3, new TwentyFortyEightTile(2));
        TwentyFortyEightBoard twentyFortyEightBoard = new TwentyFortyEightBoard(tiles, 2);
        assert twentyFortyEightBoard.hasMergableNeighbour(0,0);


    }
}