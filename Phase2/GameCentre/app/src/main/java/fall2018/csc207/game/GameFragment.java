package fall2018.csc207.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

public abstract class GameFragment<S extends GameState, M extends GameController>
        extends Fragment implements Observer {

    /**
     * Key for GameState.
     */
    public static final String GAME_STATE = "STATE";

    /**
     * The game state.
     */
    protected S state;

    /**
     * The game manager.
     */
    protected M gameManager;

    /**
     * This view.
     */
    protected View thisView;

    /**
     * Called by Android when we want to create this view. Implemented by subclasses because they
     * determine how to setup their view (all games are different).
     *
     * @param inflater           Converts layout XML into a View.
     * @param container          The container for the View.
     * @param savedInstanceState A previously saved instance, if available.
     * @return The created View.
     */
    @Override
    public abstract View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * Called by Android when this fragment is created. This is called before onCreateView,
     * so make sure no graphic initialization is done here!
     * @param savedInstanceState A previously saved instance of this activity, if available.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            state = (S) getArguments().getSerializable(GAME_STATE);
            if (state != null)
                state.addObserver(this);
        }
    }

    /**
     * This method is called when the observed object is changed.
     * @param o The observable object.
     * @param arg an argument passed to the notifyObservers method.
     */
    @Override
    public abstract void update(Observable o, Object arg);
}
