package fall2018.csc207.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

import fall2018.csc207.game.GameFactory;
import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.game.GameState;
import fall2018.csc207.game.GameStateIO;
import fall2018.csc207.slidingtiles.R;


/**
 * The menu for new games.
 */
public class NewGameActivity extends AppCompatActivity {

    public static final String GAME_NAME = "NAME";
    public static final String USERNAME = "USERNAME";

    /**
     * The factory we will use for this game.
     */
    private GameFactory gameFactory;
    /**
     * The user that opened this activity.
     */
    private String username;
    /**
     * The name of the game.
     */
    private String gameName;
    /**
     * Determines whether or not unlimited undos are toggled on or not.
     */
    private Switch infUndoSwitch;
    /**
     * The Seekbar for the user choosing the value of undos.
     */
    private EditText undoPicker;

    private TextView fileName;

    /**
     * Called when we create a NewGameActivity.
     *
     * @param savedInstanceState The activity's previously saved state, contained in a bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent intent = getIntent();

        fileName = findViewById(R.id.fileName);

        gameName = intent.getStringExtra(GAME_NAME);
        username = intent.getStringExtra(USERNAME);
        try {
            Class factoryClass = GameCentreActivity.getFactoryClass(gameName);
            gameFactory = (GameFactory) factoryClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        setupUndoOptions();
        setupSettings();
        Button startButton = findViewById(R.id.startGame);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
    }

    /**
     * Create the seek bar and update it as its value changes.
     */
    private void setupUndoOptions() {
        undoPicker = findViewById(R.id.undoPicker);
        infUndoSwitch = findViewById(R.id.infiniteUndo);

        undoPicker.setText("0", TextView.BufferType.EDITABLE);
        setSeekbarState(!infUndoSwitch.isChecked());
        infUndoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSeekbarState(!infUndoSwitch.isChecked());
            }
        });

        //Minesweeper has no undo function, we dont want to see undo options.
        if (gameName.equals("Minesweeper")){
            findViewById(R.id.infUndoRow).setVisibility(View.GONE);
            findViewById(R.id.allowedUndosRow).setVisibility(View.GONE);
        }
    }

    /**
     * Enable or disable the seekbar
     *
     * @param state The state we change the seekbar to. Enabled = true.
     */
    private void setSeekbarState(boolean state) {
        final TableRow seekbar = findViewById(R.id.allowedUndosRow);
        seekbar.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    /**
     * Initializes the buttons and displays them on activity_new_game.xml
     */
    private void setupSettings() {
        LinearLayout screenView = findViewById(R.id.settings);
        for (final GameFactory.Setting setting : gameFactory.getSettings()) {
            createItem(setting, screenView);
        }
    }

    /**
     * Creates a menu item given a setting.
     *
     * @param setting The setting to generate a menu item for.
     * @param layout  The layout to put the item under.
     */
    private void createItem(final GameFactory.Setting setting,
                            LinearLayout layout) {
        View v = View.inflate(this, R.layout.new_game_activity_item, layout);

        TextView label = v.findViewById(R.id.label);
        Spinner dropdown = v.findViewById(R.id.spinner);

        // We change the IDs so that we don't retrieve the same ones for the next
        // call to createItem
        label.setId(R.id.label + 1);
        dropdown.setId(R.id.spinner + 1);

        label.setText(setting.getName());

        SpinnerAdapter dropdownAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                setting.getPossibleValues());

        dropdown.setAdapter(dropdownAdapter);
        dropdown.setSelection(setting.getCurrentValueIndex());

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Called when a spinner item is selected.
             * @param parentView The view where the selection happened
             * @param selectedItemView The view within the adapter that was selected
             * @param position The position of the selected view in the adapter
             * @param id The row id of the selected item
             */
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {
                setting.setCurrentValue(position);
            }

            /**
             * Called when the selection disappears from view.
             * @param adapterView The parent that has no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /**
     * Starts the game with a state from gameFactory.
     */
    private void startGame() {
        Log.v("Start Game", gameName + " by user " + username);
        String undoPickerVal = undoPicker.getText().toString();
        String file = fileName.getText().toString();
        if (!isSettingsValid(undoPickerVal, file)){
            Snackbar.make(findViewById(R.id.settings),
                    "Invalid filename or undo value!",
                    Snackbar.LENGTH_LONG).show();
            return;
        }

        GameState state = gameFactory
                .getGameState(infUndoSwitch.isChecked() ? -1 : Integer.parseInt(undoPickerVal));

        Intent tmp = new Intent(NewGameActivity.this, GameMainActivity.class);
        tmp.putExtra(GameMainActivity.FRAGMENT_CLASS, gameFactory.getGameFragmentClass());
        tmp.putExtra(GameMainActivity.GAME_STATE, state);
        tmp.putExtra(GameMainActivity.USERNAME, username);
        tmp.putExtra(GameMainActivity.FILE_NAME, file);

        startActivity(tmp);
    }

    /**
     * Makes sure the filename and undo values are valid.
     *
     * @param undoPickerVal The current value of the undoPicker.
     * @param fileName The current filename
     */
    private boolean isSettingsValid(String undoPickerVal, String fileName) {
        if (!undoPickerVal.matches("^\\d+$") && !infUndoSwitch.isChecked())
            return false; // The undo value is invalid, and we need it (as we're not allowing inf undos)
        GameStateIO io = new GameStateIO(username,
                gameFactory.getGameState(
                        infUndoSwitch.isChecked() ? -1 : Integer.parseInt(undoPickerVal))
                        .getGameName(),
                getFilesDir());
        return isValidFileName(fileName, io);
    }

    /**
     * Determines if a file name is valid.
     * @param fileName The file name in question
     * @param io The object saving and loading
     * @return Whether the name is valid.
     */
    private boolean isValidFileName(String fileName, GameStateIO io) {
        return io.isValidUnusedFileName(fileName);
    }
}
