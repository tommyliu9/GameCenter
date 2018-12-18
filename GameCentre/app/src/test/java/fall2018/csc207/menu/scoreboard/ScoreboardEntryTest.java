package fall2018.csc207.menu.scoreboard;

import org.junit.Before;
import org.junit.Test;

public class ScoreboardEntryTest {
    private ScoreboardEntry entry;

    /**
     * Setups entry.
     */
    @Before
    public void setup() {
        entry = new ScoreboardEntry("User", "Sliding Tiles 3x3", 10);
    }

    /**
     * Tests getting name.
     */
    @Test
    public void testGetName() {
        assert entry.getName().equals("User");
    }

    /**
     * Tests getting score.
     */
    @Test
    public void testGetScore() {
        assert entry.getScore() == 10;
    }

    /**
     * Tests getting game.
     */
    @Test
    public void testGetGame() {
        assert entry.getGame().equals("Sliding Tiles 3x3");
    }

    /**
     * Tests setting game.
     */
    @Test
    public void testSetGame() {
        entry.setGame("Minesweeper 5x5");
        assert entry.getGame().equals("Minesweeper 5x5");
    }

    /**
     * Tests the comparesTo method.
     */
    @Test
    public void testCompareTo() {
        ScoreboardEntry entry2 = new ScoreboardEntry("User2", "Sliding Tiles 3x3", 11);
        assert entry.compareTo(entry2) < 0;

        ScoreboardEntry entry3 = new ScoreboardEntry("User3", "Sliding Tiles 3x3", 9);
        assert entry.compareTo(entry3) > 0;

        ScoreboardEntry entry4 = new ScoreboardEntry("User4", "Sliding Tiles 3x3", 10);
        assert entry.compareTo(entry4) == 0;
    }
}
