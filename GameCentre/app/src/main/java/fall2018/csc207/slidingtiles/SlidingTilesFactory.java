package fall2018.csc207.slidingtiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fall2018.csc207.game.GameFactory;

/**
 * Represents the different settings for tiles
 */
public class SlidingTilesFactory extends GameFactory {
    /**
     * The settings for the tile game.
     */
    public SlidingTilesFactory() {
        addToSettings(new Setting(
                "Sliding Tiles Size",
                Arrays.asList("3x3", "4x4", "5x5"),
                "3x3"));
    }

    /**
     * Make a Sliding Tiles board.
     *
     * @param dimensions The dimension of the square board.
     * @return A list of the tiles of this board.
     */
    private List<List<SlidingTilesTile>> generateBoard(int dimensions) {
        List<SlidingTilesTile> slidingTilesTiles = new ArrayList<>();
        int numTiles = dimensions * dimensions;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            slidingTilesTiles.add(new SlidingTilesTile(tileNum));
        }

        //Remove the last tile, add a blank tile.
        slidingTilesTiles.remove(numTiles - 1);
        slidingTilesTiles.add(new SlidingTilesTile(numTiles, R.drawable.blanktile));
        shuffleTiles(slidingTilesTiles);

        return to2DArray(dimensions, slidingTilesTiles);
    }

    /**
     * Shuffles slidingTilesTiles. This will always generate a solved board, because we perform an even number
     * of swaps.
     * <p>
     * This mutates slidingTilesTiles.
     * Source: https://www.jstor.org/stable/2369492
     *
     * @param slidingTilesTiles The slidingTilesTiles to shuffle
     */
    private void shuffleTiles(List<SlidingTilesTile> slidingTilesTiles) {
        int numTiles = slidingTilesTiles.size();
        Random rng = new Random();

        int numSwaps = rng.nextInt(numTiles) * 2 + numTiles;
        for (int i = 0; i < numSwaps; i++) {
            int ind1 = rng.nextInt(numTiles - 1);
            int ind2 = rng.nextInt(numTiles - 1);

            if (ind1 == ind2) // Ensures we never swap the same 2 slidingTilesTiles
                ind1 += ind1 > 0 ? -1 : 1;

            Collections.swap(slidingTilesTiles, ind1, ind2);
        }
    }

    /**
     * Converts slidingTilesTiles to a 2D list. Assumes slidingTilesTiles.size() == dim.
     *
     * @param dim   The dimension of the 2D array (where width = length = dim).
     * @param slidingTilesTiles The array to generate a 2D array from.
     */
    private List<List<SlidingTilesTile>> to2DArray(int dim, List<SlidingTilesTile> slidingTilesTiles) {
        List<List<SlidingTilesTile>> returnList = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            returnList.add(new ArrayList<SlidingTilesTile>());
            for (int j = 0; j < dim; j++) {
                returnList.get(i).add(slidingTilesTiles.get(i * dim + j));
            }
        }
        return returnList;
    }

    /**
     * Get a new GameState of setting's dimensions.
     *
     * @param numUndos The number of undos to allow.
     * @return The new GameState.
     */
    public SlidingTilesBoard getGameState(int numUndos) {
        SlidingTilesBoard slidingTilesBoard = null;
        switch (settings.get(0).getCurrentValue()) { //There should only be 1 thing in settings

            case "3x3":
                slidingTilesBoard = new SlidingTilesBoard(generateBoard(3));
                break;
            case "4x4":
                slidingTilesBoard = new SlidingTilesBoard(generateBoard(4));
                break;
            case "5x5":
                slidingTilesBoard = new SlidingTilesBoard(generateBoard(5));
                break;
        }
        slidingTilesBoard.setMaxUndos(numUndos);
        return slidingTilesBoard;
    }

    /**
     * Get the game's fragment class.
     *
     * @return The game's fragment class.
     */
    @Override
    public Class getGameFragmentClass() {
        return SlidingTilesFragment.class;
    }

    /**
     * Get the names of the different sliding tiles variations.
     *
     * @return The names of the variations.
     */
    @Override
    public List<String> getGameNames() {
        return Arrays.asList("Sliding Tiles 3x3", "Sliding Tiles 4x4", "Sliding Tiles 5x5");
    }
}
