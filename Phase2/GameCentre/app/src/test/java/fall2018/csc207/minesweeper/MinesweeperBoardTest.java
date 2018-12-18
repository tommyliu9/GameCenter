package fall2018.csc207.minesweeper;

import org.junit.Test;

/*
    Test Class for all public methods in MinesweeperBoard
 */
public class MinesweeperBoardTest {


    private int [][] generate4x4Board(){
        return new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 1},
                {0, 1, -1, 1}
        };
    }

    private int [][] bombBoard(){
        return new int[][]{
                {-1, -1, -1, -1},
                {-1, -1, -1, -1},
                {-1, -1, -1, -1},
                {-1, -1, -1, -1}
        };
    }

    private int [][] emptyBoard(){
        return new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
    }
    private int [][] diagBoard(){
        return new int[][]{
                {0, -1, 0, 0},
                {-1, 0, -1, 0},
                {0, -1, 0, 0},
                {0, 0, 0, 0},
        };
    }

    @Test
    public void testGenerateBoard() {
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        assert minesweeperBoard.getNumMines() == 1;
    }
    @Test
    public void testGenerateBoardAuto() {
        MinesweeperBoard newMinesweeperBoard = new MinesweeperBoard(5, .3);
        assert newMinesweeperBoard.getNumMines() <= 13;
        assert newMinesweeperBoard.getDimensions() == 5;

    }
    @Test
    public void testRevealTiles(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.revealSurroundingBlanks(0,0);
        int num = 0;
        for (int i = 0; i < minesweeperBoard.getDimensions(); i++){
            for(int b = 0; b < minesweeperBoard.getDimensions(); b++){
                if (minesweeperBoard.getTile(i,b).isRevealed()){
                    num++;
                }
            }
        }
        assert num == 14;
    }

    @Test
    public void testendGame(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.endGame(3, 2);
        assert minesweeperBoard.getNumRevealedTiles() == 1;

    }

    @Test
    public void testisOver(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.revealSurroundingBlanks(0,0);
        minesweeperBoard.revealTile(3,2);
        assert minesweeperBoard.isOver();
    }
    @Test
    public void testisOverWithFlag(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.revealSurroundingBlanks(0,0);
        minesweeperBoard.revealTile(3,3);
        minesweeperBoard.flagTile(3,2);
        assert minesweeperBoard.isOver();
    }
    @Test
    public void testGetName(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        assert minesweeperBoard.getGameName().equals("Minesweeper 4x4");
    }
    @Test
    public void testDecrementScore(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(generate4x4Board());
        minesweeperBoard.decrementScore();
        minesweeperBoard.decrementScore();
        assert minesweeperBoard.getScore() == (100000-2);
    }

    @Test
    public void testdeleteBomb(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(bombBoard());
        minesweeperBoard.deleteBomb(2, 2);
        assert minesweeperBoard.getNumMines() == 15;
    }

    @Test
    public void canUndoTest(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(bombBoard());
        minesweeperBoard.undo();
        assert !minesweeperBoard.canUndo();
    }

    @Test
    public void revealSurroundingTest(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(emptyBoard());
        minesweeperBoard.revealTile(1,1);
        minesweeperBoard.revealSurroundingBlanks(1,1);
        assert minesweeperBoard.getTile(0,0).isRevealed();
    }

    @Test
    public void revealSurroundingDiagTest(){
        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(diagBoard());
        minesweeperBoard.revealTile(1,1);
        minesweeperBoard.revealSurroundingBlanks(1,1);
        assert minesweeperBoard.getTile(0,0).isRevealed();
    }



}