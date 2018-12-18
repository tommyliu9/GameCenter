package fall2018.csc207.game;

/**
 *
 * Manages all the logic of a game while changing game data through interacting with
 * gamestates interface
 */

public abstract class GameController<T extends GameState> {

    /**
     * The model for the game.
     */
    protected final T gameState;


    /**
     * The GameController.
     *
     * @param gameState The gameState of this game.
     */
    protected GameController(T gameState) {
        this.gameState = gameState;
    }

    /**
     * Getter function for GameState retrieval.
     *
     * @return The GameState.
     */
    public T getGameState() {
        return this.gameState;
    }

}
