package fall2018.csc207.minesweeper;

import org.junit.Test;

import fall2018.csc207.slidingtiles.R;

public class MinesweeperTileTest {
    private int [][] generateBoard(){
        return new int[][]{
                {1, -1, 1, 0},
                {0, 2, 1, 1},
                {0, 1, -1, 1},
                {0, 1, 1, 1}
        };
    }

    @Test
    public void getIdTest(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generateBoard());
        assert minesweeperBoard.getTile(0,0).getId() == 1;
    }

    @Test
    public void getBackgroundTest(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generateBoard());
        minesweeperBoard.revealTile(0,0);
        assert minesweeperBoard.getTile(0,0).getBackground() == R.drawable.tile_1;
    }

    @Test
    public void toStringTest(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generateBoard());
        assert minesweeperBoard.getTile(0,0).toString().equals("1");
    }

    @Test
    public void setFlaggedTest(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generateBoard());
        MinesweeperTile tile = minesweeperBoard.getTile(0,0);
        tile.setFlagged(true);
        tile.setRevealed(false);
        tile.setFlagged(false);
        assert tile.getBackground() == R.drawable.btile;
    }
}
