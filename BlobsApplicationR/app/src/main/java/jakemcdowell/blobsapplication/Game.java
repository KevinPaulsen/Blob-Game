package jakemcdowell.blobsapplication;

import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by kevin on 7/25/17.
 */
public class Game extends AppCompatActivity {
    private int level;
    private int numBugs;
    private ProgressBar levelProgress;
    final private ArrayList<Bug> bugList;
    private int bugsKilledInLevel = 0;
    private List<Bug> bugsInLevel;

    public Game(ProgressBar progressBar, ArrayList<Bug> bugList) {
        levelProgress = progressBar;
        this.bugList = bugList;
        setupLevel(1);
    }

    public void setupLevel(int level) {
        this.level = level;
        this.numBugs = 5;
        levelProgress.setProgress(0);
        int levelBugCount = level / 3 + 1;
        this.bugsInLevel = new ArrayList<>(levelBugCount);
        this.bugsKilledInLevel = 0;
        for (int idx = 0; idx < levelBugCount; idx++) {
            bugsInLevel.add(getNewBug(idx));
        }
    }

    public void endLevel() {
        for (Bug bug : bugList) {
            bug.reset();
        }
    }

    private Bug getNewBug(int bugNum) {
        Bug bug = bugList.get(bugNum);
        bug.reset();
        bug.move();
        return bug;
    }

    public void pauseDeath(final Bug bug) {
        bug.moveOffscreen();

        if (bug.getKnockOuts() != 5) {
            bug.getButton().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bug.move();
                }
            }, 700);
        }
    }

    public int getLevel() {
        return level;
    }

    public void nextLevel() {
        Bug.addHealth(0);
        setupLevel(getLevel() + 1);
    }

    public void setProgressBar(Bug bug) {
        bug.setHp((int)(((bug.getHealth() - bug.getDamageOnBug()) / bug.getHealth()) * 100));
    }

    public void progressBarAfterDeath(Bug bug) {
        levelProgress.setProgress((int)(((double) getBugsKnockedOut() / ((double)(((level / 3) + 1) * 5))) * 100));
    }

    public int getBugsKnockedOut() {
        int totalDeadBugs = 0;
        for (Bug bug : bugsInLevel) {
            totalDeadBugs += bug.getKnockOuts();
        }
        return totalDeadBugs;
    }


    public void addBugKilledInLevel() {
        bugsKilledInLevel++;
    }


    public void resetBugsKilledInLevel() {
        bugsKilledInLevel = 0;
    }

    //returns how many bugs will die this level
    public int getBugDeathPerLevel() {
        return (((level / 3) + 1) - bugsKilledInLevel);
    }
}
