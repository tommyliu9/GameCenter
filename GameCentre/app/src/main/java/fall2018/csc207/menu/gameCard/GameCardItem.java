package fall2018.csc207.menu.gameCard;

import android.graphics.drawable.Drawable;

/**
 * Represents a GameCardItem for use in GameMenuActivity, each represents a playable game.
 */
public class GameCardItem {

    /**
     * The Score to be displayed.
     */
    private final int score;

    /**
     * The title of the game
     */
    private final String gameTitle;

    /**
     * The image of the game card.
     */
    private final Drawable gameImage;

    /**
     * A GameCardItem.
     * @param gameTitle The title associated with the game.
     * @param score The score to be displayed
     * @param gameImage The image to be displayed.
     */
    public GameCardItem(String gameTitle, int score, Drawable gameImage) {
        this.score = score;
        this.gameTitle = gameTitle;
        this.gameImage = gameImage;
    }

    /**
     * Get the score of this GameCardItem.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the Title of this GameCardItem.
     *
     * @return The title.
     */
    public String getGameTitle() {
        return gameTitle;
    }

    /**
     * Get the image of this GameCardItem.
     *
     * @return The image.
     */
    public Drawable getGameImage() {
        return gameImage;
    }

}
