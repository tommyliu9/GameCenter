package fall2018.csc207.minesweeper;

import org.junit.Test;

public class MinesweeperControllerTest {

    private int [][] generateBoard(){
        return new int[][]{
                {1, -1, 1, 0},
                {0, 2, 1, 1},
                {0, 1, -1, 1},
                {0, 1, 1, 1}
        };
    }

    private int [][] bombBoard(){
        return new int[][]{
                {3, -1, -1, -1},
                {-1, -1, -1, -1},
                {-1, -1, -1, -1},
                {-1, -1, -1, -1}
        };
    }

    @Test
    public void updateGameTestBomb(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generateBoard());
        MinesweeperController controller = new MinesweeperController(minesweeperBoard);
        //Want to hit tile[2][2]
        controller.updateGame(10);
        assert minesweeperBoard.getNumRevealedTiles() == 17;
    }

    @Test
    public void updateGameTestNullTile(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generateBoard());
        MinesweeperController controller = new MinesweeperController(minesweeperBoard);
        assert controller.isValidTap(4);
        //Hit tile[2][1]
        controller.updateGame(4);
        assert  minesweeperBoard.getNumRevealedTiles() == 8;
    }

    @Test
    public void flagTileTest(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generateBoard());
        MinesweeperController controller = new MinesweeperController(minesweeperBoard);
        controller.flagTile(0);
        MinesweeperTile tile = minesweeperBoard.getTile(0,0);
        assert tile.isFlagged();
    }

    @Test
    public void loseGameTest(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(bombBoard());
        MinesweeperController controller = new MinesweeperController(minesweeperBoard);
        assert minesweeperBoard.getTile(1,1).getId() == MinesweeperTile.BOMB;
        controller.updateGame(0);
        controller.updateGame(1);
        assert minesweeperBoard.isGameLost();
    }
}
