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

    public void move() {
        getButton().setX((float) ((Math.random() * 575) + 50));
        getButton().setY((float) ((Math.random() * 1200) + 220));
        getHp().setX(getButton().getX() - 5);
        getHp().setY(getButton().getY() - 30);
    }

}
