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
 * Created by kevin on 7/28/17.
 */

public class MovingBug extends Bug {

    private ScheduledFuture beeperHandle = null;

    private boolean lowX = false;
    private boolean highX = false;
    private boolean lowY = false;
    private boolean highY = false;
    private int steps = 0;

    public MovingBug(View view, int health, ProgressBar hp, int totalKnockOus) {
        super(view, health, hp, totalKnockOus);
    }

    @Override
    public void move() {
        if (beeperHandle == null) {
            super.move();
            final Runnable beeper = new Beeper();
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
            return 20;
        } else {
            return -20;
        }
    }

    public int getDirectionY() {
        if (lowY) {
            return 20;
        } else {
            return -20;
        }
    }

    private class Beeper implements Runnable {
        public void run() {
            if (getButton().getX() <= 50 || (lowX && getButton().getX() <= 600)) {
                lowX = true;
            } else if (getButton().getX() >= 600 || (highX && getButton().getX() >= 50)) {
                lowX = false;
            }
            if (getButton().getY() <= 220 || (lowY && getButton().getY() <= 1420)) {
                lowY = true;
            } else if (getButton().getY() >= 1420 || (highY && getButton().getY() > 220)) {
                lowY = false;
            }

            getButton().setX(getButton().getX() + getDirectionX());
            getButton().setY(getButton().getY() + getDirectionY());
            getHp().setX(getButton().getX() + 40);
            getHp().setY(getButton().getY() - 40);
            steps++;
        }
    }
}
