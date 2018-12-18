package fall2018.csc207.minesweeper;


import fall2018.csc207.game.GameState;

public class MinesweeperBoard extends GameState {

    /**
     * The Tiles on the board
     */
    private final MinesweeperTile[][] mineField;
    /**
     * The size of the board
     */
    private final int dimensions;

    /**
     * How many tiles are revealed.
     */
    private int numRevealedTiles;

    /**
     * The number of mines on a board.
     */
    private int numMines;

    /**
     * Generate a board given the dimensions and difficulty
     *
     * @param dimensions the width and height of the board
     * @param difficulty the frequency of bombs
     */
    public MinesweeperBoard(int dimensions, double difficulty) {
        setScore(1000);
        this.dimensions = dimensions;
        this.numRevealedTiles = 0;
        mineField = generateBoard(dimensions, difficulty);
    }

    /**
     * Generate a board given the integer 2d array representation
     *
     * @param numRep a 2d array representation of the board
     */
    public MinesweeperBoard(int[][] numRep) {
        setScore(100000);
        mineField = new MinesweeperTile[numRep.length][numRep[0].length];
        for (int i = 0; i < numRep.length; i++) {
            for (int j = 0; j < numRep.length; j++) {
                mineField[i][j] = new MinesweeperTile(numRep[i][j]);
                if (numRep[i][j] == -1) {
                    numMines++;
                }
            }
        }
        this.dimensions = mineField.length;
        this.numRevealedTiles = 0;

    }

    /**
     * Return the size of the board.
     *
     * @return returns the size of the board
     */
    public int getDimensions() {
        return dimensions;
    }


    /**
     * Return the number of mines
     *
     * @return Returns the number of mines on the board
     */
    public int getNumMines() {
        return numMines;
    }

    /**
     * Return the number of revealed tiles.
     *
     * @return Returns the number of mines on the board
     */
    public int getNumRevealedTiles() {
        return numRevealedTiles;
    }

