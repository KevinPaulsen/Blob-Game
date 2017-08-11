package jakemcdowell.blobsapplication;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import jakemcdowell.blobsapplication.bugs.Bug;
import static jakemcdowell.blobsapplication.Constants.radiusPricePerRadiusUpgrade;


/**
 * Created by kevin on 7/25/17.
 */
public class Game extends AppCompatActivity {
    private ProgressBar levelProgressBar;
    private List<Bug> bugList;
    public List<Bug> bugsInLevel = new ArrayList<>();
    private List<Bug> availableBugsInLevel = new ArrayList<>();
    private int bugsKilledInLevel = 0;
    private int totalKnockoutsRequiredInLevel;
    private GoldButton goldButton;

    public Game(ProgressBar progressBar, List<Bug> bugList, View view, GoldButton goldButton) {
        this.goldButton = goldButton;
        levelProgressBar = progressBar;
        this.bugList = bugList;
        ArrayList<Integer> damageIncreaseLevel = new ArrayList<>();
        ArrayList<Integer> damageIncrease = new ArrayList<>();
        ArrayList<Integer> radiusIncreaseLevel = new ArrayList<>();
        ArrayList<Integer> radiusIncrease = new ArrayList<>();
        Constants.goldIncreaseModifier = new ArrayList<>();
        for (int idx = 0; idx < 6; idx++) {
            damageIncreaseLevel.add(idx);
            radiusIncreaseLevel.add(idx);
        }
        for (int idx = 0; idx < 6; idx++) {
            damageIncrease.add(idx * 3);
        }
        for (int count = 50; count <= 475; count += 75) {
            radiusIncrease.add(count);
        }
        Constants.goldIncreaseModifier.add(1.0);
        Constants.goldIncreaseModifier.add(1.25);
        Constants.goldIncreaseModifier.add(1.5);
        Constants.goldIncreaseModifier.add(2.0);
        Constants.goldIncreaseModifier.add(2.5);
        Constants.goldIncreaseModifier.add(3.0);
        damageIncrease.set(0, 1);
        setupLevel(PlayerData.currentLevel);

        PlayerData.damageIncreasePerLevel.add(damageIncreaseLevel);
        PlayerData.damageIncreasePerLevel.add(damageIncrease);
        Constants.radiusPricePerRadiusUpgrade.add(radiusIncreaseLevel);
        Constants.radiusPricePerRadiusUpgrade.add(radiusIncrease);
    }

    public void removeAllAvailableBugs() {
        availableBugsInLevel.clear();
    }

    public void setupLevel(int level) {
        resetBugsToInitialState();
        removeAllAvailableBugs();
        PlayerData.currentLevel = level;
        this.levelProgressBar.setProgress(0);
        int levelBugCount = getTotalBugsInLevel();
        this.bugsInLevel.clear();
        this.bugsKilledInLevel = 0;
        for (int idx = 0; idx / 2 < (level / 5) + 1 && idx <= 8; idx += 2) {
            availableBugsInLevel.add(bugList.get(idx));
            availableBugsInLevel.add(bugList.get(idx + 1));
        }
        while (bugsInLevel.size() < getTotalBugsInLevel()) {
            bugsInLevel.add(getNewBug((int) (Math.random() * availableBugsInLevel.size())));
        }
        this.totalKnockoutsRequiredInLevel = levelBugCount * bugsInLevel.get(0).getTotalKnockOuts();
    }

    public void resetBugsToInitialState() {
        for (Bug bug : bugList) {
            bug.setBugToInitialState();
        }
    }

    private Bug getNewBug(int bugNum) {
        Bug bug = availableBugsInLevel.get(bugNum);
        availableBugsInLevel.remove(bugNum);
        bug.setBugToInitialState();
        bug.move();
        return bug;
    }

    public List<Bug> getBugs() {
        return bugList;
    }

    public boolean isAllDead() {
        return getBugsLeftInLevel() == 0;
    }

    public void nextLevel() {
        for (Bug bug : bugList) {
            bug.addHealth(Constants.HEALTH_ADDED);
        }
        setupLevel(PlayerData.currentLevel + 1);
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
        if (PlayerData.currentLevel / 3 + 1 <= 8) {
            return PlayerData.currentLevel / 3 + 1;
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

    public int getGoldEarnedInLevel() {
        return (int) ((PlayerData.currentLevel * 2) * Constants.goldIncreaseModifier.get(PlayerData.goldIncreaseLevel));
    }

    public void damageNear(int x, int y) {
        for (Bug bug : bugsInLevel) {
            int distance = getDistance(x, y, (int) bug.getX(), (int) bug.getY());
            if (distance <= radiusPricePerRadiusUpgrade.get(1).get(PlayerData.radiusIncreaseLevel)) {
                bug.damageBug(this);
            }
        }
    }

    public void moveAllBugsInLevel() {
        for (Bug bug : bugsInLevel) {
            bug.move();
        }
    }

    public int getDistance(int x1, int y1, int x2, int y2) {
        return (int) (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }
}
