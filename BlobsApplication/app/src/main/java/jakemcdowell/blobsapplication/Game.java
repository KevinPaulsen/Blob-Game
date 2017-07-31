package jakemcdowell.blobsapplication;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import java.util.ArrayList;


/**
 * Created by kevin on 7/25/17.
 */

public class Game extends AppCompatActivity {
    private int level;
    private int numBugs;
    private ProgressBar levelProgress;
    private int bugsKilled = 0;
    private ArrayList<Bug> bugList;

    public Game(ProgressBar progressBar, ArrayList<Bug> bugList) {
        level = 1;
        numBugs = 5;
        levelProgress = progressBar;
        levelProgress.setProgress(0);
        this.bugList = bugList;
    }

    public void pauseDeath(Bug bug) {

        final Bug B = bug;
        bug.moveOffscreen();
        new Thread(new Runnable() {
            public void run() {
                long startTime = System.currentTimeMillis();
                while(System.currentTimeMillis()-startTime < 500){
                        try {
                            B.setHp(0);
                            Thread.sleep(25);

                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }
                }
                B.setHp(100);
                }

            }).start();
        bug.moveOffscreen();
        bug.getButton().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bugsKilled != 5) {
                    B.move();
                }
            }
        }, 500);
    }

    public void addNextBug() {
        bugList.get((level / 3) - 1).move();
    }

    public void nextLevel() {
        level++;
        bugsKilled = 0;
    }

    public void setProgressBar(Bug bug) {
        bug.setHp((int)(((bug.getHealth() - bug.getTapsOnBug()) / bug.getHealth()) * 100));
    }

    public void progressBarAfterDeath(Bug bug) {
        bugsKilled++;
        levelProgress.setProgress((int)(((double)bugsKilled / ((double)(((level / 3) + 1) * 5))) * 100));
        bug.setHp(0);
    }

    public void fillHp(Bug bug) {
        bug.setHp(100);
    }

    public void resetLevelProgress() {
        levelProgress.setProgress(0);
    }

    public int getBugsKilled() {
        return bugsKilled;
    }
}
