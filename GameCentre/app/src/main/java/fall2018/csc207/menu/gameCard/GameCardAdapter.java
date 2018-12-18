package fall2018.csc207.menu.gameCard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.menu.GameMenuActivity;
import fall2018.csc207.slidingtiles.R;

public class GameCardAdapter extends RecyclerView.Adapter<GameCardAdapter.ViewHolder> {

    /**
     * The user's username
     */
    private final String username;

    /**
     * The Gamecards which will be shown in GameCentreActivity
     */
    private final List<GameCardItem> gameCardItemList;

    /**
     * A GameCardAdapter.
     *
     * @param gameCardItemList A list of gameCards to be displayed.
     * @param username The user's name.
     */
    public GameCardAdapter(List<GameCardItem> gameCardItemList, String username) {
        this.gameCardItemList = gameCardItemList;
        this.username = username;
    }

    /**
     * Inflate the LayoutInflater with game_card_item.
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType the type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_card_item,
                parent, false);
        return new ViewHolder(v);
    }

    /**
     * Displays the GameCardItem at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at
     *              the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GameCardItem current = gameCardItemList.get(position);
        holder.bind(current);
    }

    /**
     * Get the number of game cards.
     * @return The number of game cards.
     */
    @Override
    public int getItemCount() {
        return gameCardItemList.size();
    }

    /**
     * Custom view holder that will bind the information to the desired view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Views to be updated.
         */
        final TextView gameScore;
        final TextView gameTitle;
        final ImageView gameImage;
        private final CardView cardView;

        /**
         * Object we are clicking to change to that game.
         */
        private ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.gameCardItem);
            gameTitle = itemView.findViewById(R.id.gameName);
            gameScore = itemView.findViewById(R.id.gameScore);
            gameImage = itemView.findViewById(R.id.gameImage);
        }

        /**
         * Set the text and image of the GameCardItem.
         *
         * @param current The GameCardItem we are setting the values of.
         */
        private void bind(final GameCardItem current) {
            gameTitle.setText(current.getGameTitle());
            gameScore.setText("HIGHSCORE: " + String.valueOf(current.getScore()));
            gameImage.setImageDrawable(current.getGameImage());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), GameMenuActivity.class);
                    intent.putExtra(GameMainActivity.USERNAME, username);
                    intent.putExtra("game", current.getGameTitle());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
