package fall2018.csc207.slidingtiles;

import org.junit.Before;
import org.junit.Test;

public class SlidingTilesTilesTest {
    private SlidingTilesTile tile;

    @Before
    public void setup() {
        tile = new SlidingTilesTile(1, R.drawable.tile_1);
    }

    @Test
    public void testGetBackground() {
        assert tile.getBackground() == R.drawable.tile_1;
    }

    @Test
    public void testGetId() {
        assert tile.getId() == 1;
    }

    @Test
    public void testCompareTo() {
        assert tile.compareTo(tile) == 0;
        assert tile.compareTo(new SlidingTilesTile(5, R.drawable.tile_1)) > 0;
        assert tile.compareTo(new SlidingTilesTile(0, R.drawable.tile_1)) < 0;
    }
}
