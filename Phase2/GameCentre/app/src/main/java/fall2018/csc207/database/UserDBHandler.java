package fall2018.csc207.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to facilitate interaction between the app and the database.
 */
public class UserDBHandler extends SQLiteOpenHelper{

    /**
     * Strings used to streamline SQL queries and setup the table/db.
     */
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gamecentre.db";
    private static final String TABLE_NAME = "userinfo";

    /*
     * Create a new UserDBHandler
     *
     * @param context The context of the class calling this constructor
     * @param name The name of the database
     * @param factory The cursor factory (left as null)
     * @param version the Version of our database
     */
    public UserDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory,
                         int version) {
        super(context, name, factory, version);
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
                "password" + " TEXT" +
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
     * @param pass the user's password
     */
    public void addUser(String user, String pass){
        ContentValues values = new ContentValues();
        values.put("username", user);
        values.put("password", pass);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Get all entries to the database
     *
     * @return an ArrayList of [user, pass] pairs. Each array in the ArrayList represents one entry
     * (one user).
     */
    public Map<String, String> fetchDatabaseEntries(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + ";";

        Map<String, String> userMap = new HashMap<>();

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("username")) != null){

                String username =  c.getString(c.getColumnIndex("username"));
                String password =  c.getString(c.getColumnIndex("password"));
                userMap.put(username, password);

            }
            c.moveToNext();
        }
        c.close();
        db.close();

        return userMap;
    }
}
