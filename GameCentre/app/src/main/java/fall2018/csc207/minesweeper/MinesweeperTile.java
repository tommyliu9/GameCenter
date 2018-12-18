package fall2018.csc207.minesweeper;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc207.slidingtiles.R;

/**
 * A MinesweeperTile in minesweeper
 */
public class MinesweeperTile implements Serializable {

    /**
     * Contains integer to respective image
     */
    private static Map<Integer, Integer> imageMap() {
        @SuppressLint("UseSparseArrays") Map<Integer, Integer> map = new HashMap<>();
        map.put(BOMB, R.drawable.bomb);
        map.put(BLANK_TILE, R.drawable.blanktile);
        map.put(1, R.drawable.tile_1);
        map.put(2, R.drawable.tile_2);
        map.put(3, R.drawable.tile_3);
        map.put(4, R.drawable.tile_4);
        map.put(5, R.drawable.tile_5);
        map.put(6, R.drawable.tile_6);
        map.put(7, R.drawable.tile_7);
        map.put(8, R.drawable.tile_8);
        return map;
    }

    /**
     * The integer that represents a blank tile
     */
    final static int BLANK_TILE = 0;
    /**
     * The integer that represents a bomb
     */
    final static int BOMB = -1;
    /**
     * The number of adjacent bombs
     */
    private final int id;
    /**
     * The background id to find the tile image
     */
    private int background;
    /**
     * The state of the tile (revealed or not revealed)
     */
    private boolean revealed;
    private final int underBackground;

    /**
     * Returns whether if tile is flagged
     *
     * @return true if flagged false otherwise
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Set whether if MinesweeperTile is flagged
     *
     * @param flagged The value we want to set flagged to.
     */
    public void setFlagged(boolean flagged) {
        if (flagged) {
            this.background = R.drawable.flag;
        } else {
            if (isRevealed()) {
                this.background = underBackground;
            } else {
                this.background = R.drawable.btile;
            }

        }
        this.flagged = flagged;
    }

    /**
     * The state of whether the tile is flagged
     */
    private boolean flagged;

    /**
     * A tile with an id
     *
     * @param id The id of this TwentyFortyEightTile.
     */
    public MinesweeperTile(int id) {
        this.id = id;
        this.background = R.drawable.btile;
        this.underBackground = imageMap().get(id);

    }

    /**
     * Return the tile id.
     *
     * @return the type of MinesweeperTile
     */
    public int getId() {
        return id;
    }

    /**
     * Return the background
     *
     * @return The background of this TwentyFortyEightTile.
     */
    public int getBackground() {
        return background;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }

    /**
     * Returns if the tile has been revealed.
     *
     * @return revealed
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * Set whether the tile has been revealed.
     *
     * @param reveal Whether the tile has been revealed.
     */
    public void setRevealed(boolean reveal) {
        if (!isFlagged()) {
            this.background = this.underBackground;
            revealed = reveal;
        }
    }
}
