package jakemcdowell.blobsapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakemcdowell on 7/28/17.
 */

public class Constants {

    public static String version = "1.0.0 Beta";

//BASIC CONSTANTS

//This is the amount of time in the level measured in seconds times 1000(milliseconds)
    public static long timeInLevel = 10  * 1000;

//UPGRADES
    //This is the price of the damage increase upgrade by level
    public static int damageIncreasePriceLevel1 = 30;
    public static int damageIncreasePriceLevel2 = 60;
    public static int damageIncreasePriceLevel3 = 136;
    public static int damageIncreasePriceLevel4 = 364;
    public static int damageIncreasePriceLevel5 = 1184;
    //This is how much damage is increased by per tap
    public static int damageIncreaseLevel1 = 3;
    public static int damageIncreaseLevel2 = 6;
    public static int damageIncreaseLevel3 = 9;
    public static int damageIncreaseLevel4 = 12;
    public static int damageIncreaseLevel5 = 15;

    // This is the price of radius per upgrade
    public static List<List<Integer>> radiusPricePerRadiusUpgrade = new ArrayList<>();

    //This is the price of the radius increase upgrade by level
    public static int radiusIncreasePriceLevel1 = 50;
    public static int radiusIncreasePriceLevel2 = 110;
    public static int radiusIncreasePriceLevel3 = 282;
    public static int radiusIncreasePriceLevel4 = 872;
    public static int radiusIncreasePriceLevel5 = 3378;
    //This is the radius in pixels per level of this upgrade
    public static int radiusIncreaseLevel0 = 250;
    public static int radiusIncreaseLevel1 = 350;
    public static int radiusIncreaseLevel2 = 450;
    public static int radiusIncreaseLevel3 = 550;
    public static int radiusIncreaseLevel4 = 650;
    public static int radiusIncreaseLevel5 = 750;

    //This is the price of pesticide powerUp based on level
    public static int pesticidePriceLevel1 = 100;
    public static int pesticidePriceLevel2 = 252;
    public static int pesticidePriceLevel3 = 762;
    public static int pesticidePriceLevel4 = 2874;
    public static int pesticidePriceLevel5 = 14132;

    //This is the price of gold increase upgrade based on level
    public static int goldIncreasePriceLevel1 = 500;
    public static int goldIncreasePriceLevel2 = 1732;
    public static int goldIncreasePriceLevel3 = 7696;
    public static int goldIncreasePriceLevel4 = 46080;
    public static int goldIncreasePriceLevel5 = 394652;

    public static List<Double> goldIncreaseModifier;

    public static int goldAddedPerLevel = 2;

//RANDOM GOLD
    //This is the chance of seeing random gold (out of 100) everytime it moves
    public static int chanceOfSeeingGold = 15;
    //This is how often random gold moves around in seconds x times 1000(milliseconds)
    public static int goldMovementTiming = (int) (1.5 * 1000);

//BUG MECHANICS
    //This is the movingSpeed (milliseconds) per 20 pixels a bug moves
    public static final int MOVING_BUG_SPEED = 100;
// Initial Health
    public static final int INITIAL_HEALTH = 1;
//Times knocked out per death
    public static final int KOS_PER_DEATH = 2;
//Health added per next level
    public static final int HEALTH_ADDED = 0;
}
