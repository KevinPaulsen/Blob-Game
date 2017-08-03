package jakemcdowell.blobsapplication.bugs;

import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import jakemcdowell.blobsapplication.Constants;
import jakemcdowell.blobsapplication.Game;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by kevin on 7/28/17.
 */

public class MovingBug extends Bug {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean beenThrough = false;
    private Runnable beeper;
    private ScheduledFuture beeperHandle;

    private boolean lowX = false;
    private boolean highX = false;
    private boolean lowY = false;
    private boolean highY = false;

    public MovingBug(View view, int health, ProgressBar hp, int totalKnockOus) {
        super(view, health, hp, totalKnockOus);
    }

    @Override
    public void move() {
        if (!beenThrough) {
            super.move();
            beenThrough = true;
            final Runnable beeper = new Runnable() {
                public void run() {

                    if (getButton().getX() <= 50 || (lowX && getButton().getX() <= 775)) {
                        lowX = true;
                        highX = false;
                    } else if (getButton().getX() >= 775 || (highX && getButton().getX() >= 50)) {
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
            };
            final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, Constants.speed, MILLISECONDS);
            scheduler.schedule(new Runnable() {
                public void run() {
                    beeperHandle.cancel(true);
                }
            }, 35, SECONDS);
            this.beeper = beeper;
            this.beeperHandle = beeperHandle;
        }
    }

    @Override
    public void damageBug(Game game) {

        if (!beenThrough) {
            move();
        }
        addDamage(1);
        decreaseHpProgressBar();
        if (isKnockedOut()) {
            resetAfterKnockedOut();
            game.updateLevelKnockOutProgressBar();
            beeperHandle.cancel(true);
            beenThrough = false;

            // Check and sets bugsLeftToKill to how many bugs should die in this level
            if (isDead()) {
                moveOffscreen();
                game.addBugKilledInLevel();
            } else {
                pauseDeath();
            }
        }

        if (game.isAllDead()) {
            game.endLevel();
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
}
