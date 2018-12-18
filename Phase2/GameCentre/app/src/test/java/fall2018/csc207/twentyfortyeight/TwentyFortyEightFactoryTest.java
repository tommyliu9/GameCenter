package fall2018.csc207.twentyfortyeight;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import fall2018.csc207.game.GameFactory;

import static org.junit.Assert.fail;


public class TwentyFortyEightFactoryTest {
    private TwentyFortyEightFactory tfeFactory;

    /**
     * Setups stFactory for each test.
     */
    @Before
    public void setupBefore() {
        tfeFactory = new TwentyFortyEightFactory();
    }

    /**
     * Tests if SlidingTilesFactory returns the correct class.
     */
    @Test
    public void testCorrectFragmentClass() {
        assert tfeFactory.getGameFragmentClass().equals(TwentyFortyEightFragment.class);
    }

    /**
     * Tests if SlidingTilesFactory returns the correct game names (for scoreboard + serialization)
     */
    @Test
    public void testGameNames() {
        List<String> gameNames = tfeFactory.getGameNames();
        List<String> expectedNames = Arrays.asList("2048 3x3", "2048 4x4", "2048 5x5", "2048 6x6");
        assert gameNames.equals(expectedNames);
    }

    /**
     * Tests if SlidingTilesFactory returns the correct default settings.
     */
    @Test
    public void testCorrectDefaultSettings() {
        List<GameFactory.Setting> settings = tfeFactory.getSettings();
        assert settings.size() == 1;

        GameFactory.Setting boardSize = settings.get(0);
        List<String> possibleValues = boardSize.getPossibleValues();
        List<String> expectedPossibleVals = Arrays.asList("3x3", "4x4", "5x5", "6x6");

        assert boardSize.getName().equals("Board Size");
        assert possibleValues.equals(expectedPossibleVals);

        assert boardSize.getCurrentValue().equals("4x4");
        assert boardSize.getCurrentValueIndex() == 1;
    }

    /**
     * Tests if TwentyFortyEightFactory throws exceptions on wrong settings.
     */
    @Test
    public void testChangeInvalidSettings() {
        List<GameFactory.Setting> settings = tfeFactory.getSettings();
        GameFactory.Setting boardSize = settings.get(0);
        try {
            boardSize.setCurrentValue(-1);
            fail();
        } catch (IndexOutOfBoundsException ignored) {

        }

        try {
            boardSize.setCurrentValue(5);
            fail();
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

    /**
     * Tests if TwentyFortyEightFactory returns a 3x3 when we set the setting to "0".
     */
    @Test
    public void testGet3x3() {
        GameFactory.Setting boardSize = tfeFactory.getSettings().get(0);
        boardSize.setCurrentValue(0);
        TwentyFortyEightBoard state = (TwentyFortyEightBoard) tfeFactory.getGameState(1);
        assert state.getDimensions() == 3 * 3;
        assert state.getMaxUndos() == 1;
    }

    /**
     * Tests if TwentyFortyEightFactory returns a 4x4 when we set the setting to "1".
     */
    @Test
    public void testGet4x4() {
        GameFactory.Setting boardSize = tfeFactory.getSettings().get(0);
        boardSize.setCurrentValue(1);
        TwentyFortyEightBoard state = (TwentyFortyEightBoard) tfeFactory.getGameState(1);
        assert state.getDimensions() == 4 * 4;
        assert state.getMaxUndos() == 1;
    }

    /**
     * Tests if TwentyFortyEightFactory returns a 5x5 when we set the setting to "2".
     */
    @Test
    public void testGet5x5() {
        GameFactory.Setting boardSize = tfeFactory.getSettings().get(0);
        boardSize.setCurrentValue(2);
        TwentyFortyEightBoard state = (TwentyFortyEightBoard) tfeFactory.getGameState(1);
        assert state.getDimensions() == 5 * 5;
        assert state.getMaxUndos() == 1;
    }

    /**
     * Tests if TwentyFortyEightFactory returns a 6x6 when we set the setting to "3".
     */
    @Test
    public void testGet6x6() {
        GameFactory.Setting boardSize = tfeFactory.getSettings().get(0);
        boardSize.setCurrentValue(3);
        TwentyFortyEightBoard state = (TwentyFortyEightBoard) tfeFactory.getGameState(1);
        assert state.getDimensions() == 6 * 6;
        assert state.getMaxUndos() == 1;
    }

    /**
     * Tests if TwentyFortyEightFactory returns different states on subsequent calls to getGameState.
     */
    @Test
    public void testGetStateGivesDifferentStates() {
        assert tfeFactory.getGameState(0) != tfeFactory.getGameState(0);
    }
}
