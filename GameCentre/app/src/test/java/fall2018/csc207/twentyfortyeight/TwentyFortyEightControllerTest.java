package fall2018.csc207.twentyfortyeight;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

public class TwentyFortyEightControllerTest {

    @Test
    public void moveLeftTest(){
        TwentyFortyEightTile tile1 = new TwentyFortyEightTile(2);
        TwentyFortyEightTile tile2 = new TwentyFortyEightTile(2);
        TwentyFortyEightTile tile3 = new TwentyFortyEightTile(8);
        TwentyFortyEightTile tile4 = new TwentyFortyEightTile(16);
        TwentyFortyEightTile[] arr = {tile1, tile2, tile3, tile4};
        ArrayList<TwentyFortyEightTile> tiles = new ArrayList<>(Arrays.asList(arr));
        TwentyFortyEightBoard board = new TwentyFortyEightBoard(tiles ,2);
        TwentyFortyEightController controller = new TwentyFortyEightController(board);

        controller.moveLeft();
        assert board.getTile(0,0).value == 4;
    }
    @Test
    public void moveUpTest(){
        TwentyFortyEightTile tile1 = new TwentyFortyEightTile(2);
        TwentyFortyEightTile tile2 = new TwentyFortyEightTile(4);
        TwentyFortyEightTile tile3 = new TwentyFortyEightTile(2);
        TwentyFortyEightTile tile4 = new TwentyFortyEightTile(16);
        TwentyFortyEightTile[] arr = {tile1, tile2, tile3, tile4};
        ArrayList<TwentyFortyEightTile> tiles = new ArrayList<>(Arrays.asList(arr));
        TwentyFortyEightBoard board = new TwentyFortyEightBoard(tiles ,2);
        TwentyFortyEightController controller = new TwentyFortyEightController(board);

        controller.moveUp();
        assert board.getTile(0,0).value == 4;
    }
    @Test
    public void moveDownTest(){
        TwentyFortyEightTile tile1 = new TwentyFortyEightTile(2);
        TwentyFortyEightTile tile2 = new TwentyFortyEightTile(4);
        TwentyFortyEightTile tile3 = new TwentyFortyEightTile(2);
        TwentyFortyEightTile tile4 = new TwentyFortyEightTile(16);
        TwentyFortyEightTile[] arr = {tile1, tile2, tile3, tile4};
        ArrayList<TwentyFortyEightTile> tiles = new ArrayList<>(Arrays.asList(arr));
        TwentyFortyEightBoard board = new TwentyFortyEightBoard(tiles ,2);
        TwentyFortyEightController controller = new TwentyFortyEightController(board);

        controller.moveDown();
        assert board.getTile(1,0).value == 4;
    }
    @Test
    public void moveRightTest(){
        TwentyFortyEightTile tile1 = new TwentyFortyEightTile(2);
        TwentyFortyEightTile tile2 = new TwentyFortyEightTile(2);
        TwentyFortyEightTile tile3 = new TwentyFortyEightTile(8);
        TwentyFortyEightTile tile4 = new TwentyFortyEightTile(16);
        TwentyFortyEightTile[] arr = {tile1, tile2, tile3, tile4};
        ArrayList<TwentyFortyEightTile> tiles = new ArrayList<>(Arrays.asList(arr));
        TwentyFortyEightBoard board = new TwentyFortyEightBoard(tiles ,2);
        TwentyFortyEightController controller = new TwentyFortyEightController(board);

        controller.moveRight();
        assert board.getTile(0,1).value == 4;
    }
}
