package jakemcdowell.blobsapplication.bugs;

import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import jakemcdowell.blobsapplication.Game;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by jakemcdowell on 7/26/17.
 */

public class TeleportingBug extends Bug {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean beenThrough = false;
    private Runnable beeper;
    private ScheduledFuture beeperHandle;

    public TeleportingBug(View view, int health, ProgressBar hp, int totalKnockOuts) {
        super(view, health, hp, totalKnockOuts);
    }

    @Override
    public void move() {
        if (!beenThrough) {
            beenThrough = true;
            final Runnable beeper = new Runnable() {
                public void run() {
                    TeleportingBug.super.move();
                }
            };
            final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 1000, MILLISECONDS);
            scheduler.schedule(new Runnable() {
                public void run() {
                    beeperHandle.cancel(true);
                }
            }, 30, SECONDS);
            this.beeper = beeper;
            this.beeperHandle = beeperHandle;
        } else {
            super.move();
        }
    }

    @Override
    public void damageBug(Game game) {

        if (beenThrough) {
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
}
