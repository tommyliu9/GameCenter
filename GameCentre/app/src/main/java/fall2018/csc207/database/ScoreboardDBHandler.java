package fall2018.csc207.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc207.menu.scoreboard.ScoreboardEntry;

/**
 * A class to facilitate interaction between the app and the database.
 */
public class ScoreboardDBHandler extends SQLiteOpenHelper {

    /**
     * Strings used to streamline SQL queries and setup the table/db.
     */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scoreboard.db";
    private static final String TABLE_NAME = "scoreboard";

    /**
     * Create a new ScoreboardDBHandler
     *
     * @param context The context of the class calling this constructor

     * @param factory The cursor factory (left as null)
     */
    public ScoreboardDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Create the users table if it does not exist.
     *
     * @param db the Database for which we are making our table.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username" + " TEXT, " +
                "score" + " INTEGER, " +
                "game" + " TEXT" +
                ");";

        db.execSQL(query);
    }

    /**
     * Drop (delete) the table.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Add a user entry to the users table.
     * PRECONDITION: user is not already a username in the table.
     *
     * @param user The user's username
     * @param score The user's score
     * @param game The title of the game
     */
    public void addEntry(String user, int score, String game) {
        ContentValues values = new ContentValues();
        values.put("username", user);
        values.put("score", score);
        values.put("game", game);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Get all entries to the database for a specific game.
     *
     * @param gameName The name of the game whose entries are to be fetched.
     *
     * @return an ArrayList of ScoreboardEntry. Each array in the ArrayList represents
     * one entry (one score).
     */
    public ArrayList<ScoreboardEntry> fetchDatabaseEntries(String gameName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE game = \"" + gameName + "\";";

        return getEntriesFromQuery(db, query, "score");
    }

    /**
     * Get all highscores to the database for all games, for a certain user.
     *
     * @param username The user we want the highscores for.
     *
     * @return an ArrayList of ScoreboardEntry with one entry, the highscore entry.
     */
    public List<ScoreboardEntry> fetchUserHighScores(String username){
        SQLiteDatabase db = getWritableDatabase();

        //Needs to match the names of the games in SlidingTilesFactory.java
        String query =
                //Get the scores of just sliding tiles and variants.
                "SELECT username, game, max(score) FROM " + TABLE_NAME +
                " WHERE username = \"" + username + "\" AND game IN " +
                        "(\"Sliding Tiles 3x3\",\"Sliding Tiles 4x4\",\"Sliding Tiles 5x5\")" +
                " UNION" +
                //Get the scores of Minesweeper and variants.
                " SELECT username, game, max(score) FROM " + TABLE_NAME +
                " WHERE username = \"" + username + "\" AND game IN " +
                        "(\"Minesweeper 5x5\",\"Minesweeper 8x8\"," +
                        "\"Minesweeper 13x13\",\"Minesweeper 20x20\")" +
                " UNION" +
                //Get the scores of just 2048.
                " SELECT username, game, max(score) FROM " + TABLE_NAME +
                " WHERE username = \"" + username + "\" AND game IN (\"2048\")" +
                " GROUP BY game;";
        return getEntriesFromQuery(db, query, "max(score)");
    }


    /**
     * Helper function which walks through the query on the scoreboard table.
     *
     * @param db Reference to the db in question.
     * @param query The query we are making to the database.
     * @param scoreType The name of the score column in the table.
     *
     * @return an ArrayList of ScoreboardEntry representing the entries returned from the query. One
     * entry per row in the table.
     */
    @NonNull
    private ArrayList<ScoreboardEntry> getEntriesFromQuery(SQLiteDatabase db, String query,
                                                           String scoreType) {

        ArrayList<ScoreboardEntry> scoreList = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("username")) != null){

                String username =  c.getString(c.getColumnIndex("username"));
                int score = c.getInt(c.getColumnIndex(scoreType));
                String game = c.getString(c.getColumnIndex("game"));
                ScoreboardEntry entry = new ScoreboardEntry(username, game, score);
                scoreList.add(entry);
            }
            c.moveToNext();
        }
        c.close();
        db.close();

        return scoreList;
    }
}
