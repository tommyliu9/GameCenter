package fall2018.csc207.twentyfortyeight;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;

import fall2018.csc207.game.CustomAdapter;
import fall2018.csc207.game.GameFragment;
import fall2018.csc207.slidingtiles.R;


/**
 * Fragment class made to display the game in the GameMainActivity
 */
public class TwentyFortyEightFragment extends GameFragment<TwentyFortyEightBoard, TwentyFortyEightController> {


    /**
     * The buttons (tiles) that can be clicked on to be moved.
     */
    private ArrayList<TextView> tileTextViews;

    /**
     * Grid View and calculated column height and width based on device size.
     */
    private GestureDetectGridView2048 gridView;
    private static int columnWidth, columnHeight;
    private int dimensions;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    private void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileTextViews, columnWidth, columnHeight));
    }

    /**
     * Called by Android when we want to create this view.
     *
     * @param inflater           Converts layout XML into a View.
     * @param container          The container for the View.
     * @param savedInstanceState A previously saved instance, if available.
     * @return The created View.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_twenty_forty_eight, container, false);
        createTile(thisView.getContext());

        // Add View to activity
        gridView = thisView.findViewById(R.id.grid);
        gridView.setNumColumns(dimensions);
        gridView.setController(gameManager);


        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        TwentyFortyEightFragment.columnWidth = displayWidth / dimensions;
                        TwentyFortyEightFragment.columnHeight = displayHeight / dimensions;

                        display();
                    }
                });

        return thisView;
    }

    /**
     * Called by Android when this fragment is created. This is called before onCreateView,
     * so make sure no graphic initialization is done here!
     *
     * @param savedInstanceState A previously saved instance of this activity, if available.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Pulls the type of SlidingTilesController to be initialized
        gameManager = new TwentyFortyEightController(this.state);
        dimensions = (int) Math.sqrt(state.getDimensions());
        state = gameManager.getGameState();
        state.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTile(Context context) {
        TwentyFortyEightBoard board = gameManager.getGameState();
        tileTextViews = new ArrayList<>();
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                TextView tmp = new TextView(context);
                TwentyFortyEightTile twentyFortyEightTile = board.getTile(row, col);
                setTiles(tmp, twentyFortyEightTile);
                this.tileTextViews.add(tmp);
            }
        }

    }

    /**
     * Setup a Tile.
     * @param tmp The TextView being modified.
     * @param twentyFortyEightTile The tile corresponding to the TextView.
     */
    private void setTiles(TextView tmp, TwentyFortyEightTile twentyFortyEightTile) {
        tmp.setBackgroundColor(getResources().getColor(twentyFortyEightTile.getBackground(), null));
        String text = twentyFortyEightTile.getValue() == 0 ? "" : Integer.toString(twentyFortyEightTile.getValue());
        tmp.setText(text);
        tmp.setTextColor(getResources().getColor(R.color.textDark, null));
        tmp.setTextSize(36);
        tmp.setGravity(Gravity.CENTER);
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        TwentyFortyEightBoard board = gameManager.getGameState();
        int nextPos = 0;
        for (TextView tile : tileTextViews) {
            int row = nextPos / dimensions;
            int col = nextPos % dimensions;
            TwentyFortyEightTile cur = board.getTile(row, col);
            setTiles(tile, cur);

            nextPos++;
        }
    }
}
