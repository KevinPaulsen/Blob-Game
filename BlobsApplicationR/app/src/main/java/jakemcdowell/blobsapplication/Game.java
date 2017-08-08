package jakemcdowell.blobsapplication;

import android.support.v7.app.AppCompatActivity;

import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;
//<<<<<<< Updated upstream

//=======
import java.lang.Math;
//>>>>>>> Stashed changes
import jakemcdowell.blobsapplication.bugs.Bug;

import static jakemcdowell.blobsapplication.Constants.radiusIncreaseLevel1;
import static jakemcdowell.blobsapplication.Constants.radiusIncreaseLevel2;
import static jakemcdowell.blobsapplication.Constants.radiusIncreaseLevel3;
import static jakemcdowell.blobsapplication.Constants.radiusIncreaseLevel4;
import static jakemcdowell.blobsapplication.Constants.radiusIncreaseLevel5;


/**
 * Created by kevin on 7/25/17.
 */
public class Game extends AppCompatActivity {
    private ProgressBar levelProgressBar;
    private List<Bug> bugList;
    public List<Bug> bugsInLevel;
    private List<Bug> availableBugsInLevel = new ArrayList<>();
    public int level;
    private int bugsKilledInLevel = 0;
    private int totalKnockoutsRequiredInLevel;

    public Game(ProgressBar progressBar, List<Bug> bugList) {
        levelProgressBar = progressBar;
        this.bugList = bugList;
        setupLevel(PlayerData.currentLevel);
    }

    public void removeAllAvailableBugs() {
        while (availableBugsInLevel.size() != 0) {
            availableBugsInLevel.remove(0);
        }
    }

    public void setupLevel(int level) {
        removeAllAvailableBugs();
        this.level = level;
        this.levelProgressBar.setProgress(0);
        int levelBugCount = getTotalBugsInLevel();
        this.bugsInLevel = new ArrayList<>(levelBugCount);
        this.bugsKilledInLevel = 0;
        for (int idx = 0; idx != 2 + ((level / 5) * 2) && idx <= 8; idx += 2) {
            availableBugsInLevel.add(bugList.get(idx));
            availableBugsInLevel.add(bugList.get(idx + 1));
        }
        while (bugsInLevel.size() < getTotalBugsInLevel()) {
            bugsInLevel.add(getNewBug((int)(Math.random() * availableBugsInLevel.size())));
        }
        this.totalKnockoutsRequiredInLevel = levelBugCount * bugsInLevel.get(0).getTotalKnockOuts();
    }


    public void endLevel() {
        for (Bug bug : bugList) {
            bug.resetBugToInitialState();
        }
    }

    public void radiusDamage(Bug bug1, Game game) {
        bug1.damageBug(game);
        for (Bug bug2 : bugsInLevel) {
            if (PlayerData.radiusIncreaseLevel == 1 && bug1 != bug2) {
                if (bug1.getDistance(bug2) <= radiusIncreaseLevel1) {
                    bug2.damageBug(game);
                }
            } else if (PlayerData.radiusIncreaseLevel == 2 && bug1 != bug2) {
                if (bug1.getDistance(bug2) <= radiusIncreaseLevel2) {
                    bug2.damageBug(game);
                }
            } else if (PlayerData.radiusIncreaseLevel == 3 && bug1 != bug2) {
                if (bug1.getDistance(bug2) <= radiusIncreaseLevel3) {
                    bug2.damageBug(game);
                }
            } else if (PlayerData.radiusIncreaseLevel == 4 && bug1 != bug2) {
                if (bug1.getDistance(bug2) <= radiusIncreaseLevel4) {
                    bug2.damageBug(game);
                }
            } else if (PlayerData.radiusIncreaseLevel == 5 && bug1 != bug2) {
                if (bug1.getDistance(bug2) <= radiusIncreaseLevel5) {
                    bug2.damageBug(game);
                }
            }
        }
    }

    private Bug getNewBug(int bugNum) {
        Bug bug = availableBugsInLevel.get(bugNum);
        availableBugsInLevel.remove(bugNum);
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
            bug.addHealth(Constants.HEALTH_ADDED);
        }
        setupLevel(getLevel() + 1);
    }

    public void updateLevelKnockOutProgressBar() {
        levelProgressBar.setProgress((int) ((((double) getBugsKnockedOut()) / totalKnockoutsRequiredInLevel) * 100));
    }
    public void resetKOBar(){
        levelProgressBar.setProgress(0);
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
        if (level / 3 + 1 <= 8) {
            return level / 3 + 1;
        } else {
            return 8;
        }
    }

    public Bug getFirstBugInLevel() {
        return bugsInLevel.get(0);
    }

    //returns how many bugs will die this level
    public int getBugsLeftInLevel() {
        return getTotalBugsInLevel() - bugsKilledInLevel;
    }

    public void pesticide(){
        for(Bug bug : bugsInLevel){
           bug.addDamage(bug.getHealth());
        }
    }
}
