package five.team.tttdenysjonathan;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by j0nbiz on 9/24/2016.
 */

public class Tile {
    private int owner = 0;
    private ImageButton button;

    public Tile(ImageButton button) {
        this.button = button;
        button.setClickable(false);
    }

    public ImageButton getButton() {
        return button;
    }

    public void setButton(ImageButton button) {
        this.button = button;
    }

    public int getOwner() {
        return owner;
    }

    public void setX() {
        button.setImageResource(R.drawable.cross);
        button.setClickable(false);
        this.owner = 1;
    }

    public void setO() {
        button.setImageResource(R.drawable.ooo);
        button.setClickable(false);
        this.owner = 2;
    }

    public boolean isOwned() {
        if (owner != 0) {
            return true;
        }

        return false;
    }

    public boolean isX() {
        if (owner == 1) {
            return true;
        }

        return false;
    }

    public boolean isO() {
        if (owner == 2) {
            return true;
        }

        return false;
    }

    public void reset() {
        this.owner = 0;
        button.setImageResource(R.drawable.emptysquare);
        button.setClickable(false);
    }
}
