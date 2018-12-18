package fall2018.csc207.menu.scoreboard;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fall2018.csc207.database.ScoreboardDBHandler;
import fall2018.csc207.slidingtiles.R;

/**
 * The class corresponding to the activity that creates the scoreboard.
 */
public class ScoreboardActivity extends AppCompatActivity {

    /**
     * Key for use in Intent extras.
     */
    public static final String GAME_NAMES = "GAME_NAMES";

    // The key is the game name, value.first is the username, value.second is the score.
    private final Map<String, ArrayList<ScoreboardEntry>> scores = new HashMap<>();

    /**
     * Creates the scoreboard given the GAME_NAMES ArrayList<String> intent.
     * Each element in GAME_NAMES should correspond to a game name in the Scores database.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        Iterable<String> gameNames = getIntent().getStringArrayListExtra(GAME_NAMES);
        ScoreboardDBHandler db = new ScoreboardDBHandler(this, null);
        for (String game : gameNames) {
            scores.put(game, getEntries(game, db));
        }
        setupScoreboardFragments();
    }

    /**
     * Setups the scoreboard fragments based on data obtained from scores.
     */
    private void setupScoreboardFragments() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // We create the fragment for each game given in the intent.
        for (Map.Entry<String, ArrayList<ScoreboardEntry>> entry : scores.entrySet()) {
            String title = entry.getKey();
            ArrayList<ScoreboardEntry> scoreList = entry.getValue();

            // We add the scores array into a Bundle, which we then send to the fragment.
            ScoreboardListFragment fragment = new ScoreboardListFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(ScoreboardListFragment.SCORES_BUNDLE_KEY, scoreList);
            fragment.setArguments(bundle);

            adapter.addFragment(fragment, title);
        }
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Gets entries to a specific game. They are sorted in descending order.
     *
     * @param gameName The name of the game that we want the entries of.
     *
     * @return The ArrayList of Pair<String, Integer>
     */
    private ArrayList<ScoreboardEntry> getEntries(String gameName, ScoreboardDBHandler dbHandler) {
        ArrayList<ScoreboardEntry> list = dbHandler.fetchDatabaseEntries(gameName);
        Collections.sort(list, Collections.<ScoreboardEntry>reverseOrder());
        return list;
    }

    /**
     * Bridges the data between the fragments under ViewPager and the ViewPager.
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        /**
         * Get the item at the nth position of the fragmentList.
         * @param position The nth position.
         * @return The item at the nth position of the fragmentList.
         */
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        /**
         * Get the size of the fragmentList.
         * @return The size of the fragmentList.
         */
        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * Add a fragment to the fragmentList.
         * @param fragment The fragment we want to add.
         * @param title The title of the fragment.
         */
        void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        /**
         * Get the title of the nth fragment.
         * @param position The n in question.
         * @return the title of the nth fragment.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
