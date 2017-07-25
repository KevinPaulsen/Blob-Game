package jakemcdowell.blobsapplication;

import android.view.View;
import android.widget.Button;

/**
 * Created by kevin on 7/25/17.
 */

public class Bug {
    private View bug;
    private int tapsOnBug;
    private double health;

    public Bug(View view, double health) {
        bug = (Button)view;
        this.health = health;
    }

    public void move() {
        bug.setX((float) ((Math.random() * 725) + 50));
        bug.setY((float) ((Math.random() * 1200) + 220));
    }

    public void newBug() {
        tapsOnBug = 0;
    }

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
