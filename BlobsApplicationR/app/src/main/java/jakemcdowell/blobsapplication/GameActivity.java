package jakemcdowell.blobsapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.ProgressBar;
import android.graphics.drawable.AnimationDrawable;

import java.util.ArrayList;

import jakemcdowell.blobsapplication.bugs.Bug;
import jakemcdowell.blobsapplication.bugs.FireBug;
import jakemcdowell.blobsapplication.bugs.MovingBug;
import jakemcdowell.blobsapplication.bugs.SmallBug;
import jakemcdowell.blobsapplication.bugs.TeleportingBug;


public class GameActivity extends AppCompatActivity {
    ArrayList<AnimationDrawable> normalbuganimations = new ArrayList<>();
    ArrayList<AnimationDrawable> smallbuganimations = new ArrayList<>();
    ArrayList<AnimationDrawable> movingbuganimations = new ArrayList<>();
    ArrayList<AnimationDrawable> teleportingbuganimations = new ArrayList<>();
    ArrayList<AnimationDrawable> armedfirebuganimations = new ArrayList<>();




    private Game game;
    private ArrayList<Bug> bugList = new ArrayList<>();
    private boolean isInitialized = false;
    private ProgressBar timeprogressbar;
    private int goldearnedbylevel = 0;
    private boolean gameactivityispaused = false;

    boolean sandyfirstrun = true;
    boolean gravelfirstrun = true;
    boolean leaffirstrun = true;
    boolean desertfirstrun = true;
    boolean snowfirstrun = true;

