package jakemcdowell.blobsapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakemcdowell on 7/28/17.
 */

public class PlayerData {

    public static int currentGold = 99999;
    public static int totalEarnedGoldInLevel = 0;
    public static List<List<Integer>> damageIncreasePerLevel = new ArrayList<>();

    public static int currentLevel = 1;
    public static int highestLevel = 0;

    public static int damageIncreaseLevel = 5;
    public static int radiusIncreaseLevel = 5;
    public static int goldIncreaseLevel = 0;
    public static int numberOfPesticide = 5;
    public static boolean continueLevel = false;
    public static boolean continueLevel1 = false;
}
