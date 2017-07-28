package jakemcdowell.blobsapplication;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by kevin on 7/25/17.
 */

public class Bug {

    private View button;
    private int tapsOnBug = 0;
    private static double health;
    private ProgressBar hp;
    private int totalDeaths = 0;

    public Bug() {}

    public Bug(View view, double health, ProgressBar hp) {
        button = view;
        this.health = health;
        this.hp = hp;
    }

    public void reset() {
        moveOffscreen();
        tapsOnBug = 0;
        hp.setProgress(100);
        totalDeaths = 0;
    }

    public void move() {
        button.setX((float) ((Math.random() * 725) + 50));
        button.setY((float) ((Math.random() * 1200) + 220));
        hp.setX(button.getX() + 40);
        hp.setY(button.getY() - 20);
    }

    public void moveOffscreen() {
        button.setY(10000);
        hp.setY(10000);
    }

    public void setHp(int x) { hp.setProgress(x); }

    public View getButton() {
        return button;
    }

    public void resetDamage() { tapsOnBug = 0; }

    public void resetTotalDeaths() {
        totalDeaths = 0;
    }

    public void damageBug() {
        tapsOnBug++;
    }

    public void died() {
        totalDeaths++;
    }

    public int getKnockOuts() {
        return totalDeaths;
    }

    public double getHealth() {
        return health;
    }

    public int getDamageOnBug() {
        return tapsOnBug;
    }

    public static void addHealth(int moreHealth) {
        health += moreHealth;
    }
}
