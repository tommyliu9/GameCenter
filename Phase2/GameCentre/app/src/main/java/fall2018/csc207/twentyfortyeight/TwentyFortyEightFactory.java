package fall2018.csc207.twentyfortyeight;

import java.util.Arrays;
import java.util.List;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameState;

public class TwentyFortyEightFactory extends GameFactory{

    /**
     * The settings for the 2048 game.
     */
    public TwentyFortyEightFactory() {
        addToSettings(new Setting(
                "Board Size",
                Arrays.asList("3x3", "4x4", "5x5", "6x6"),
                "4x4"
        ));
    }

    /**
     * Create a 2048 GameState with some number of undos
     * @param numUndos The number of undos
     * @return the 2048 GameState
     */
    @Override
    public GameState getGameState(int numUndos) {
        TwentyFortyEightBoard board = null;
        switch (settings.get(0).getCurrentValue()) {
            case "3x3":
                board = new TwentyFortyEightBoard(3);
                break;
            case "4x4":
                board = new TwentyFortyEightBoard(4);
                break;
            case "5x5":
                board = new TwentyFortyEightBoard(5);
                break;
            case "6x6":
                board = new TwentyFortyEightBoard(6);
                break;
        }
        if (board != null){
            board.setMaxUndos(numUndos);
        }
        return board;
    }

    /**
     * Get the 2048 Fragment.
     * @return The 2048 Fragment.
     */
    @Override
    public Class getGameFragmentClass() {
        return TwentyFortyEightFragment.class;
    }

    /**
     * Get the list of variants of 2048.
     * @return The list of variants.
     */
    @Override
    public List<String> getGameNames() {
        return Arrays.asList("2048 3x3", "2048 4x4", "2048 5x5", "2048 6x6");
    }
}