    //Sets up game screen (progress bar)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button buttons[] = new Button[10];
        ProgressBar progressBar[] = new ProgressBar[10];
        int buttonIds[] = new int[10];
        int progressBarIds[] = new int[10];
        int health = 1;
        int kOsPerDeath = Constants.KOSPERDEATH;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);

        buttonIds[0] = R.id.button;
        buttonIds[1] = R.id.button5;
        buttonIds[2] = R.id.button6;
        buttonIds[3] = R.id.button7;
        buttonIds[4] = R.id.button8;
        buttonIds[5] = R.id.button9;
        buttonIds[6] = R.id.button10;
        buttonIds[7] = R.id.button11;
        buttonIds[8] = R.id.button12;
        buttonIds[9] = R.id.button13;

        progressBarIds[0] = R.id.progressBar;
        progressBarIds[1] = R.id.progressBar3;
        progressBarIds[2] = R.id.progressBar4;
        progressBarIds[3] = R.id.progressBar5;
        progressBarIds[4] = R.id.progressBar6;
        progressBarIds[5] = R.id.progressBar7;
        progressBarIds[6] = R.id.progressBar8;
        progressBarIds[7] = R.id.progressBar9;
        progressBarIds[8] = R.id.progressBar10;
        progressBarIds[9] = R.id.progressBar11;
        timeprogressbar = (ProgressBar)this.findViewById(R.id.timeProgressBar);

        for (int count = 0; count < 10; count++) {
            //Instantiates all bug buttons, and  puts them offscreen
            buttons[count] = (Button) findViewById(buttonIds[count]);
            if (count < 2) {
                buttons[count].setBackgroundResource(R.drawable.normalbuganimation);
            }
            else if (count < 4) {
                buttons[count].setBackgroundResource(R.drawable.smallbuganimation);
            }
            else if (count < 6) {
                buttons[count].setBackgroundResource(R.drawable.movingbuganimation);
            }
            else if (count < 8) {
                buttons[count].setBackgroundResource(R.drawable.teleportingbuganimation);
            }
            else if (count < 10) {
                buttons[count].setBackgroundResource(R.drawable.armedfirebuganimation);
            }
            putOffScreen(buttons[count]);
            //Instantiates all progresssBars, and  puts them offscreen
            progressBar[count] = (ProgressBar) findViewById(progressBarIds[count]);
            putOffScreen(progressBar[count]);
            // Get all the bug animation objects
            if (count < 2) {
                normalbuganimations.add((AnimationDrawable) buttons[count].getBackground());
            }
            else if (count < 4) {
                smallbuganimations.add((AnimationDrawable) buttons[count].getBackground());
            }
            else if (count < 6) {
                movingbuganimations.add((AnimationDrawable) buttons[count].getBackground());
            }
            else if (count < 8) {
                teleportingbuganimations.add((AnimationDrawable) buttons[count].getBackground());
            }
            else if (count < 10) {
                armedfirebuganimations.add((AnimationDrawable) buttons[count].getBackground());
            }
        }

        //instantiantes every bug that will appear.
        bugList.add(new Bug(buttons[0], health, progressBar[0], kOsPerDeath));
        bugList.add(new Bug(buttons[1], health, progressBar[1], kOsPerDeath));
        bugList.add(new SmallBug(buttons[2], health, progressBar[2], kOsPerDeath));
        bugList.add(new SmallBug(buttons[3], health, progressBar[3], kOsPerDeath));
        bugList.add(new MovingBug(buttons[4], health, progressBar[4], kOsPerDeath));
        bugList.add(new MovingBug(buttons[5], health, progressBar[5], kOsPerDeath));
        bugList.add(new TeleportingBug(buttons[6], health, progressBar[6], kOsPerDeath));
        bugList.add(new TeleportingBug(buttons[7], health, progressBar[7], kOsPerDeath));
        bugList.add(new FireBug(buttons[8], health, progressBar[8], kOsPerDeath));
        bugList.add(new FireBug(buttons[9], health, progressBar[9], kOsPerDeath));

        Activity gameActivity = new GameActivity();

        //Creates new Game
        ProgressBar levelProgress = (ProgressBar)this.findViewById(R.id.progressBar2);
        game = new Game(levelProgress, bugList);

        /*
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    //
                }
            }
        }).start();
        */
        if (PlayerData.damageIncreaseLevel == 0) {
            findViewById(R.id.imageView29).setVisibility(View.GONE);
            findViewById(R.id.imageView30).setVisibility(View.GONE);
            findViewById(R.id.textView27).setVisibility(View.GONE);
        }
        if (PlayerData.radiusIncreaseLevel == 0) {
            findViewById(R.id.imageView31).setVisibility(View.GONE);
            findViewById(R.id.imageView32).setVisibility(View.GONE);
            findViewById(R.id.imageView33).setVisibility(View.GONE);
            findViewById(R.id.textView28).setVisibility(View.GONE);
        }
        if (PlayerData.goldIncreaseLevel == 0) {
            findViewById(R.id.imageView34).setVisibility(View.GONE);
            findViewById(R.id.imageView35).setVisibility(View.GONE);
            findViewById(R.id.textView29).setVisibility(View.GONE);
        }
        if (PlayerData.damageIncreaseLevel == 0 && PlayerData.radiusIncreaseLevel == 0 && PlayerData.goldIncreaseLevel == 0) {
            TextView upgradetitle = (TextView) findViewById(R.id.textView26);
            upgradetitle.setText("You don't have any Upgrades!");
        }
        if (PlayerData.damageIncreaseLevel == 1) {
            TextView damageleveldisplay = (TextView) findViewById(R.id.textView27);
            damageleveldisplay.setText("Level 1");
        }
        if (PlayerData.damageIncreaseLevel == 2) {
            TextView damageleveldisplay = (TextView) findViewById(R.id.textView27);
            damageleveldisplay.setText("Level 2");
        }
        if (PlayerData.damageIncreaseLevel == 3) {
            TextView damageleveldisplay = (TextView) findViewById(R.id.textView27);
            damageleveldisplay.setText("Level 3");
        }
        if (PlayerData.damageIncreaseLevel == 4) {
            TextView damageleveldisplay = (TextView) findViewById(R.id.textView27);
            damageleveldisplay.setText("Level 4");
        }
        if (PlayerData.damageIncreaseLevel == 5) {
            TextView damageleveldisplay = (TextView) findViewById(R.id.textView27);
            damageleveldisplay.setText("Level 5");
        }
        if (PlayerData.radiusIncreaseLevel == 1) {
            TextView radiusleveldisplay = (TextView) findViewById(R.id.textView28);
            radiusleveldisplay.setText("Level 1");
        }
        if (PlayerData.radiusIncreaseLevel == 2) {
            TextView radiusleveldisplay = (TextView) findViewById(R.id.textView28);
            radiusleveldisplay.setText("Level 2");
        }
        if (PlayerData.radiusIncreaseLevel == 3) {
            TextView radiusleveldisplay = (TextView) findViewById(R.id.textView28);
            radiusleveldisplay.setText("Level 3");
        }
        if (PlayerData.radiusIncreaseLevel == 4) {
            TextView radiusleveldisplay = (TextView) findViewById(R.id.textView28);
            radiusleveldisplay.setText("Level 4");
        }
        if (PlayerData.radiusIncreaseLevel == 5) {
            TextView radiusleveldisplay = (TextView) findViewById(R.id.textView28);
            radiusleveldisplay.setText("Level 5");
        }
        if (PlayerData.goldIncreaseLevel == 1) {
            TextView goldleveldisplay = (TextView) findViewById(R.id.textView29);
            goldleveldisplay.setText("Level 1");
        }
        if (PlayerData.goldIncreaseLevel == 2) {
            TextView goldleveldisplay = (TextView) findViewById(R.id.textView29);
            goldleveldisplay.setText("Level 2");
        }
        if (PlayerData.goldIncreaseLevel == 3) {
            TextView goldleveldisplay = (TextView) findViewById(R.id.textView29);
            goldleveldisplay.setText("Level 3");
        }
        if (PlayerData.goldIncreaseLevel == 4) {
            TextView goldleveldisplay = (TextView) findViewById(R.id.textView29);
            goldleveldisplay.setText("Level 4");
        }
        if (PlayerData.goldIncreaseLevel == 5) {
            TextView goldleveldisplay = (TextView) findViewById(R.id.textView29);
            goldleveldisplay.setText("Level 5");
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (!isInitialized) {
            game.getFirstBugInLevel().move();
            for (AnimationDrawable normalBugAnimation : normalbuganimations) {
                normalBugAnimation.start();
            }
            for (AnimationDrawable smallBugAnimation : smallbuganimations) {
                smallBugAnimation.start();
            }
            for (AnimationDrawable movingBugAnimation : movingbuganimations) {
                movingBugAnimation.start();
            }
            for (AnimationDrawable teleportingBugAnimation : teleportingbuganimations) {
                teleportingBugAnimation.start();
            }
            for (AnimationDrawable armedFireBugAnimation : armedfirebuganimations) {
                armedFireBugAnimation.start();
            }
            isInitialized = true;
            startCountDownTimer();
        }
    }
    public void onPause() {
        super.onPause();
        gameactivityispaused = true;
    }
    public void onResume() {
        super.onResume();
        gameactivityispaused = false;
    }
    public void startCountDownTimer() {
        new Thread(new Runnable() {
            public void run() {
                resetCountDownTimer();
                while(timeprogressbar.getProgress() != 0){
                    try {
                        Thread.sleep(Constants.timeInLevel / 100);
                        timeprogressbar.setProgress(timeprogressbar.getProgress() - 1);
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                    if (game.isAllDead()) {
                        break;
                    }
                    while (gameactivityispaused == true) {
                        try {
                            Thread.sleep(1);
                        } catch(Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                if (timeprogressbar.getProgress() == 0) {
                    gameOver();
                }
            }

        }).start();
    }

    public void resetCountDownTimer() {
        timeprogressbar.setProgress(100);
    }

    public void gameOver() {
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //Button game mechanics
    public void buttonTestClick(View v) {

        //sets +1 tap on the bug
        findBug(v).damageBug(game);

        //Activity Change (Backdrop Change,etc)
        if (game.isAllDead()) {
            //makes buttons and textViews appear, to make nextLevel screen.
            findViewById(R.id.button2).setVisibility(View.VISIBLE);
            findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            findViewById(R.id.textView22).setVisibility(View.VISIBLE);
            findViewById(R.id.textView23).setVisibility(View.VISIBLE);
            findViewById(R.id.textView24).setVisibility(View.VISIBLE);
            findViewById(R.id.textView25).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView25).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView26).setVisibility(View.VISIBLE);
            findViewById(R.id.button).setVisibility(View.GONE);
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            findViewById(R.id.progressBar2).setVisibility(View.GONE);
            findViewById(R.id.timeProgressBar).setVisibility(View.GONE);
            findViewById(R.id.textView21).setVisibility(View.GONE);
            findViewById(R.id.textView17).setVisibility(View.GONE);
            goldearnedbylevel = goldearnedbylevel + Constants.goldAddedPerLevel;
            PlayerData.currentGold = PlayerData.currentGold + goldearnedbylevel;
            TextView goldEarnedDisplay = (TextView) findViewById(R.id.textView23);
            TextView currentGoldDisplay = (TextView) findViewById(R.id.textView25);
            goldEarnedDisplay.setText("" + goldearnedbylevel);
            currentGoldDisplay.setText("" + PlayerData.currentGold);
        }
    }
    //
    public void returnToMenuClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    //code rand for nextLevelButtonClick
    public void nextLevelButtonClick(View v) {
        //checks to see if new bug on screen should be added
        game.nextLevel();

        startCountDownTimer();

        //Updates level marker
        TextView f = (TextView) findViewById(R.id.textView1);
        f.setText("Level: " + game.getLevel());
        ConstraintLayout j = (ConstraintLayout)findViewById(R.id.Constraint);
        Drawable k = getDrawable(R.drawable.sandy);
        Drawable l = getDrawable(R.drawable.gravel);
        Drawable m = getDrawable(R.drawable.leaf);
        Drawable n = getDrawable(R.drawable.desert);
        Drawable o = getDrawable(R.drawable.grassy);
        Drawable p = getDrawable(R.drawable.snow);

        //sets up next level and removes nextLevel page.
        findViewById(R.id.button2).setVisibility(View.GONE);
        findViewById(R.id.textView2).setVisibility(View.GONE);
        findViewById(R.id.textView22).setVisibility(View.GONE);
        findViewById(R.id.textView23).setVisibility(View.GONE);
        findViewById(R.id.textView24).setVisibility(View.GONE);
        findViewById(R.id.textView25).setVisibility(View.GONE);
        findViewById(R.id.imageView25).setVisibility(View.GONE);
        findViewById(R.id.imageView26).setVisibility(View.GONE);
        findViewById(R.id.button).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
        findViewById(R.id.textView21).setVisibility(View.VISIBLE);
        findViewById(R.id.textView17).setVisibility(View.VISIBLE);
        findViewById(R.id.timeProgressBar).setVisibility(View.VISIBLE);
        if(game.getLevel()%25 == 0 && game.getLevel()%25 < 5){
            j.setBackground(o);
        }
        if(game.getLevel()%25 > 4 && game.getLevel()%25 < 9){
            j.setBackground(k);
        }
        if(game.getLevel()%25 > 8 && game.getLevel()%25 < 13){
            j.setBackground(l);
            MediaPlayer gravelmain = MediaPlayer.create(this, R.raw.gravelmain);
            if (gravelfirstrun == true) {
                gravelmain.start();
                gravelfirstrun = false;
            }
        }
        if(game.getLevel()%25 > 12 && game.getLevel()%25 < 17){
            j.setBackground(m);
            MediaPlayer leafmain = MediaPlayer.create(this, R.raw.leafmain);
            if (leaffirstrun == true) {
                leafmain.start();
                leaffirstrun = false;
            }
        }
        if(game.getLevel()%25 > 16 && game.getLevel()%25 < 21){
            j.setBackground(n);
        }

        if(game.getLevel()%25 > 20 && game.getLevel()%25 < 24){
            j.setBackground(p);
            MediaPlayer snowmain = MediaPlayer.create(this, R.raw.snowmain);
            if (snowfirstrun == true) {
                snowmain.start();
                snowfirstrun = false;
            }
        }
    }

    private static void putOffScreen(View v) {
        v.setY(10000);
    }

    private static void putOffScreen(ProgressBar p) {
        p.setY(10000);
    }

    private Bug findBug(View view) {
        Bug result = null;
        for (Bug bug : bugList) {
            if (bug.getButton() == view) {
                result = bug;
            }
        }
        return result;
    }
}
