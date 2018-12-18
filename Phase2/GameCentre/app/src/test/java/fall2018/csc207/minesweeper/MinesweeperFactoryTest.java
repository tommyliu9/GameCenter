package fall2018.csc207.minesweeper;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MinesweeperFactoryTest {
    @Test
    public void testFactoryConstructor5x5(){
        MinesweeperFactory newFactory = new MinesweeperFactory();
        MinesweeperBoard board = (MinesweeperBoard)newFactory.getGameState(0);
        assert board.getDimensions() == 5;
    }

    @Test
    public void testFactoryConstructor8x8(){
        MinesweeperFactory newFactory = new MinesweeperFactory();
        newFactory.getSettings().get(0).setCurrentValue(1);
        MinesweeperBoard board = (MinesweeperBoard)newFactory.getGameState(0);
        assert board.getDimensions() == 8;
    }
    @Test
    public void testFactoryConstructor15x15(){
        MinesweeperFactory newFactory = new MinesweeperFactory();
        newFactory.getSettings().get(0).setCurrentValue(2);
        MinesweeperBoard board = (MinesweeperBoard)newFactory.getGameState(0);
        assert board.getDimensions() == 15;
    }
    @Test
    public void testgetGameFragmentClass(){
        MinesweeperFactory newFactory = new MinesweeperFactory();
        assert newFactory.getGameFragmentClass() == MinesweeperFragment.class;
    }
    @Test
    public void testgetGamenames(){
        MinesweeperFactory newFactory = new MinesweeperFactory();
        List b = newFactory.getGameNames();
        assert b.equals(Arrays.asList("Minesweeper 5x5", "Minesweeper 8x8","Minesweeper 15x15"));
    }

}
