package fall2018.csc207.twentyfortyeight;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fall2018.csc207.game.GameState;

/**
 * The 2048 tiles board.
 */
public class TwentyFortyEightBoard extends GameState implements Iterable<TwentyFortyEightTile> {

    /**
     * The board for 2048.
     */
    private TwentyFortyEightTile[][] board;
    private final int numRows;
    private final int numCols;

    /**
     * Stack holding previous game states, used for undo
     */
    private final Stack<int[][]> states = new Stack<>();

    /**
     * Number of tiles with values
     */
    private int numActiveTiles;

    /**
     * Number of undos user has used.
     */
    private int undosUsed;

    /**
     * A 2048 Board.
     */
    TwentyFortyEightBoard(int dimensions) {
        this.numCols = dimensions;
        this.numRows = dimensions;
        this.board = new TwentyFortyEightTile[numRows][numCols];
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                this.board[row][col] = new TwentyFortyEightTile(0);
            }
        }
        addTile();
        addTile();
        determineScore();
    }

    /**
     * A 2048 Board.
     */
    TwentyFortyEightBoard(Iterable<TwentyFortyEightTile> tiles, int dimensions) {
        this.board = new TwentyFortyEightTile[dimensions][dimensions];
        Iterator<TwentyFortyEightTile> iter = tiles.iterator();
        numCols = dimensions;
        numRows = dimensions;
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                TwentyFortyEightTile x = iter.next();
                this.board[row][col] = x;
                if(x.value > 0){
                    numActiveTiles++;
                }
            }
        }
    }

    /**
     * Returns the number of tiles in this board
     *
     * @return the numer of tiles
     */
    private int numTiles() {
        return numRows * numCols;
    }

    /**
     * Returns the number of non-empty tiles in this board
     *
     * @return the number of non-empty tiles
     */
    public int getNumActiveTiles() {
        return numActiveTiles;
    }

    /**
     * 2048 MinesweeperTile logic: https://github.com/bulenkov/2048
     * Credits go to Konstantin Bulenkov
     *
     * Add a new tile at an available spot
     */
    private void addTile() {
        List<TwentyFortyEightTile> list = availableSpace();
        if (!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            TwentyFortyEightTile emptyTile = list.get(index);
            emptyTile.setValue(Math.random() < 0.9 ? 2 : 4);
            emptyTile.setBackground(emptyTile.value);
            numActiveTiles++;
        }
    }

    /**
     * Initializs a list of empty tiles  that are currently on the board
     *
     * @return a list of tiles
     */
    private List<TwentyFortyEightTile> availableSpace() {
        final List<TwentyFortyEightTile> list = new ArrayList<>(getDimensions());

        for (TwentyFortyEightTile[] row : board) {
            for (TwentyFortyEightTile twentyFortyEightTile : row) {
                if (twentyFortyEightTile.isEmpty()) {
                    list.add(twentyFortyEightTile);
                }
            }
        }
        return list;
    }

    /**
     * Shifts all tiles up and merges simliar tiles in sequence
     */
    public void moveUp() {
        states.push(getStateAsIntArray());
        for (int index = 0; index < board.length; index++) {
            for (int b = 0; b < board.length; b++) {
                boolean seen = false;
                for (int i = b + 1; i != board.length; i++) {
                    if (board[b][index].value == 0 && board[i][index].value != 0) {
                        swapWithZero(board[i][index], board[b][index]);
                    } else {
                        if (board[b][index].value != board[i][index].value && board[i][index].value != 0) {
                            seen = true;
                        }
                        if (board[i][index].canMergeWith(board[b][index]) && !seen) {
                            merge(board[b][index], board[i][index]);
                        }
                    }
                }
            }
        }

    }

    /**
     * Shifts all tiles down and merges simliar tiles in sequence
     */
    public void moveDown() {
        states.push(getStateAsIntArray());
        for (int index = 0; index < board.length; index++) {
            for (int b = board.length - 1; b >= 1; b--) {
                boolean seen = false;
                for (int i = b - 1; i >= 0; i--) {
                    if (board[b][index].value == 0 && board[i][index].value != 0) {
                        swapWithZero(board[i][index], board[b][index]);
                    } else {
                        if (board[b][index].value != board[i][index].value && board[i][index].value != 0) {
                            seen = true;
                        }
                        if (board[i][index].canMergeWith(board[b][index]) && !seen) {
                            merge(board[b][index], board[i][index]);
                        }
                    }
                }
            }
        }


    }

    /**
     * Shifts all tiles left and merges simliar tiles in sequence
     */
    public void moveLeft() {
        states.push(getStateAsIntArray());
        for (TwentyFortyEightTile[] row : board) {
            for (int b = 0; b < board.length; b++) {
                boolean seen = false;
                for (int i = b + 1; i != board.length; i++) {
                    if (row[b].value == 0 && row[i].value != 0) {
                        swapWithZero(row[i], row[b]);
                    } else {
                        if (row[b].value != row[i].value && row[i].value != 0) {
                            seen = true;
                        }
                        if (row[i].canMergeWith(row[b]) && !seen) {
                            merge(row[b], row[i]);
                        }
                    }
                }
            }
        }

    }

    /**
     * Shifts all tiles right and merges simliar tiles in sequence
     */
    public void moveRight() {
        states.push(getStateAsIntArray());
        for (TwentyFortyEightTile[] row : board) {
            for (int b = board.length - 1; b >= 1; b--) {
                boolean seen = false;
                for (int i = b - 1; i >= 0; i--) {
                    if (row[b].value == 0 && row[i].value != 0) {
                        swapWithZero(row[i], row[b]);
                    } else {
                        if (row[b].value != row[i].value && row[i].value != 0) {
                            seen = true;
                        }
                        if (row[i].canMergeWith(row[b]) && !seen) {
                            merge(row[b], row[i]);
                        }
                    }
                }
            }
        }

    }

    /**
     * Swaps the zero tile with the root tile
     *
     * @param root tile with value
     * @param zero tile with no value
     */
    private void swapWithZero(TwentyFortyEightTile root, TwentyFortyEightTile zero) {
        zero.value = root.value;
        root.value = 0;
        zero.setBackground(zero.value);
        root.setBackground(root.value);

    }

    /**
     * Merges the tiles root and merger by takinng merger,
     * adding it to root and setting merger to zero
     *
     * @param root   The destination of the tile
     * @param merger The merger
     */
    private void merge(TwentyFortyEightTile root, TwentyFortyEightTile merger) {
        root.value = root.value + merger.value;
        if (merger.value != 0){
            numActiveTiles--;
        }
        merger.value = 0;
        merger.setMerged(true);
        root.setMerged(true);
        root.setBackground(root.value);
        merger.setBackground(merger.value);
        determineScore();

    }


    /**
     * Updates the score of the board
     */
    private void determineScore() {
        int currentScore = 0;
        int dimension = board.length;
        for (TwentyFortyEightTile[] aBoard : board) {
            for (int j = 0; j < dimension; j++) {
                currentScore += aBoard[j].getValue();
            }
        }
        this.score = currentScore;
    }

    /**
     * Clears all merge flags after a move
     */
    private void clearMerged() {
        for (TwentyFortyEightTile[] row : board)
            for (TwentyFortyEightTile twentyFortyEightTile : row)
                if (!twentyFortyEightTile.isEmpty())
                    twentyFortyEightTile.setMerged(false);
    }

    /**
     * Performs post move actions to prepare for next move and updates the board.
     * @param shouldNotifyObs Should update the board or not.
     */
    public void afterMoveActions(boolean shouldNotifyObs) {
        clearMerged();
        setChanged();
        if (shouldNotifyObs) {
            notifyObservers();
        }
        addTile();
    }

    /**
     * Create a 2D Array representing the current tiles, as integers
     * @return The 2D array.
     */
    private int[][] getStateAsIntArray() {
        int[][] intArray = new int[numRows][numCols];
        for (int i = 0; i < board.length; i++) {
            for (int b = 0; b < board.length; b++) {
                intArray[i][b] = getTile(i, b).value;
            }
        }
        return intArray;
    }

    /**
     * Revert to a previous state.
     */
    @Override
    public void undo() {
        TwentyFortyEightTile[][] newTiles = new TwentyFortyEightTile[numRows][numCols];
        int[][] numRep = states.pop();
        numActiveTiles = 0;
        for (int i = 0; i < numRep.length; i++) {
            for (int b = 0; b < numRep[0].length; b++) {
                TwentyFortyEightTile newTile = new TwentyFortyEightTile(numRep[i][b]);
                newTiles[i][b] = newTile;
                if (newTile.value > 0){
                    numActiveTiles++;
                }
            }
        }
        this.board = newTiles;
        setChanged();
        notifyObservers();
        undosUsed++;
    }

    /**
     * Determine whether undo is valid.
     * @return Whether undo is valid or not.
     */
    @Override
    public boolean canUndo() {
        return (getMaxUndos() == -1 && !states.isEmpty() && undosUsed < getMaxUndos());
    }


    /**
     * Return true if there any avaialable moves left
     * on the board
     *
     * @return true if game is over false otherwise
     */
    @Override
    public boolean isOver() {
        if (availableSpace().size() != 0) {
            return false;
        } else {
            for (int i = 0; i < numCols; i++) {
                for (int j = 0; j < numRows; j++) {
                    if (hasMergableNeighbour(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Return true if there exists a neighbor the
     * tile can merge with
     *
     * @param row position of the board
     * @param col position of the board
     * @return whether if position (row, rol) has multiple labor
     */
    public boolean hasMergableNeighbour(int row, int col) {
        int thisVal = board[row][col].getValue();
        if (row - 1 >= 0 && board[row - 1][col].getValue() == thisVal) {
            return true;
        } else if (col - 1 >= 0 && board[row][col - 1].getValue() == thisVal) {
            return true;
        } else if (row + 1 < numRows && board[row + 1][col].getValue() == thisVal) {
            return true;
        }
        return (col + 1 < numCols && board[row][col + 1].getValue() == thisVal);
    }


    /**
     * Return the name of the game which is
     * 2048
     * @return the name of the game
     */
    @Override
    public String getGameName() {
        return "2048";
    }

    @NonNull
    @Override
    public Iterator<TwentyFortyEightTile> iterator() {
        return new BoardIterator(board);
    }

    /**
     * Return the dimensions of the board
     *
     * @return the dimensions of the board
     */
    public int getDimensions() {
        return numRows * numCols;
    }

    /**
     * Gets the tile given the position
     *
     * @param row position
     * @param col position
     * @return a Tile from the board
     */
    public TwentyFortyEightTile getTile(int row, int col) {
        return board[row][col];
    }

    /**
     * Iterates through the TwentyFortyEightTiles
     */
    private class BoardIterator implements Iterator<TwentyFortyEightTile> {

        /**
         * index indicates the position of the board
         */
        private int index;
        private final TwentyFortyEightTile[][] array2D;

        private BoardIterator(TwentyFortyEightTile[][] tiles) {
            array2D = tiles;
        }

        @Override
        public boolean hasNext() {
            return index != numTiles();
        }

        @Override
        public TwentyFortyEightTile next() {
            int col = index % numCols;
            int row = index / numCols;
            index++;

            return array2D[row][col];
        }
    }

}
