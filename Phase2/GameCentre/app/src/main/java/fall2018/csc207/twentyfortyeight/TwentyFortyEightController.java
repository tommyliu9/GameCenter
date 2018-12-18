package fall2018.csc207.twentyfortyeight;

import fall2018.csc207.game.GameController;

/**
 * Manage the board state by processing swipes.
 */
public class TwentyFortyEightController extends GameController<TwentyFortyEightBoard> {

    /**
     * Manage a twentyFortyEightBoard that has been pre-populated.
     *
     * @param board the twentyFortyEightBoard
     */
    TwentyFortyEightController(TwentyFortyEightBoard board) {
        super(board);
    }

    /**
     * Moves all the tiles right
     */
    public void moveRight() {
        gameState.moveRight();
        gameState.afterMoveActions(true);
    }

    /**
     * Moves all the tiles left
     */
    public void moveLeft() {
        gameState.moveLeft();
        gameState.afterMoveActions(true);
    }

    /**
     * Moves all the tiles up
     */
    public void moveUp() {
        gameState.moveUp();
        gameState.afterMoveActions(true);
    }

    /**
     * Moves all the tiles down
     */
    public void moveDown() {
        gameState.moveDown();
        gameState.afterMoveActions(true);
    }
}
