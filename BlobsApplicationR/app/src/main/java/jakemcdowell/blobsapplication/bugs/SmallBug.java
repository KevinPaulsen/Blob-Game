package jakemcdowell.blobsapplication.bugs;

import android.content.res.Resources;
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
        int barOffsetX = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.0046);
        return -barOffsetX;
    }

    @Override
    public int getHealthBarYOffset() {
        int barOffsetY = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.0156);
        return -barOffsetY;    }
}
