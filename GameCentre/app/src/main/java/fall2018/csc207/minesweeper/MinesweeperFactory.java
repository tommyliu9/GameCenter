package fall2018.csc207.minesweeper;

import java.util.Arrays;
import java.util.List;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameState;

public class MinesweeperFactory extends GameFactory {
    /**
     * The settings for the Minesweeper game.
     */
    public MinesweeperFactory() {
        addToSettings(new Setting(
                "Minesweeper Size",
                Arrays.asList("5x5", "8x8", "15x15"),
                "5x5"
        ));
        addToSettings(new Setting(
                "Mine Probability",
                Arrays.asList("0.1", "0.2", "0.3", "0.4", "0.45"),
                "0.2"
        ));
    }

    /**
     * Return a gamestate
     * @param numUndos The number of undos to allow.
     * @return a new GameState of type MinesweeperBoard
     */
    @Override
    public GameState getGameState(int numUndos) {
        MinesweeperBoard minesweeperBoard;
        double mineProbability = Double.valueOf(settings.get(1).getCurrentValue());
        String val = settings.get(0).getCurrentValue();

        if (val.equals("5x5")){
            minesweeperBoard = new MinesweeperBoard(5, mineProbability);
        } else if (val.equals("8x8")){
            minesweeperBoard = new MinesweeperBoard(8, mineProbability);
        }
        else {
            minesweeperBoard = new MinesweeperBoard(15, mineProbability);
        }

        minesweeperBoard.setMaxUndos(numUndos);
        return minesweeperBoard;
    }

    @Override
    public Class getGameFragmentClass() {
        return MinesweeperFragment.class;
    }

    @Override
    public List<String> getGameNames() {
        return Arrays.asList("Minesweeper 5x5", "Minesweeper 8x8", "Minesweeper 15x15");
    }
}
