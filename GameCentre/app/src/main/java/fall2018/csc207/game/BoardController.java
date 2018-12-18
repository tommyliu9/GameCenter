package fall2018.csc207.game;

/**
 * A version of GameController used for games that require tapping on specific tiles.
 */
public abstract class BoardController<T extends GameState> extends GameController<T> {

    /**
     * Constructs controller.
     *
     * @param gameState The gameState of this game.
     */
    protected BoardController(T gameState) {
        super(gameState);
    }

    /**
     * Processes the user input given my GestureDetect/MovementController and follows game logic
     * accordingly to update the Model(gameState).
     * Note: This should be the only method that outside classes interact with.
     *
     * @param position The position that was tapped.
     */
    public void processTap(int position) {
        if (isValidTap(position)) {
            updateGame(position);
        }
    }

    /**
     * Determines whether the given tap is valid for the game
     *
     * @return Returns true if and only if the tap is valid.
     */
    abstract protected boolean isValidTap(int position);

    /**
     * Calls subfunctions to update the gameState depending on position.
     *
     * @param position The position on the grid.
     */
    abstract protected void updateGame(int position);
}
