package jakemcdowell.blobsapplication.bugs;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import jakemcdowell.blobsapplication.Constants;
import jakemcdowell.blobsapplication.Game;
import jakemcdowell.blobsapplication.GameActivity;
import jakemcdowell.blobsapplication.PlayerData;
import jakemcdowell.blobsapplication.R;

/**
 * Created by kevin on 7/25/17.
 */

public class Bug {

    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    public final View button;
    private final ProgressBar hp;
    public boolean clickable = true;

    private int timesKnockedOut;
    public int damage;             // number of clicks
    private int health;             // Number of times clicked till KO
    private int totalKnockOuts;     // number of KO's until death


    public Bug(View view, int health, ProgressBar hp, int kOsPerDeath) {
        this.button = view;
        this.health = health;
        this.hp = hp;
        this.totalKnockOuts = kOsPerDeath;
        this.timesKnockedOut = 0;
        this.damage = 0;
        resetHpProgressBar();
        view.setBackgroundResource(R.drawable.normalbuganimation);

    }

    public void setBugToInitialState() {
        moveOffScreen();
        resetHpProgressBar();
        this.timesKnockedOut = 0;
        this.damage = 0;
    }

    public void move() {
        float smallNumX = ((float) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.046));
        float largeNumX = ((float) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.5787));
        float smallNumY = ((float) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.11458));
        float largeNumY = ((float) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.739583));

        setX((float) ((Math.random() * (largeNumX - smallNumX) + smallNumX)));
        setY((float) ((Math.random() * (largeNumY - smallNumY)) + smallNumY));
    }

    public void moveOffScreen() {
       setY(70000);
    }

    public void decreaseHpProgressBar() {
        if (damage > health) {
            damage = health;
        }
        int newProgressValue = (int)(((double)(getHealth() - getDamageOnBug()) / getHealth()) * 100);
        hp.setProgress(newProgressValue);
    }

    public ProgressBar getHp() {
        return hp;
    }

    public void resetHpProgressBar() {
        hp.setProgress(100);
    }

    public float getX() {
        return button.getX();
    }

    public float getY() {
        return button.getY();
    }

    public void setX(float x) {
        button.setX(x);
        hp.setX(x + getHealthBarXOffset());
    }

    public void setY(float y) {
        button.setY(y);
        hp.setY(y + getHealthBarYOffset());
    }

    public int getHealthBarXOffset() {
        int barOffsetX = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.03103);
        return barOffsetX;
    }

    public int getHealthBarYOffset() {
        int barOffsetY = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.02083);
        return -barOffsetY;
    }

    public GameActivity getGameActivity() {
        return (GameActivity) button.getContext();
    }

    public AnimationDrawable getBackground() {
        return (AnimationDrawable) button.getBackground();
    }

    public boolean hasButton(View view) {
        return button.equals(view);
    }

    public void setBackgroundResource(int id) {
        button.setBackgroundResource(id);
    }

    public void startAnimation() {
        Drawable draw = button.getBackground();
        if (draw instanceof AnimationDrawable) {
            AnimationDrawable ani = (AnimationDrawable) draw;
            if (ani != null) {
                ani.start();
            }
        }
    }

    public void damageBug(Game game) {
        if (clickable) {
            damage += PlayerData.damageIncreasePerLevel.get(1).get(PlayerData.damageIncreaseLevel);
            decreaseHpProgressBar();
            if (isKnockedOut()) {
                resetAfterKnockedOut();
                game.updateLevelKnockOutProgressBar();

                // Check and sets bugsLeftToKill to how many bugs should die in this level
                if (isDead()) {
                    game.addBugKilledInLevel();
                    pauseDeath(game);
                } else {
                    pauseKnockout();
                }
            } else {
                move();
            }
        }
    }

    public void resetAfterKnockedOut() {
        damage = 0;
        hp.setProgress(100);
        timesKnockedOut++;
    }
    public boolean isKnockedOut() {
        if (damage == health) {
            return true;
        }
        return false;
    }

    public void pauseKnockout() {
        button.setBackgroundResource(R.drawable.deadbug);
        clickable = false;

        if (getKnockOuts() != Constants.KOS_PER_DEATH) {
            button.postDelayed(new Runnable() {
                @Override
                public void run() {
                    move();
                    button.setBackgroundResource(R.drawable.normalbuganimation);
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
                button.setBackgroundResource(R.drawable.normalbuganimation);
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

    public boolean isDead() {
        return timesKnockedOut == totalKnockOuts;
    }

    public int getKnockOuts() {
        return timesKnockedOut;
    }

    public int getHealth() {
        return health;
    }

    public int getDamageOnBug() {
        return damage;
    }

    public void addHealth(int moreHealth) {
        health += moreHealth;
    }

    public int getTotalKnockOuts() {
        return totalKnockOuts;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }
}
