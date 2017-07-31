package jakemcdowell.blobsapplication;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by kevin on 7/25/17.
 */

public class Bug {

    private View button;
    private int tapsOnBug;
    private static double health;
    public ProgressBar hp;

    public Bug() {

    }

    public Bug(View view, double health, ProgressBar hp) {
        button = view;
        this.health = health;
        this.hp = hp;
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

    public void resetTaps() { tapsOnBug = 0; }

    public void tapBug() {
        tapsOnBug++;
    }

    public double getHealth() {
        return health;
    }

    public int getTapsOnBug() {
        return tapsOnBug;
    }

    public void addHealth(int moreHealth) {
        health += moreHealth;
    }
}
