package jakemcdowell.blobsapplication.bugs;

import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import jakemcdowell.blobsapplication.Constants;
import jakemcdowell.blobsapplication.Game;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by jakemcdowell on 7/26/17.
 */

public class FireBug extends Bug {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(8);
    private ScheduledFuture beeperHandle = null;

    private boolean lowX = false;
    private boolean highX = false;
    private boolean lowY = false;
    private boolean highY = false;

    public FireBug(View view, int health, ProgressBar hp, int totalKnockOus) {
        super(view, health, hp, totalKnockOus);
    }

    @Override
    public void move() {
        if (beeperHandle == null) {
            super.move();
            final Runnable beeper = new FireBug.Beeper();
            this.beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, Constants.MOVINGBUGSPEED, MILLISECONDS);
        }
    }

    @Override
    public void damageBug(Game game) {
        super.damageBug(game);
    }

    @Override
    public void moveOffScreen() {
        super.moveOffScreen();
        if (beeperHandle != null) {
            beeperHandle.cancel(true);
            beeperHandle = null;
        }
    }

    public int getDirectionX() {
        if (lowX) {
            return 10;
        } else {
            return -10;
        }
    }

    public int getDirectionY() {
        if (lowY) {
            return 10;
        } else {
            return -10;
        }
    }

    private class Beeper implements Runnable {
        public void run() {
            if (getButton().getX() <= 50 || (lowX && getButton().getX() <= 600)) {
                lowX = true;
                highX = false;
            } else if (getButton().getX() >= 600 || (highX && getButton().getX() >= 50)) {
                lowX = false;
                highX = true;
            }
            if (getButton().getY() <= 220 || (lowY && getButton().getY() <= 1420)) {
                lowY = true;
                highY = false;
            } else if (getButton().getY() >= 1420 || (highY && getButton().getY() > 220)) {
                lowY = false;
                highY = true;
            }

            getButton().setX(getButton().getX() + getDirectionX());
            getButton().setY(getButton().getY() + getDirectionY());
            getHp().setX(getButton().getX() + 40);
            getHp().setY(getButton().getY() - 40);
        }
    }
}
