package jakemcdowell.blobsapplication.bugs;

import android.view.View;
import android.widget.ProgressBar;

import jakemcdowell.blobsapplication.Game;
import jakemcdowell.blobsapplication.R;

/**
 * Created by kevin on 7/25/17.
 */

public class Bug {

    private final View button;
    private final ProgressBar hp;

    private int timesKnockedOut;
    private int damage;             // number of clicks
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
    }

    public void resetBugToInitialState() {
        moveOffscreen();
        resetHpProgressBar();
        this.timesKnockedOut = 0;
        this.damage = 0;
    }

    public void move() {
        button.setX((float) ((Math.random() * 725) + 50));
        button.setY((float) ((Math.random() * 1200) + 220));
        hp.setX(button.getX() + 40);
        hp.setY(button.getY() - 40);
    }

    public void moveOffscreen() {
        button.setY(10000);
        hp.setY(10000);
    }

    public void decreaseHpProgressBar() {
        int newProgressValue = (int)(((double)(getHealth() - getDamageOnBug()) / getHealth()) * 100);
        hp.setProgress(newProgressValue);
    }

    public void resetHpProgressBar() {
        hp.setProgress(100);
    }

    public View getButton() {
        return button;
    }

    public void damageBug(Game game) {
        move();
        damage++;
        decreaseHpProgressBar();
        if (isKnockedOut()) {
            resetAfterKnockedOut();
            game.updateLevelKnockOutProgressBar();

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
        moveOffscreen();

        if (getKnockOuts() != 5) {
            getButton().postDelayed(new Runnable() {
                @Override
                public void run() {
                    move();
                }
            }, 700);
        }
    }

    public boolean isDead() {
        return timesKnockedOut == totalKnockOuts;
    }

    public void died() {
        timesKnockedOut++;
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

    public void setHealth(int health) {
        this.health = health;
    }

    public void setTotalKnockOuts(int totalKnockOuts) {
        this.totalKnockOuts = totalKnockOuts;
    }

    public int getTotalKnockOuts() {
        return totalKnockOuts;
    }
}
