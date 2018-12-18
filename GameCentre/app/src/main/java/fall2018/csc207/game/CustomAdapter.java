package fall2018.csc207.game;

/*
Taken from:
https://github.com/DaveNOTDavid/
sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java

This Class is an overwrite of the Base Adapter class
It is designed to aid setting the button sizes and positions in the GridView
 */


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    /**
     * A list of tiles for a game.
     */
    private final List mTiles;
    private final int mColumnWidth;
    private final int mColumnHeight;

    /**
     * A CustomAdapter
     *
     * @param buttons      A list of the tile buttons for the slidingTiles game.
     * @param columnWidth  The width of a column.
     * @param columnHeight The height of a column.
     */
     public CustomAdapter(List buttons, int columnWidth, int columnHeight) {
        mTiles = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    /**
     * Get the number of tiles.
     * @return The number of tiles
     */
    @Override
    public int getCount() {
        return mTiles.size();
    }

    /**
     * Get the nth tile
     * @param position The n in question
     * @return The nth tile.
     */
    @Override
    public Object getItem(int position) {
        return mTiles.get(position);
    }

    /**
     * Get the ID of item at position
     * @param position The position of the item
     * @return The ID of the item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     * @param position The position of the item within the data set
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object tile;

        if (convertView == null) {
            tile = mTiles.get(position);
        } else {
            if (convertView instanceof Button)
                tile = convertView;
            else
                tile = convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        if (tile instanceof Button){
            ((Button)tile).setLayoutParams(params);
            return (Button)tile;
        }
        else {
            ((TextView)tile).setLayoutParams(params);
            return (TextView)tile;
        }



    }
}
