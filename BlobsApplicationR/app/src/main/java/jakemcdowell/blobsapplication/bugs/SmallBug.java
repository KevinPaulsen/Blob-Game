package jakemcdowell.blobsapplication.bugs;

import android.content.res.Resources;
import android.view.View;
import android.widget.ProgressBar;

import jakemcdowell.blobsapplication.Constants;
import jakemcdowell.blobsapplication.Game;
import jakemcdowell.blobsapplication.R;

/**
 * Created by jakemcdowell on 7/26/17.
 */

public class SmallBug extends Bug {

    public SmallBug(View view, int health, ProgressBar hp, int totalKnockOuts) {
        super(view, health, hp, totalKnockOuts);
        view.setBackgroundResource(R.drawable.smallbuganimation);
    }

    public void pauseKnockout() {
        setBackgroundResource(R.drawable.deadbug);
        clickable = false;

        if (getKnockOuts() != Constants.KOS_PER_DEATH) {
            button.postDelayed(new Runnable() {
                @Override
                public void run() {
                    move();
                    setBackgroundResource(R.drawable.smallbuganimation);
                    startAnimation();
                    clickable = true;
                }
            }, 500);
        }
    }

    public void pauseDeath(final Game g) {
        button.setBackgroundResource(R.drawable.deadbug);
        clickable = false;

        button.postDelayed(new Runnable() {
            @Override
            public void run() {
                setBackgroundResource(R.drawable.smallbuganimation);
                startAnimation();
                clickable = true;
                if (g.isAllDead()) {
                    getGameActivity().allBugsDead();
                    g.resetBugsToInitialState();
                }
                moveOffScreen();
            }
        }, 500);

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