    @Override
    public void undo() {
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    /**
     * Called when a bomb is clicked.
     */
    public void endGame(int row, int col) {
        score = 0;
        revealTile(row, col);
        isOver();
    }

    /**
     * Determines if a bomb was revealed, or time runs out
     *
     * @return Whether or not the game is over.
     */
    @Override
    public boolean isOver() {
        if (score == 0) {
            return true;
        }
        for (MinesweeperTile[] first : mineField) {
            for (MinesweeperTile minesweeperTile : first) {
                if (minesweeperTile.getId() == MinesweeperTile.BOMB &&
                        minesweeperTile.isRevealed()) {
                    return true;
                }
                if (minesweeperTile.getId() == MinesweeperTile.BOMB &&
                        !minesweeperTile.isFlagged()) {
                    return false;
                }


            }
        }
        return !(numRevealedTiles + getNumMines() <= mineField.length * mineField.length - 1);
    }

    /**
     * Returns the name of the game.
     *
     * @return Minesweeper name.
     */
    @Override
    public String getGameName() {
        String name = "Minesweeper ";
        String setting = String.valueOf(this.dimensions) + "x" + String.valueOf(this.dimensions);
        return name + setting;
    }

    /**
     * Generates a minesweeper board
     * Minesweeper MinesweeperBoard generating algorithm:
     * https://introcs.cs.princeton.edu/java/14array/Minesweeper.java.html
     * Credits go to Princeton University
     *
     * @param dimensions the width and height of the board
     * @param difficulty the frequency of bombs
     * @return A generated tile board
     */
    private MinesweeperTile[][] generateBoard(int dimensions, double difficulty) {
        MinesweeperTile[][] mines = new MinesweeperTile[dimensions][dimensions];
        int[][] repMines = new int[dimensions + 2][dimensions + 2];
        for (int x = 1; x <= dimensions; x++) {
            for (int y = 1; y <= dimensions; y++) {
                if (difficulty >= Math.random()) {
                    numMines += 1;
                    repMines[x][y] = MinesweeperTile.BOMB;
                }
            }
        }
        for (int x = 1; x <= dimensions; x++) {
            for (int y = 1; y <= dimensions; y++) {
                int counter = 0;
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (repMines[i][j] == MinesweeperTile.BOMB) {
                            counter++;
                        }
                    }
                }
                if (repMines[x][y] != MinesweeperTile.BOMB) {
                    repMines[x][y] = counter;
                }
                mines[x - 1][y - 1] = new MinesweeperTile(repMines[x][y]);
            }
        }
        return mines;
    }

    /**
     * Get the tile at row, col.
     *
     * @param row The row of this tile
     * @param col The col of this tile
     * @return Returns the time at row and col in the mineField
     */
    public MinesweeperTile getTile(int row, int col) {
        return mineField[row][col];
    }

    /**
     * Reveals the single tile located at row, col.
     *
     * @param col position of the column.
     * @param row position of the row.
     */
    public void revealTile(int row, int col) {
        mineField[row][col].setRevealed(true);
        mineField[row][col].setFlagged(false);
        numRevealedTiles++;
        setChanged();
        notifyObservers();
    }

    /**
     * Reveals surrounding blank tiles as well as one layer of number tiles
     * Precondition: mineField[col][row].BLANK_TILE == 0
     *
     * @param col position of the column
     * @param row position of the row
     */
    public void revealSurroundingBlanks(int row, int col) {
        revealEdgeAdjacent(row, col);
        revealDiagonalAdjacent(row, col);
    }

    /**
     * Reveals the tiles that are connected to this tile, connected diagonally.
     *
     * @param row The row of this tile.
     * @param col The column of this tile.
     */
    private void revealDiagonalAdjacent(int row, int col) {
        //TwentyFortyEightTile below-right
        if (canRevealTile(row + 1, col + 1)) {
            revealTile(row + 1, col + 1);
            if (isBlankTile(row + 1, col + 1)) {
                revealSurroundingBlanks(row + 1, col + 1);
            }
        }
        //TwentyFortyEightTile above-left
        if (canRevealTile(row - 1, col - 1)) {
            revealTile(row - 1, col - 1);
            if (isBlankTile(row - 1, col - 1)) {
                revealSurroundingBlanks(row - 1, col - 1);
            }
        }
        //TwentyFortyEightTile above-right
        if (canRevealTile(row - 1, col + 1)) {
            revealTile(row - 1, col + 1);
            if (isBlankTile(row - 1, col + 1)) {
                revealSurroundingBlanks(row - 1, col + 1);
            }
        }
        //TwentyFortyEightTile below-left
        if (canRevealTile(row + 1, col - 1)) {
            revealTile(row + 1, col - 1);
            if (isBlankTile(row + 1, col - 1)) {
                revealSurroundingBlanks(row + 1, col - 1);
            }
        }
    }

    /**
     * Reveals the tiles that are connected to this tile, connected by edge.
     *
     * @param row The row of this tile.
     * @param col The column of this tile.
     */
    private void revealEdgeAdjacent(int row, int col) {
        //TwentyFortyEightTile Below
        if (canRevealTile(row + 1, col)) {
            revealTile(row + 1, col);
            if (isBlankTile(row + 1, col)) {
                revealSurroundingBlanks(row + 1, col);
            }
        }
        //TwentyFortyEightTile above
        if (canRevealTile(row - 1, col)) {
            revealTile(row - 1, col);
            if (isBlankTile(row - 1, col)) {
                revealSurroundingBlanks(row - 1, col);
            }
        }
        //TwentyFortyEightTile to right
        if (canRevealTile(row, col + 1)) {
            revealTile(row, col + 1);
            if (isBlankTile(row, col + 1)) {
                revealSurroundingBlanks(row, col + 1);
            }
        }
        //TwentyFortyEightTile to left
        if (canRevealTile(row, col - 1)) {
            revealTile(row, col - 1);
            if (isBlankTile(row, col - 1)) {
                revealSurroundingBlanks(row, col - 1);
            }
        }
    }

    /**
     * Return if tile at row, col can be revealed.
     *
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return Whether the tile can be revealed.
     */
    private boolean canRevealTile(int row, int col) {
        return col < dimensions && col >= 0 && row < dimensions && row >= 0 &&
                mineField[row][col].getId() != MinesweeperTile.BOMB &&
                !mineField[row][col].isRevealed();
    }

    /**
     * Return whether tile at row, col is blank.
     *
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return Whether the tile is blank.
     */
    private boolean isBlankTile(int row, int col) {
        return mineField[row][col].getId() == MinesweeperTile.BLANK_TILE;
    }

    /**
     * Removes the BOMB at given position to change the tile to a null tile.
     * Updates the values of the surronding tiles accordingly.
     *
     * @param row The row of this tile.
     * @param col The column of this tile.
     */
    public void deleteBomb(int row, int col) {
        //Delete the bomb.
        mineField[row][col] = new MinesweeperTile(getNumAdjacentBombs(row, col));
        if (canDecrementTile(row + 1, col)) {
            decrementTile(row + 1, col);
        }
        if (canDecrementTile(row, col + 1)) {
            decrementTile(row, col + 1);
        }
        if (canDecrementTile(row - 1, col)) {
            decrementTile(row - 1, col);
        }
        if (canDecrementTile(row, col - 1)) {
            decrementTile(row, col - 1);
        }
        if (canDecrementTile(row + 1, col + 1)) {
            decrementTile(row + 1, col + 1);
        }
        if (canDecrementTile(row - 1, col - 1)) {
            decrementTile(row - 1, col - 1);
        }
        if (canDecrementTile(row - 1, col + 1)) {
            decrementTile(row - 1, col + 1);
        }
        if (canDecrementTile(row + 1, col - 1)) {
            decrementTile(row + 1, col - 1);
        }
        numMines--;
    }

    /**
     * Return the number of adjacent bombs to this tile.
     *
     * @param row The row of this tile.
     * @param col The column of this tile.
     * @return The number of adjacent bombs.
     */
    private int getNumAdjacentBombs(int row, int col) {
        int count = 0;
        if (isInBounds(row + 1, col) && mineField[row + 1][col].getId() == MinesweeperTile.BOMB)
            count++;
        if (isInBounds(row, col + 1) && mineField[row][col + 1].getId() == MinesweeperTile.BOMB)
            count++;
        if (isInBounds(row - 1, col) && mineField[row - 1][col].getId() == MinesweeperTile.BOMB)
            count++;
        if (isInBounds(row, col - 1) && mineField[row][col - 1].getId() == MinesweeperTile.BOMB)
            count++;
        if (isInBounds(row + 1, col + 1) && mineField[row + 1][col + 1].getId() == MinesweeperTile.BOMB)
            count++;
        if (isInBounds(row + 1, col - 1) && mineField[row + 1][col - 1].getId() == MinesweeperTile.BOMB)
            count++;
        if (isInBounds(row - 1, col - 1) && mineField[row - 1][col - 1].getId() == MinesweeperTile.BOMB)
            count++;
        if (isInBounds(row - 1, col + 1) && mineField[row - 1][col + 1].getId() == MinesweeperTile.BOMB)
            count++;
        return count;
    }

    /**
     * Replace the TwentyFortyEightTile at row, col with a new tile with 1 less adjacent bombs.
     *
     * @param row The row of this tile.
     * @param col The column of this tile.
     */
    private void decrementTile(int row, int col) {
        mineField[row][col] = new MinesweeperTile(mineField[row][col].getId() - 1);
    }

    /**
     * Return whether the given tile represents a valid tile to decrement. Cant decrement a bomb.
     *
     * @param row The row of this tile.
     * @param col The column of this tile.
     * @return Whether the tile represents a valid tile (one that is in bounds)
     */
    private boolean canDecrementTile(int row, int col) {
        return isInBounds(row, col) &&
                mineField[row][col].getId() != MinesweeperTile.BOMB;
    }

    /**
     * Determines if a given coordinate is in bounds.
     *
     * @param row The row of tile.
     * @param col The column of the tile.
     * @return Whether the coordinate is in bounds.
     */
    private boolean isInBounds(int row, int col) {
        return row < dimensions && col < dimensions && row >= 0 && col >= 0;
    }

    /**
     * Flags a tile
     *
     * @param row The row of the tile.
     * @param col The col of the tile.
     */
    public void flagTile(int row, int col) {
        if (!getTile(row, col).isRevealed()) {
            getTile(row, col).setFlagged(!getTile(row, col).isFlagged());
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Decreases the score by one.
     */
    public void decrementScore() {
        setScore(getScore() - 1);
    }

}
