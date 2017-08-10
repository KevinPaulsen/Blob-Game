package jakemcdowell.blobsapplication.bugs;

import android.view.View;
import android.widget.ProgressBar;

import jakemcdowell.blobsapplication.R;

/**
 * Created by jakemcdowell on 7/26/17.
 */

public class SmallBug extends Bug {

    public SmallBug(View view, int health, ProgressBar hp, int totalKnockOuts) {
        super(view, health, hp, totalKnockOuts);
        view.setBackgroundResource(R.drawable.smallbuganimation);
    }

    @Override
    public int getHealthBarXOffset() {
        return -5;
    }

    @Override
    public int getHealthBarYOffset() {
        return -30;
    }

    public void move() {
        setX((float) ((Math.random() * 575) + 50));
        setY((float) ((Math.random() * 1200) + 220));
    }

}
