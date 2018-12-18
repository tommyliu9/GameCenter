package fall2018.csc207.minesweeper;

import fall2018.csc207.game.BoardController;

/**
 * Controller for Minesweeper.
 */
public class MinesweeperController extends BoardController<MinesweeperBoard> {

    /**
     * The dimension of the board
     */
    private final int dimensions;

    /**
     * Manage a minesweeperBoard that has been pre-populated.
     *
     * @param minesweeperBoard the minesweeperBoard
     */
    MinesweeperController(MinesweeperBoard minesweeperBoard) {
        super(minesweeperBoard);
        this.dimensions = gameState.getDimensions();
    }

    /**
     * Processes user input and updates tile game accordingly.
     *
     * @param position The position of the input.
     */
    @Override
    public void updateGame(int position) {
        int row = position / dimensions;
        int col = position % dimensions;

        MinesweeperTile currTile = gameState.getTile(row, col);

        //Check for cases where the selected tile is a BOMB or BLANK_TILE
        if (currTile.getId() == MinesweeperTile.BOMB && gameState.getNumRevealedTiles() == 0){
            gameState.deleteBomb(row,col);
            updateGame(position);
        }
        else if (currTile.getId() == MinesweeperTile.BOMB) {
            gameState.setGameLost(true);
            gameState.endGame(row, col);
        }
        else if (currTile.getId() == MinesweeperTile.BLANK_TILE && !currTile.isFlagged()) {
            gameState.revealSurroundingBlanks(row, col);
        }

        if (!currTile.isFlagged()) {
            gameState.revealTile(row, col);
        }
    }

    /**
     * Return whether the position tapped is a valid move. In minesweeper, the user
     * may tap any square.
     *
     * @param position the tile to check
     * @return Whether or not the position tapped is valid.
     */
    @Override
    protected boolean isValidTap(int position) {
        return true;
    }

    /**
     * Flags a MinesweeperTile.
     *
     * @param position the position
     */
    public void flagTile(int position) {
        int row = position / dimensions;
        int col = position % dimensions;
        gameState.flagTile(row, col);
    }
}
