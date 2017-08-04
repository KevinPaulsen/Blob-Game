package jakemcdowell.blobsapplication.bugs;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import jakemcdowell.blobsapplication.Constants;
import jakemcdowell.blobsapplication.Game;
import jakemcdowell.blobsapplication.PlayerData;
import jakemcdowell.blobsapplication.R;

/**
 * Created by kevin on 7/25/17.
 */

public class Bug {

    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    private final View button;
    private final ProgressBar hp;

    private int timesKnockedOut;
    public int damage;             // number of clicks
    private int health;             // Number of times clicked till KO
    private int totalKnockOuts;     // number of KO's until death


    public Bug(View view, int health, ProgressBar hp, int kOsPerDeath) {
        view.setBackgroundResource(R.drawable.normalbuganimation);
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
        button.setX((float) ((Math.random() * 575) + 50));
        button.setY((float) ((Math.random() * 1200) + 220));
        hp.setX(button.getX() + 40);
        hp.setY(button.getY() - 40);
    }

    private int getDiff(float val1, float val2) {
        return (int) (val1 - val2);
    }

    public int getDistance(Bug bug) {
        return (int) Math.sqrt(Math.pow(getDiff(getButton().getX(), bug.getButton().getX()), 2) +
                Math.pow(getDiff(getButton().getY(), bug.getButton().getY()), 2));
    }

    public void moveOffScreen() {
        button.setY(70000);
        hp.setY(10000);
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

    public View getButton() {
        return button;
    }

    public void startAnimation() {

        Drawable draw = button.getBackground();
        System.out.println("DRAW: " + draw);
        if (draw instanceof AnimationDrawable) {
            AnimationDrawable ani = (AnimationDrawable) draw;
            if (ani != null) {
                ani.start();
            }
        }
    }

    public void damageBug(Game game) {
        move();
        damage += PlayerData.damageIncreasePerLevel.get(1).get(PlayerData.damageIncreaseLevel);
        decreaseHpProgressBar();
        if (isKnockedOut()) {
            resetAfterKnockedOut();
            game.updateLevelKnockOutProgressBar();

            // Check and sets bugsLeftToKill to how many bugs should die in this level
            if (isDead()) {
                moveOffScreen();
                game.addBugKilledInLevel();
            } else {
                pauseDeath();
            }
        }

        if (game.isAllDead()) {
            game.resetBugsToInitialState();
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

    public void pauseDeath() {
        moveOffScreen();

        if (getKnockOuts() != Constants.KOSPERDEATH) {
            getButton().postDelayed(new Runnable() {
                @Override
                public void run() {
                    move();
                }
            }, 500);
        }
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
