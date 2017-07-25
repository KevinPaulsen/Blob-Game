package jakemcdowell.blobsapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by kevin on 7/25/17.
 */

public class Game extends AppCompatActivity {
    private int level;
    private int numBugs;
    private ProgressBar levelProgress;
    private ProgressBar hp;
    private int bugsKilled = 0;

    public Game(ProgressBar progressBar, ProgressBar hp) {
        level = 1;
        numBugs = 5;
        levelProgress = progressBar;
        this.hp = hp;
    }

    public void nextLevel() {
        level++;
        bugsKilled = 0;
    }

    public void setProgressBar(Bug bug) {
        hp.setProgress((int)(((bug.getHealth() - bug.getTapsOnBug()) / bug.getHealth()) * 100));
    }

    public void deadBug() {
        bugsKilled++;
        levelProgress.setProgress((int)(20 * bugsKilled));
        hp.setProgress(0);
    }

    public void fillHp() {
        hp.setProgress(100);
    }

    public void resetLevelProgress() {
        levelProgress.setProgress(0);
    }

    public int getBugsKilled() {
        return bugsKilled;
    }
}
