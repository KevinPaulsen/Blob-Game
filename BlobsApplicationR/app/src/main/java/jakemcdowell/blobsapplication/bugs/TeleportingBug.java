package jakemcdowell.blobsapplication.bugs;

import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.ScheduledFuture;

import jakemcdowell.blobsapplication.Constants;
import jakemcdowell.blobsapplication.Game;
import jakemcdowell.blobsapplication.PlayerData;
import jakemcdowell.blobsapplication.R;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by jakemcdowell on 7/26/17.
 */

public class TeleportingBug extends Bug {

    private boolean beenThrough = false;
    private Runnable beeper;
    private ScheduledFuture beeperHandle;

    public TeleportingBug(View view, int health, ProgressBar hp, int totalKnockOuts) {
        super(view, health, hp, totalKnockOuts);
        view.setBackgroundResource(R.drawable.teleportingbuganimation);
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

    public void pauseKnockout() {
        setBackgroundResource(R.drawable.deadbug);
        clickable = false;
        beeperHandle.cancel(true);

        if (getKnockOuts() != Constants.KOS_PER_DEATH) {
            button.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setBackgroundResource(R.drawable.teleportingbuganimation);
                    startAnimation();
                    clickable = true;
                    move();
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
                setBackgroundResource(R.drawable.teleportingbuganimation);
                startAnimation();
                clickable = true;
                if (g.isAllDead()) {
                    getGameActivity().allBugsDead();
                    g.resetBugsToInitialState();
                }
                beeperHandle.cancel(true);
                moveOffScreen();
            }
        }, 500);

    }

    @Override
    public void damageBug(Game game) {
        addDamage(PlayerData.damageIncreasePerLevel.get(1).get(PlayerData.damageIncreaseLevel));
        decreaseHpProgressBar();
        if (isKnockedOut()) {
            resetAfterKnockedOut();
            game.updateLevelKnockOutProgressBar();
            beeperHandle.cancel(true);
            beenThrough = false;

            // Check and sets bugsLeftToKill to how many bugs should die in this level
            if (isDead()) {
                pauseDeath(game);
                game.addBugKilledInLevel();
            } else {
                pauseKnockout();
            }
        } else {
            move();
        }
    }

    @Override
    public void setBugToInitialState() {
        if (beeperHandle != null) {
            beeperHandle.cancel(true);
        }
        super.setBugToInitialState();
    }
}
