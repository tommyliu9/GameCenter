package fall2018.csc207.twentyfortyeight;

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

/**
 * Detects gestures for our grid based games.
 */
public class GestureDetectGridView2048 extends GridView {
    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private GestureDetector gDetector;
    private boolean mFlingConfirmed;
    private float mTouchX;
    private float mTouchY;
    private TwentyFortyEightController controller;

    public GestureDetectGridView2048(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridView2048(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridView2048(Context context, AttributeSet attrs, int defStyleAttr) {
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
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) >
                                SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                controller.moveRight();
                            } else {
                                controller.moveLeft();
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) >
                            SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            controller.moveDown();
                        } else {
                            controller.moveUp();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
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
     * Sets the TwentyFortyEightController.
     *
     * @param controller The controller for this grid view to set.
     */
    public void setController(TwentyFortyEightController controller) {
        this.controller = controller;
    }
}