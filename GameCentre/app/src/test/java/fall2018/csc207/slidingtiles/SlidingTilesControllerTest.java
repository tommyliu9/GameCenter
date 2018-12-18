package fall2018.csc207.slidingtiles;

import org.junit.Before;
import org.junit.Test;

public class SlidingTilesControllerTest {
    private SlidingTilesController controller;

    /**
     * Setups controller for each test.
     */
    @Before
    public void setup() {
        SlidingTilesFactory factory = new SlidingTilesFactory();

        // Add a 3x3 board (as that is the default) to a new controller
        controller = new SlidingTilesController(factory.getGameState(5));
    }

    /**
     * Tests if games are properly updated on calls to updateGame.
     */
    @Test
    public void testUpdateGame() {
        controller.updateGame(7);
        assert controller.getGameState().getTile(2, 1).getId() == 9; // Should be blank
    }

    /**
     * Tests if isValidTap determines if taps are valid correctly.
     */
    @Test
    public void testIsValidTap() {
        assert !controller.isValidTap(6); // First move, blank should be adajcent to 7
        assert controller.isValidTap(7);
        controller.updateGame(7);
        assert controller.isValidTap(8);
    }
}
