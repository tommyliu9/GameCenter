package fall2018.csc207.menu.gameCard;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameCardTest {
    @Test
    public void testGameCardItem() {
        Drawable draw = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {

            }

            @Override
            public void setAlpha(int i) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        GameCardItem game = new GameCardItem("Halo Reach", 3, draw);
        assert 3 == game.getScore();
        assert game.getGameTitle().equals("Halo Reach");
        assert game.getGameImage() == draw;
    }
    @Test
    public void testGameCardAdapter() {
        List gameCards = new ArrayList<GameCardItem>();
        Drawable draw = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {

            }

            @Override
            public void setAlpha(int i) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        GameCardItem game1 = new GameCardItem("Halo Reach", 3, draw);
        GameCardItem game2 = new GameCardItem("Call of Duty", 3, draw);
        gameCards.add(game1);
        gameCards.add(game2);
        GameCardAdapter adapt = new GameCardAdapter(gameCards, "BOB");
        assert adapt.getItemCount() == 2;

    }
}
