package fall2018.csc207.menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.game.GameState;
import fall2018.csc207.game.GameStateIO;
import fall2018.csc207.menu.scoreboard.ScoreboardActivity;
import fall2018.csc207.slidingtiles.R;

/**
 * The Initial activity for instantiating Games
 */
public class GameMenuActivity extends AppCompatActivity {

    /**
     * The user that's currently playing.
     */
    private String username;
    /**
     * The GameFactory for this game.
     */
    private GameFactory gameFactory;
    /**
     * The name of the game that we're playing. We should be able to use this to retrieve
     * the game factory.
     */
    private String gameName;
    /**
     * The dialog box used to give instructions the game.
     */
    private Dialog infoDialog;

    /**
     * Called when we create a GameMenuActivity.
     *
     * @param savedInstanceState The activity's previously saved state, contained in a bundle.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
        Intent intent = getIntent();

        //Get name of user and the game.
        username = intent.getStringExtra(GameMainActivity.USERNAME);
        gameName = intent.getStringExtra("game");
        try {
            gameFactory = (GameFactory) GameCentreActivity.getFactoryClass(gameName).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        TextView gameTitle = findViewById(R.id.game_name);
        gameTitle.setText(gameName);
        newGame();
        showSavedGames();
        initScoreboard();
        infoDialog = new Dialog(this);
    }


    /**
     * Instantiates the scoreboard button
     */
    private void initScoreboard() {
        CardView scoreBoard = findViewById(R.id.score_board);
        scoreBoard.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent scoreboardIntent = new Intent(GameMenuActivity.this,
                        ScoreboardActivity.class);
                ArrayList<String> gameNames = new ArrayList<>(gameFactory.getGameNames());
                scoreboardIntent.putStringArrayListExtra(ScoreboardActivity.GAME_NAMES, gameNames);
                startActivity(scoreboardIntent);
            }
        });
    }

    /**
     * Shows information about game.
     *
     * @param v The given view from the onClick method.
     */
    public void showGameInstructions(View v) {
        infoDialog.setContentView(R.layout.dialog_instructions);
        TextView instructionsTextView = infoDialog.findViewById(R.id.instructionsTextView);
        switch(gameName){
            case "Sliding Tiles":
                instructionsTextView.setText(R.string.sliding_tiles_info);
                break;
            case "Minesweeper":
                instructionsTextView.setText(R.string.minesweeper_info);
                break;
            case "2048":
                instructionsTextView.setText(R.string.twentyfortyeight_info);
                break;
            default: instructionsTextView.setText(R.string.error);
        }
        TextView infoTxtClose;
        infoTxtClose = infoDialog.findViewById(R.id.infoTxtCloseInstructions);
        infoTxtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    /**
     * Shows a dialog with a list of all saved games.
     */
    private void showSavedGames() {
        CardView loadGame = findViewById(R.id.load_game);
        loadGame.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // When we need to load, we grab files from this array
                final Map<String, File> files = getSavedGames();
                // We display this array (as AlertDialogs can't display maps)
                final String gameNames[] = files.keySet().toArray(new String[0]);

                AlertDialog.Builder builder = new AlertDialog.Builder(GameMenuActivity.this);
                builder.setTitle("Choose save file");
                builder.setItems(gameNames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        File saveFile = files.get(gameNames[which]);
                        loadPopup(new GameStateIO(saveFile), saveFile, gameNames[which]);
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * Grabs the saved games for all the games in gameFactory.getGameNames
     *
     * @return A map of the name of the save file (for display purposes), and the File
     * object corresponding to the game.
     */
    private Map<String, File> getSavedGames() {
        final Map<String, File> files = new HashMap<>();
        for (String gameName : gameFactory.getGameNames()) {
            GameStateIO io = new GameStateIO(username, gameName, getFilesDir());
            for (File file : io.getAllSaves()) {
                files.put(file.getParentFile().getName() + "/" + file.getName(), file);
            }
        }
        return files;
    }

    /**
     * Instantiates the new game button.
     */
    private void newGame() {
        CardView newGame = findViewById(R.id.new_game);
        newGame.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button_clicked", "new Game");
                Intent intent = new Intent(GameMenuActivity.this, NewGameActivity.class);
                intent.putExtra(NewGameActivity.USERNAME, username);
                intent.putExtra(NewGameActivity.GAME_NAME, gameName);
                startActivity(intent);
            }
        });
    }

    /**
     * Shows the load game popup for a specific save file.
     *
     * @param io       The GameStateIO corresponding to the game to load.
     * @param file     The File object to load a GameState from.
     * @param fileName The name of the file for the title of the dialog.
     */
    private void loadPopup(final GameStateIO io, final File file, CharSequence fileName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(fileName)
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        loadFromFile(file.getName(), io);
                    }
                })
                .setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!canDeleteFile(file, io)) {
                            Toast.makeText(GameMenuActivity.this,
                                    "Could not delete file!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }

    /**
     * Loads a game from a file.
     *
     * @param file The file to load a save from.
     * @param io   The appropriate helper class to load the save file.
     */
    private void loadFromFile(String file, GameStateIO io) {
        Log.v("Loading file", file);
        GameState gameState;
        // We load the specified file into gameState.
        try {
            gameState = io.getGameSave(file);
        } catch (IOException | ClassNotFoundException e) {
            Toast.makeText(this, "Error loading save file.",
                    Toast.LENGTH_LONG).show();
            Log.e("login activity", "Loading error: " + e.toString());
            return;
        }
        Intent main = new Intent(GameMenuActivity.this, GameMainActivity.class);
        main.putExtra(GameMainActivity.FRAGMENT_CLASS, gameFactory.getGameFragmentClass());
        main.putExtra(GameMainActivity.GAME_STATE, gameState);
        main.putExtra(GameMainActivity.USERNAME, username);
        main.putExtra(GameMainActivity.FILE_NAME, file);
        startActivity(main);
    }

    /**
     * Deletes the file, if possible.
     *
     * @param file The file to delete.
     * @param io   The appropriate helper class to delete the save file.
     * @return true if the file was successfully deleted. false otherwise.
     */
    private boolean canDeleteFile(File file, GameStateIO io) {
        try {
            io.deleteSave(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
