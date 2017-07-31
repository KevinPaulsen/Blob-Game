package jakemcdowell.blobsapplication;

import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import android.os.CountDownTimer;
import android.content.Intent;
import android.app.Activity;

import jakemcdowell.blobsapplication.bugs.Bug;


/**
 * Created by kevin on 7/25/17.
 */
public class Game extends AppCompatActivity {
    private ProgressBar levelProgressBar;
    private List<Bug> bugList;
    private List<Bug> bugsInLevel;
    private int level;
    private int bugsKilledInLevel = 0;
    private int totalKnockoutsRequiredInLevel;

    public Game(ProgressBar progressBar, List<Bug> bugList) {
        levelProgressBar = progressBar;
        this.bugList = bugList;
        setupLevel(1);
    }

    public void setupLevel(int level) {
        this.level = level;
        levelProgressBar.setProgress(0);
        int levelBugCount = getTotalBugsInLevel();
        this.bugsInLevel = new ArrayList<>(levelBugCount);
        this.bugsKilledInLevel = 0;
        for (int idx = 0; idx < levelBugCount; idx++) {
            bugsInLevel.add(getNewBug(idx));
        }
        this.totalKnockoutsRequiredInLevel = levelBugCount * bugsInLevel.get(0).getTotalKnockOuts();
    }

    public void endLevel() {
        for (Bug bug : bugList) {
            bug.resetBugToInitialState();
        }
    }

    private Bug getNewBug(int bugNum) {
        Bug bug = bugList.get(bugNum);
        bug.resetBugToInitialState();
        bug.move();
        return bug;
    }

    public int getLevel() {
        return level;
    }

    public boolean isAllDead() {
        return getBugsLeftInLevel() == 0;
    }

    public void nextLevel() {
        for (Bug bug : bugList) {
            bug.addHealth(1);
        }
        setupLevel(getLevel() + 1);
    }

    public void updateLevelKnockOutProgressBar() {
        levelProgressBar.setProgress((int) ((((double) getBugsKnockedOut()) / totalKnockoutsRequiredInLevel) * 100));
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

    private int getTotalBugsInLevel() {
        return level / 3 + 1;
    }

    //returns how many bugs will die this level
    public int getBugsLeftInLevel() {
        return getTotalBugsInLevel() - bugsKilledInLevel;
    }
}
