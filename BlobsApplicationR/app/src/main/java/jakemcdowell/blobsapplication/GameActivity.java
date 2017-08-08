package jakemcdowell.blobsapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import jakemcdowell.blobsapplication.bugs.Bug;
import jakemcdowell.blobsapplication.bugs.FireBug;
import jakemcdowell.blobsapplication.bugs.MovingBug;
import jakemcdowell.blobsapplication.bugs.SmallBug;
import jakemcdowell.blobsapplication.bugs.TeleportingBug;
import static jakemcdowell.blobsapplication.Constants.goldAddedPerLevel;
import static jakemcdowell.blobsapplication.bugs.Bug.scheduler;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;


public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private int buttonIds[] = new int[10];
    private Game game;
    private ArrayList<Bug> bugList = new ArrayList<>();
    private boolean isInitialized = false;
    private ProgressBar timeProgressbar;
    private int goldEarnedByRandom = 0;
    private int randomGoldNumber = 0;
    private boolean gameActivityIsPaused = false;
    Button goldButton;
    boolean nextLevelScreen = false;

    boolean sandyFirstRun = true;
    boolean gravelFirstRun = true;
    boolean leafFirstRun = true;
    boolean desertFirstRun = true;
    boolean snowFirstRun = true;
    boolean grassyFirstRun = true;

    //Sets up game screen (progress bar)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sandyFirstRun = true;
        gravelFirstRun = true;
        leafFirstRun = true;
        desertFirstRun = true;
        snowFirstRun = true;
        grassyFirstRun = true;

        Button buttons[] = new Button[10];
        ProgressBar progressBar[] = new ProgressBar[10];
        int progressBarIds[] = new int[10];
        int health = Constants.INITIAL_HEALTH;
        int kOsPerDeath = Constants.KOSPERDEATH;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);

        goldButton = (Button)this.findViewById(R.id.goldRandomButton);
        goldButton.setOnClickListener(this);

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

        for (int idx = 0; idx < progressBar.length; idx++) {
            progressBar[idx] = (ProgressBar) findViewById(progressBarIds[idx]);
            buttons[idx] = (Button) findViewById(buttonIds[idx]);
        }

        timeProgressbar = (ProgressBar) this.findViewById(R.id.timeProgressBar);

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

        //Creates new Game
        ProgressBar levelProgress = (ProgressBar) this.findViewById(R.id.progressBar2);
        game = new Game(levelProgress, bugList);

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
            upgradetitle.setText("You don't have any Upgrades! Go buy some in the Shop!");
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
        if (PlayerData.numberOfPesticide == 0) {
            findViewById(R.id.pesticide).setVisibility(View.GONE);
        }
        if (PlayerData.continueLevel == true || PlayerData.beginLevel == true) {
            PlayerData.continueLevel = false;
            PlayerData.continueLevel1 = false;
            //NEXTLEVELBUTTONCLICKCALLED
            //checks to see if new bug on screen should be added
            if(PlayerData.beginLevel == true) {
                PlayerData.currentLevel = PlayerData.currentLevel - 1;
                PlayerData.beginLevel = false;
            }
            //Updates level marker
            TextView f = (TextView) findViewById(R.id.textView1);
            Game.nextLevel(game);
            startCountDownTimer();
            f.setText("Level: " + game.getLevel());
            goldButton.setText("" + randomGoldNumber);
            ConstraintLayout j = (ConstraintLayout) findViewById(R.id.Constraint);
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
            if (PlayerData.numberOfPesticide != 0) {
                findViewById(R.id.pesticide).setVisibility(View.VISIBLE);
            }
            if (game.getLevel() % 25 == 0 && game.getLevel() % 25 < 5) {
                snowFirstRun = true;
                j.setBackground(o);
                MediaPlayer grassymain = MediaPlayer.create(this, R.raw.grassymain);
                if (grassyFirstRun) {
                    MusicPlayer.startSong(grassymain);
                    grassyFirstRun = false;
                }
            }
            if (game.getLevel() % 25 > 4 && game.getLevel() % 25 < 9) {
                grassyFirstRun = true;
                j.setBackground(k);
                MediaPlayer sandymain = MediaPlayer.create(this, R.raw.sandymain);
                if (sandyFirstRun) {
                    MusicPlayer.startSong(sandymain);
                    sandyFirstRun = false;
                }
            }
            if (game.getLevel() % 25 > 8 && game.getLevel() % 25 < 13) {
                sandyFirstRun = true;
                j.setBackground(l);
                MediaPlayer gravelmain = MediaPlayer.create(this, R.raw.gravelmain);
                if (gravelFirstRun) {
                    MusicPlayer.startSong(gravelmain);
                    gravelFirstRun = false;
                }
            }
            if (game.getLevel() % 25 > 12 && game.getLevel() % 25 < 17) {
                gravelFirstRun = true;
                j.setBackground(m);
                MediaPlayer leafmain = MediaPlayer.create(this, R.raw.leafmain);
                if (leafFirstRun) {
                    MusicPlayer.startSong(leafmain);
                    leafFirstRun = false;
                }
            }
            if (game.getLevel() % 25 > 16 && game.getLevel() % 25 < 21) {
                leafFirstRun = true;
                j.setBackground(n);
                MediaPlayer desertmain = MediaPlayer.create(this, R.raw.desertmain);
                if (desertFirstRun) {
                    MusicPlayer.startSong(desertmain);
                    desertFirstRun = false;
                }
            }
            if (game.getLevel() % 25 > 20 && game.getLevel() % 25 < 24) {
                desertFirstRun = true;
                j.setBackground(p);
                MediaPlayer snowmain = MediaPlayer.create(this, R.raw.snowmain);
                if (snowFirstRun) {
                    MusicPlayer.startSong(snowmain);
                    snowFirstRun = false;
                }
            }
        }
        TextView f = (TextView) findViewById(R.id.textView1);
        f.setText("Level: " + game.getLevel());
        TextView g = (TextView) findViewById(R.id.textView31);
        g.setText("Remaining: " + PlayerData.numberOfPesticide);
        startCountDownTimer();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        sandyFirstRun = true;
        gravelFirstRun = true;
        leafFirstRun = true;
        desertFirstRun = true;
        snowFirstRun = true;
        grassyFirstRun = true;

        //makes every button unclickable
        for (int idx : buttonIds) {
            findViewById(idx).setClickable(false);
        }

        // Starts moving gold
        randomGoldMove();

        //starts every animation, countdown timer,
        if (!isInitialized) {
            game.getFirstBugInLevel().move();
            for (Bug bug : bugList) {
                bug.startAnimation();
            }
            isInitialized = true;
            startCountDownTimer();
            ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.Constraint);
            Drawable sandy = getDrawable(R.drawable.sandy);
            Drawable gravel = getDrawable(R.drawable.gravel);
            Drawable leaf = getDrawable(R.drawable.leaf);
            Drawable desert = getDrawable(R.drawable.desert);
            Drawable grassy = getDrawable(R.drawable.grassy);
            Drawable snow = getDrawable(R.drawable.snow);
            if(game.getLevel() % 25 == 1 || game.getLevel()%25 < 5) {
                snowFirstRun = true;
                constraintLayout.setBackground(grassy);
                MediaPlayer grassymain = MediaPlayer.create(this, R.raw.grassymain);
                if (grassyFirstRun == true) {
                    MusicPlayer.startSong(grassymain);
                    grassyFirstRun = false;
                }
            }
            if(game.getLevel() % 25 > 4 && game.getLevel()%25 < 9) {
                grassyFirstRun = true;
                constraintLayout.setBackground(sandy);
                MediaPlayer sandymain = MediaPlayer.create(this, R.raw.sandymain);
                if (sandyFirstRun == true) {
                    MusicPlayer.startSong(sandymain);
                    sandyFirstRun = false;
                }
            }
            if(game.getLevel() % 25 > 8 && game.getLevel()%25 < 13){
                sandyFirstRun = true;
                constraintLayout.setBackground(gravel);
                MediaPlayer gravelmain = MediaPlayer.create(this, R.raw.gravelmain);
                if (gravelFirstRun == true) {
                    MusicPlayer.startSong(gravelmain);
                    gravelFirstRun = false;
                }
            }
            if(game.getLevel() % 25 > 12 && game.getLevel()%25 < 17){
                gravelFirstRun = true;
                constraintLayout.setBackground(leaf);
                MediaPlayer leafmain = MediaPlayer.create(this, R.raw.leafmain);
                if (leafFirstRun == true) {
                    MusicPlayer.startSong(leafmain);
                    leafFirstRun = false;
                }
            }
            if(game.getLevel() % 25 > 16 && game.getLevel()%25 < 21){
                leafFirstRun = true;
                constraintLayout.setBackground(desert);
                MediaPlayer desertmain = MediaPlayer.create(this, R.raw.desertmain);
                if (desertFirstRun == true) {
                    MusicPlayer.startSong(desertmain);
                    desertFirstRun = false;
                }
            }
            if(game.getLevel() % 25 > 20 && game.getLevel()%25 < 24){
                desertFirstRun = true;
                constraintLayout.setBackground(snow);
                MediaPlayer snowmain = MediaPlayer.create(this, R.raw.snowmain);
                if (snowFirstRun == true) {
                    MusicPlayer.startSong(snowmain);
                    snowFirstRun = false;
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!nextLevelScreen) {

            game.damageNear((int) event.getX(), (int) event.getY());

            if (game.isAllDead()) {
                allBugsDead();
                nextLevelScreen = true;
            }
        }
        return super.onTouchEvent(event);
    }

    public int getDistance(float centerPointX, float centerPointY, float buttonPointX, float buttonPointY) {
        float number1 = buttonPointX - centerPointX;
        float number2 = buttonPointY - centerPointY;
        number1 = number1*number1;
        number2 = number2*number2;
        return (int) Math.sqrt(number1+number2);
    }

    public void onPause() {
        super.onPause();
        gameActivityIsPaused = true;
    }
    public void onResume() {
        super.onResume();
        gameActivityIsPaused = false;
    }
    public void startCountDownTimer() {
        new Thread(new Runnable() {
            public void run() {
                resetCountDownTimer();
                while(timeProgressbar.getProgress() != 0){
                    try {
                        Thread.sleep(Constants.timeInLevel / 100);
                        timeProgressbar.setProgress(timeProgressbar.getProgress() - 1);
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                    if (game.isAllDead()) {
                        break;
                    }
                    while (gameActivityIsPaused) {
                        try {
                            Thread.sleep(1);
                        } catch(Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                if (timeProgressbar.getProgress() == 0) {
                    if (game.getLevel() > PlayerData.highestLevel) {
                        PlayerData.highestLevel = game.getLevel();
                    }
                    PlayerData.currentLevel = 1;
                    gameOver();
                }
            }

        }).start();
    }

    public void gameOver() {
        PlayerData.currentLevel = 1;
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void moveOnScreen(View view) {
        view.setX((int)(Math.random() * 575) + 50);
        view.setY((int)(Math.random() * 1200) + 220);
    }

    private void moveOffScreen(View view) {
        view.setY(10000);
    }

    public void randomGoldMove() {
        final Runnable beeper = new Runnable() {
            public void run() {
                if (Math.random() * 100 + 1 <= Constants.chanceOfSeeingGold) {
                    moveOnScreen(goldButton);
                } else {
                    moveOffScreen(goldButton);
                }
            }
        };
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, Constants.goldMovementTiming, MILLISECONDS);
        scheduler.schedule(new Runnable() {
            public void run() {
                beeperHandle.cancel(true);
            }
        }, 3000, MINUTES);
    }

    public void onClick(View v) {
        goldButtonClick();
    }

    public void goldButtonClick() {
        moveOffScreen(goldButton);
        goldEarnedByRandom += PlayerData.totalEarnedGoldInLevel / game.getLevel();
    }

    public void resetCountDownTimer() {
        timeProgressbar.setProgress(100);
    }

    //Button game mechanics
    public void buttonTestClick(View v) {
        //sets +1 tap on the bug
        findBug(v).damageBug(game);

        if (game.isAllDead()) {
            allBugsDead();
        }
    }

    public void allBugsDead() {
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
        findViewById(R.id.goldRandomButton).setVisibility(View.GONE);
        PlayerData.currentGold = PlayerData.currentGold + goldEarnedByRandom;
        if (PlayerData.goldIncreaseLevel == 0) {

        }
        if (PlayerData.goldIncreaseLevel == 1) {
            goldAddedPerLevel = (int) (goldAddedPerLevel * 1.25);
        }
        if (PlayerData.goldIncreaseLevel == 2) {
            goldAddedPerLevel = (int) (goldAddedPerLevel * 1.5);
        }
        if (PlayerData.goldIncreaseLevel == 3) {
            goldAddedPerLevel =  goldAddedPerLevel * 2;
        }
        if (PlayerData.goldIncreaseLevel == 4) {
            goldAddedPerLevel = (int) (goldAddedPerLevel * 2.5);
        }
        if (PlayerData.goldIncreaseLevel == 5) {
            goldAddedPerLevel = goldAddedPerLevel * 3;
        }
        PlayerData.totalEarnedGoldInLevel += Constants.goldAddedPerLevel;
        PlayerData.currentGold = PlayerData.currentGold + PlayerData.totalEarnedGoldInLevel;
        TextView goldEarnedDisplay = (TextView) findViewById(R.id.textView23);
        TextView currentGoldDisplay = (TextView) findViewById(R.id.textView25);
        goldEarnedDisplay.setText("" + (PlayerData.totalEarnedGoldInLevel + goldEarnedByRandom));
        currentGoldDisplay.setText("" + PlayerData.currentGold);

        if(game.getLevel()%25 == 4) {
            MediaPlayer grassyend = MediaPlayer.create(this, R.raw.grassyend);
            MusicPlayer.playEndingMusic(grassyend);
        }
        if(game.getLevel()%25 == 8) {
            MediaPlayer sandyend = MediaPlayer.create(this, R.raw.sandyend);
            MusicPlayer.playEndingMusic(sandyend);
        }
        if(game.getLevel()%25 == 12) {
            MediaPlayer gravelend = MediaPlayer.create(this, R.raw.gravelend);
            MusicPlayer.playEndingMusic(gravelend);
        }
        if(game.getLevel()%25 == 16) {
            MediaPlayer leafend = MediaPlayer.create(this, R.raw.leafend);
            MusicPlayer.playEndingMusic(leafend);
        }
        if(game.getLevel()%25 == 20) {
            MediaPlayer desertend = MediaPlayer.create(this, R.raw.desertend);
            MusicPlayer.playEndingMusic(desertend);
        }
        if(game.getLevel()%25 == 24) {
            MediaPlayer snowend = MediaPlayer.create(this, R.raw.snowend);
            MusicPlayer.playEndingMusic(snowend);
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
            if(PlayerData.numberOfPesticide != 0){
                findViewById(R.id.pesticide).setVisibility(View.VISIBLE);
            }
            if (PlayerData.goldIncreaseLevel == 0) {

            }
            if (PlayerData.goldIncreaseLevel == 1) {
                goldAddedPerLevel = (int) (goldAddedPerLevel * 1.25);
            }
            if (PlayerData.goldIncreaseLevel == 2) {
                goldAddedPerLevel = (int) (goldAddedPerLevel * 1.5);
            }
            if (PlayerData.goldIncreaseLevel == 3) {
                goldAddedPerLevel =  goldAddedPerLevel * 2;
            }
            if (PlayerData.goldIncreaseLevel == 4) {
                goldAddedPerLevel = (int) (goldAddedPerLevel * 2.5);
            }
            if (PlayerData.goldIncreaseLevel == 5) {
                goldAddedPerLevel = goldAddedPerLevel * 3;
            }
        }
    }
    public void returnToMenuClick(View v) {
            if (game.getLevel() > PlayerData.highestLevel) {
                PlayerData.highestLevel = game.getLevel();
            }
        if(game.isAllDead()){
            PlayerData.currentLevel = game.getLevel();
            PlayerData.beginLevel = true;
        }
        else if(game.getLevel() != 1) {
            PlayerData.currentLevel = game.getLevel() - 1;
            PlayerData.continueLevel = true;
            for (Bug bug : game.bugsInLevel) {
                bug.setBugToInitialState();
            }
            game.resetKOBar();
            goldEarnedByRandom = 0;
        }
        else{
            PlayerData.continueLevel1 = true;
            game.resetKOBar();
            goldEarnedByRandom = 0;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    @Override
    public void onBackPressed() {
        if (game.getLevel() > PlayerData.highestLevel) {
            PlayerData.highestLevel = game.getLevel();
        }
        if(game.isAllDead()){
            PlayerData.currentLevel = game.getLevel();
            PlayerData.beginLevel = true;
        }
        else if(game.getLevel() != 1) {
            PlayerData.currentLevel = game.getLevel() - 1;
            PlayerData.continueLevel = true;
            for (Bug bug : game.bugsInLevel) {
                bug.setBugToInitialState();
            }
            game.resetKOBar();
            goldEarnedByRandom = 0;
        }
        else{
            PlayerData.continueLevel1 = true;
            game.resetKOBar();
            goldEarnedByRandom = 0;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
}
    //code rand for nextLevelButtonClick
    public void nextLevelButtonClick(View v) {
        goldButton.setText("" + randomGoldNumber);
        goldEarnedByRandom = 0;
        nextLevelScreen = false;



        //checks to see if new bug on screen should be added

        Game.nextLevel(game);

        startCountDownTimer();

        //Updates level marker
        TextView f = (TextView) findViewById(R.id.textView1);
        f.setText("Level: " + game.getLevel());
        goldButton.setText("" + goldEarnedByRandom);
        ConstraintLayout j = (ConstraintLayout) findViewById(R.id.Constraint);
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
        findViewById(R.id.goldRandomButton).setVisibility(View.VISIBLE);
            if (PlayerData.numberOfPesticide != 0) {
                findViewById(R.id.pesticide).setVisibility(View.VISIBLE);
            }
            if (game.getLevel() % 25 == 0 && game.getLevel() % 25 < 5) {
                snowFirstRun = true;
                j.setBackground(o);
                MediaPlayer grassymain = MediaPlayer.create(this, R.raw.grassymain);
                if (grassyFirstRun == true) {
                    MusicPlayer.startSong(grassymain);
                    grassyFirstRun = false;
                }
            }
            if (game.getLevel() % 25 > 4 && game.getLevel() % 25 < 9) {
                grassyFirstRun = true;
                j.setBackground(k);
                MediaPlayer sandymain = MediaPlayer.create(this, R.raw.sandymain);
                if (sandyFirstRun == true) {
                    MusicPlayer.startSong(sandymain);
                    sandyFirstRun = false;
                }
            }
            if (game.getLevel() % 25 > 8 && game.getLevel() % 25 < 13) {
                sandyFirstRun = true;
                j.setBackground(l);
                MediaPlayer gravelmain = MediaPlayer.create(this, R.raw.gravelmain);
                if (gravelFirstRun == true) {
                    MusicPlayer.startSong(gravelmain);
                    gravelFirstRun = false;
                }
            }
            if (game.getLevel() % 25 > 12 && game.getLevel() % 25 < 17) {
                gravelFirstRun = true;
                j.setBackground(m);
                MediaPlayer leafmain = MediaPlayer.create(this, R.raw.leafmain);
                if (leafFirstRun == true) {
                    MusicPlayer.startSong(leafmain);
                    leafFirstRun = false;
                }
            }
            if (game.getLevel() % 25 > 16 && game.getLevel() % 25 < 21) {
                leafFirstRun = true;
                j.setBackground(n);
                MediaPlayer desertmain = MediaPlayer.create(this, R.raw.desertmain);
                if (desertFirstRun == true) {
                    MusicPlayer.startSong(desertmain);
                    desertFirstRun = false;
                }
            }
            if (game.getLevel() % 25 > 20 && game.getLevel() % 25 < 24) {
                desertFirstRun = true;
                j.setBackground(p);
                MediaPlayer snowmain = MediaPlayer.create(this, R.raw.snowmain);
                if (snowFirstRun == true) {
                    MusicPlayer.startSong(snowmain);
                    snowFirstRun = false;
                }
            }
        }
    public void pesticideUse(View v) {
        game.pesticide();
        for (Bug bug : game.bugsInLevel) {
            bug.damageBug(game);
        }
        PlayerData.numberOfPesticide = PlayerData.numberOfPesticide - 1;
        TextView g = (TextView) findViewById(R.id.textView31);
        g.setText("Remaining: " + PlayerData.numberOfPesticide);
        if (PlayerData.numberOfPesticide == 0) {
            findViewById(R.id.pesticide).setVisibility(View.GONE);
        }
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
            findViewById(R.id.pesticide).setVisibility(View.GONE);
            if (PlayerData.goldIncreaseLevel == 0) {

            }
            if (PlayerData.goldIncreaseLevel == 1) {
                goldAddedPerLevel = (int) (goldAddedPerLevel * 1.25);
            }
            if (PlayerData.goldIncreaseLevel == 2) {
                goldAddedPerLevel = (int) (goldAddedPerLevel * 1.5);
            }
            if (PlayerData.goldIncreaseLevel == 3) {
                goldAddedPerLevel = goldAddedPerLevel * 2;
            }
            if (PlayerData.goldIncreaseLevel == 4) {
                goldAddedPerLevel = (int) (goldAddedPerLevel * 2.5);
            }
            if (PlayerData.goldIncreaseLevel == 5) {
                goldAddedPerLevel = goldAddedPerLevel * 3;
            }
            PlayerData.totalEarnedGoldInLevel += Constants.goldAddedPerLevel;
            PlayerData.currentGold = PlayerData.currentGold + PlayerData.totalEarnedGoldInLevel;
            TextView goldEarnedDisplay = (TextView) findViewById(R.id.textView23);
            TextView currentGoldDisplay = (TextView) findViewById(R.id.textView25);
            goldEarnedDisplay.setText("" + PlayerData.totalEarnedGoldInLevel);
            currentGoldDisplay.setText("" + PlayerData.currentGold);
        }
        final View button = v;
        putOffScreen(button);
        v.postDelayed(new Runnable() {
                public void run() {
                    moveBack(button);
                }
            }, 510);

    }
    private static void moveBack(View v){
        v.setY(1337);
    }

    private static void putOffScreen(View v) {
        v.setY(10000);
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
