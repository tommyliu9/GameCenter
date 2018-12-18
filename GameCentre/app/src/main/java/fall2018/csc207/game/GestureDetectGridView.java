package fall2018.csc207.game;

/*
Adapted from:
https://github.com/DaveNOTDavid/
sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

import fall2018.csc207.minesweeper.MinesweeperController;

/**
 * Detects gestures for our grid based games.
 */
public class GestureDetectGridView extends GridView {
    private static final int SWIPE_MIN_DISTANCE = 100;
    private GestureDetector gDetector;
    private boolean mFlingConfirmed;
    private float mTouchX;
    private float mTouchY;
    private fall2018.csc207.game.BoardController boardController;


    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initializes GestureDetectGridView.
     *
     * @param context The context for this grid view.
     */
    private void init(final Context context) {
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                boardController.processTap(position);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (boardController instanceof MinesweeperController){
                    int position = GestureDetectGridView.this.pointToPosition
                            (Math.round(e.getX()), Math.round(e.getY()));
                    ((MinesweeperController) boardController).flagTile(position);
                }
            }
        });
    }

    /**
     * Called by Android whenever a touch event occurs.
     * @param ev The MotionEvent that triggered this function call.
     * @return Whether we use the event or not.
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * Called whenever this GestureGridView is touched.
     * @param ev The details about this event.
     * @return Whether we used the event or not.
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            performClick();
        }
        return gDetector.onTouchEvent(ev);
    }

    /**
     * Called whenever the user clicks.
     * @return Whether we did anything with the click.
     */
    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     * Sets the BoardController for this GridView.
     * @param boardController The BoardController to use.
     */
    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }
}