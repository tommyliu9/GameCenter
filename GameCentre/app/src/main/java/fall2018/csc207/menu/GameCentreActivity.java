package fall2018.csc207.menu;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fall2018.csc207.database.ScoreboardDBHandler;
import fall2018.csc207.menu.gameCard.GameCardAdapter;
import fall2018.csc207.menu.gameCard.GameCardItem;
import fall2018.csc207.menu.scoreboard.ScoreboardEntry;
import fall2018.csc207.minesweeper.MinesweeperFactory;
import fall2018.csc207.slidingtiles.R;
import fall2018.csc207.slidingtiles.SlidingTilesFactory;
import fall2018.csc207.twentyfortyeight.TwentyFortyEightFactory;

/**
 * The Game Centre Activity class
 */
public class GameCentreActivity extends AppCompatActivity {

    /**
     * Key for use in intents.
     */
    private static final String USERNAME = "USERNAME";
    /**
     * A map from the name of the game to the game's factory class.
     */
    private static final Map<String, Class> GAME_LIBRARY = new HashMap<>();

    /**
     * Retrieves the factory class for a particular game.
     *
     * @param gameName The name of the game to retrieve the GameFactory for.
     * @return The GameFactory corresponding to the particular game.
     */
    public static Class getFactoryClass(String gameName) {
        return GAME_LIBRARY.get(gameName);
    }


//  This static constructor is called when GameCentreActivity is first used.
//  We add elements to GAME_LIBRARY through this constructor (as there is no way to initialize
//  a HashMap inline).

    static {
        GAME_LIBRARY.put("Sliding Tiles", SlidingTilesFactory.class);
        GAME_LIBRARY.put("Minesweeper", MinesweeperFactory.class);
        GAME_LIBRARY.put("2048", TwentyFortyEightFactory.class);
    }

    /**
     * The user's username.
     */
    private String username;
    /**
     * Game Card's adapter that will notify the recyclerview if any item is added
     */
    private GameCardAdapter gameCardAdapter;
    /**
     * List of all the games in a GameCardItem format
     */
    private final List<GameCardItem> gameCardItemList = new ArrayList<>();

    /**
     * Called when we create a GameCentreActivity.
     *
     * @param savedInstanceState The activity's previously saved state, contained in a bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_centre);

        username = getIntent().getStringExtra(USERNAME);
        //Create the LayoutManager
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.gameCentreRecyclerView);
        gameCardAdapter = new GameCardAdapter(gameCardItemList, username);
        recyclerView.setAdapter(gameCardAdapter);
        recyclerView.setLayoutManager(mLayoutManager);

        createGameCards();
    }

    /**
     * Create the GameCardItems.
     */
    private void createGameCards() {
        Map<String, Drawable> pictureMap = getCardDrawables();
        Map<String, Integer> userHighScores = new HashMap<>();

        //Set the default highscores to 0.
        for (HashMap.Entry<String, Class> entry : GAME_LIBRARY.entrySet()) {
            userHighScores.put(entry.getKey(), 0);
        }

        //Get a reference to the Database, load the highscores.
        ScoreboardDBHandler db = new ScoreboardDBHandler(this, null);
        List<ScoreboardEntry> userHighScoreList = db.fetchUserHighScores(username);

        //Fill the highscores of the different games
        for (ScoreboardEntry entry : userHighScoreList) {
            // If the name is Sliding Tiles 3x3, 4z4 etc change it to just SlidingTiles.
            if (entry.getGame().contains("Sliding Tiles"))
                entry.setGame("Sliding Tiles");
            else if (entry.getGame().contains("Minesweeper"))
                entry.setGame("Minesweeper");
            userHighScores.put(entry.getGame(), entry.getScore());
        }

        // For each game in the game library make a new GameCardItem
        for (HashMap.Entry<String, Class> entry : GAME_LIBRARY.entrySet()) {
            String gameName = entry.getKey();
            GameCardItem newGame = new GameCardItem(gameName, userHighScores.get(gameName),
                    pictureMap.get(gameName));
            gameCardItemList.add(newGame);
        }

        gameCardAdapter.notifyDataSetChanged();

    }

    /**
     * Get a hashmap of the game's names and their corresponding pictures.
     *
     * @return The hashmap of the game's names and their corresponding pictures.
     */
    private Map<String, Drawable> getCardDrawables() {
        Map<String, Drawable> pictureMap = new HashMap<>();
        Drawable slidingTiles = getDrawable(R.drawable.slidingtiles);
        Drawable twentyFortyEight = getDrawable(R.drawable.tilelogo);
        Drawable minesweeper = getDrawable(R.drawable.mine);
        pictureMap.put("Sliding Tiles", slidingTiles);
        pictureMap.put("2048", twentyFortyEight);
        pictureMap.put("Minesweeper", minesweeper);
        return pictureMap;
    }
}
