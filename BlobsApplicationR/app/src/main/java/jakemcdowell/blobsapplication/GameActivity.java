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
import java.util.List;

import jakemcdowell.blobsapplication.bugs.Bug;
import jakemcdowell.blobsapplication.bugs.FireBug;
import jakemcdowell.blobsapplication.bugs.MovingBug;
import jakemcdowell.blobsapplication.bugs.SmallBug;
import jakemcdowell.blobsapplication.bugs.TeleportingBug;
import static jakemcdowell.blobsapplication.Constants.goldAddedPerLevel;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private int buttonIds[] = new int[10];
    private boolean isInitialized = false;
    private ProgressBar timeProgressbar;
    private boolean gameActivityIsPaused = false;
    private GoldButton goldButton;
    private View upgradeImages[] = new View[10];
    boolean nextLevelScreen = false;
    boolean sandyFirstRun = true;
    boolean gravelFirstRun = true;
    boolean leafFirstRun = true;
    boolean desertFirstRun = true;
    boolean snowFirstRun = true;
    boolean grassyFirstRun = true;
    boolean goingToNextLevelScreen = false;
    private Game game;
    private boolean isOnNextLevelPage = false;

    //Sets up game screen (progress bar)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);

        PlayerData.shouldResumeGame = false;

        Button buttons[] = new Button[10];
        ProgressBar progressBar[] = new ProgressBar[10];
        int progressBarIds[] = new int[10];
        int health = Constants.INITIAL_HEALTH;
        int kOsPerDeath = Constants.KOS_PER_DEATH;

        goldButton = new GoldButton(findViewById(R.id.goldRandomButton));
        goldButton.getButton().setOnClickListener(this);

        //0-2 damage upgrade
        //3-6 radius upgrade
        //7-9 gold upgrade
        upgradeImages[0] = findViewById(R.id.imageView29);
        upgradeImages[1] = findViewById(R.id.imageView30);
        upgradeImages[2] = findViewById(R.id.textView27);
        upgradeImages[3] = findViewById(R.id.imageView31);
        upgradeImages[4] = findViewById(R.id.imageView32);
        upgradeImages[5] = findViewById(R.id.imageView33);
        upgradeImages[6] = findViewById(R.id.textView28);
        upgradeImages[7] = findViewById(R.id.imageView34);
        upgradeImages[8] = findViewById(R.id.imageView35);
        upgradeImages[9] = findViewById(R.id.textView29);

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
        List<Bug> bugList = new ArrayList<>();
        bugList.add(new Bug(buttons[0], health, progressBar[0], kOsPerDeath));
        bugList.add(new Bug(buttons[1], health, progressBar[1], kOsPerDeath));
        bugList.add(new SmallBug(buttons[2], health, progressBar[2], kOsPerDeath));
        bugList.add(new SmallBug(buttons[3], health, progressBar[3], kOsPerDeath));
        bugList.add(new MovingBug(buttons[4], health, progressBar[4], kOsPerDeath));
        bugList.add(new MovingBug(buttons[5], health, progressBar[5], kOsPerDeath));
        bugList.add(new TeleportingBug(buttons[6], health, progressBar[6], kOsPerDeath));
        bugList.add(new TeleportingBug(buttons[7], health, progressBar[7], kOsPerDeath));
        bugList.add(new Bug(buttons[8], health, progressBar[8], kOsPerDeath));
        bugList.add(new FireBug(buttons[9], health, progressBar[9], kOsPerDeath));

        //Creates new Game
        ProgressBar levelProgress = (ProgressBar) this.findViewById(R.id.progressBar2);
        game = new Game(levelProgress, bugList, findViewById(R.id.goldRandomButton), goldButton);
        // Sets the upgrade images on sideBar
        for (View view : upgradeImages) {
            view.setVisibility(View.GONE);
        }
        if (PlayerData.damageIncreaseLevel == 0 && PlayerData.radiusIncreaseLevel == 0 && PlayerData.goldIncreaseLevel == 0) {
            TextView upgradetitle = (TextView) findViewById(R.id.textView26);
            upgradetitle.setText("You don't have any Upgrades! Go buy some in the Shop!");
        } else {
            if (PlayerData.damageIncreaseLevel != 0) {
                setDamageImage("Level " + PlayerData.damageIncreaseLevel);
            }
            if (PlayerData.radiusIncreaseLevel != 0) {
                setRadiusImage("Level " + PlayerData.radiusIncreaseLevel);
            }
            if (PlayerData.goldIncreaseLevel != 0) {
                setGoldImage("Level " + PlayerData.goldIncreaseLevel);
            }
        }
        if (PlayerData.numberOfPesticide == 0) {
            findViewById(R.id.pesticide).setVisibility(View.GONE);
        }
        //Updates level marker
        startCountDownTimer();
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
        if (PlayerData.currentLevel % 25 == 0 && PlayerData.currentLevel % 25 < 5) {
            snowFirstRun = true;
            j.setBackground(o);
            MediaPlayer grassymain = MediaPlayer.create(this, R.raw.grassymain);
            if (grassyFirstRun) {
                MusicPlayer.startSong(grassymain);
                grassyFirstRun = false;
            }
        }
        if (PlayerData.currentLevel % 25 > 4 && PlayerData.currentLevel % 25 < 9) {
            grassyFirstRun = true;
            j.setBackground(k);
            MediaPlayer sandymain = MediaPlayer.create(this, R.raw.sandymain);
            if (sandyFirstRun) {
                MusicPlayer.startSong(sandymain);
                sandyFirstRun = false;
            }
        }
        if (PlayerData.currentLevel % 25 > 8 && PlayerData.currentLevel % 25 < 13) {
            sandyFirstRun = true;
            j.setBackground(l);
            MediaPlayer gravelmain = MediaPlayer.create(this, R.raw.gravelmain);
            if (gravelFirstRun) {
                MusicPlayer.startSong(gravelmain);
                gravelFirstRun = false;
            }
        }
        if (PlayerData.currentLevel % 25 > 12 && PlayerData.currentLevel % 25 < 17) {
            gravelFirstRun = true;
            j.setBackground(m);
            MediaPlayer leafmain = MediaPlayer.create(this, R.raw.leafmain);
            if (leafFirstRun) {
                MusicPlayer.startSong(leafmain);
                leafFirstRun = false;
            }
        }
        if (PlayerData.currentLevel % 25 > 16 && PlayerData.currentLevel % 25 < 21) {
            leafFirstRun = true;
            j.setBackground(n);
            MediaPlayer desertmain = MediaPlayer.create(this, R.raw.desertmain);
            if (desertFirstRun) {
                MusicPlayer.startSong(desertmain);
                desertFirstRun = false;
            }
        }
        if (PlayerData.currentLevel % 25 > 20 && PlayerData.currentLevel % 25 < 24) {
            desertFirstRun = true;
            j.setBackground(p);
            MediaPlayer snowmain = MediaPlayer.create(this, R.raw.snowmain);
            if (snowFirstRun) {
                MusicPlayer.startSong(snowmain);
                snowFirstRun = false;
            }
        }
        TextView f = (TextView) findViewById(R.id.textView1);
        f.setText("Level: " + PlayerData.currentLevel);
        TextView g = (TextView) findViewById(R.id.textView31);
        g.setText("Remaining: " + PlayerData.numberOfPesticide);
        startCountDownTimer();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (!PlayerData.shouldResumeGame) {
            game.moveAllBugsInLevel();
        }

        if (isOnNextLevelPage) {
            game.resetBugsToInitialState();
            isOnNextLevelPage = false;
        }

        sandyFirstRun = true;
        gravelFirstRun = true;
        leafFirstRun = true;
        desertFirstRun = true;
        snowFirstRun = true;
        grassyFirstRun = true;

        // Starts moving gold
        goldButton.startRandomGoldMove();

        //starts every animation, countdown timer,
        if (!isInitialized) {
            for (Bug bug : game.getBugs()) {
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
            if(PlayerData.currentLevel % 25 == 1 || PlayerData.currentLevel % 25 < 5) {
                snowFirstRun = true;
                constraintLayout.setBackground(grassy);
                MediaPlayer grassymain = MediaPlayer.create(this, R.raw.grassymain);
                if (grassyFirstRun == true) {
                    MusicPlayer.startSong(grassymain);
                    grassyFirstRun = false;
                }
            }
            if(PlayerData.currentLevel % 25 > 4 && PlayerData.currentLevel % 25 < 9) {
                grassyFirstRun = true;
                constraintLayout.setBackground(sandy);
                MediaPlayer sandymain = MediaPlayer.create(this, R.raw.sandymain);
                if (sandyFirstRun == true) {
                    MusicPlayer.startSong(sandymain);
                    sandyFirstRun = false;
                }
            }
            if(PlayerData.currentLevel % 25 > 8 && PlayerData.currentLevel % 25 < 13){
                sandyFirstRun = true;
                constraintLayout.setBackground(gravel);
                MediaPlayer gravelmain = MediaPlayer.create(this, R.raw.gravelmain);
                if (gravelFirstRun == true) {
                    MusicPlayer.startSong(gravelmain);
                    gravelFirstRun = false;
                }
            }
            if(PlayerData.currentLevel % 25 > 12 && PlayerData.currentLevel%25 < 17){
                gravelFirstRun = true;
                constraintLayout.setBackground(leaf);
                MediaPlayer leafmain = MediaPlayer.create(this, R.raw.leafmain);
                if (leafFirstRun == true) {
                    MusicPlayer.startSong(leafmain);
                    leafFirstRun = false;
                }
            }
            if(PlayerData.currentLevel % 25 > 16 && PlayerData.currentLevel%25 < 21){
                leafFirstRun = true;
                constraintLayout.setBackground(desert);
                MediaPlayer desertmain = MediaPlayer.create(this, R.raw.desertmain);
                if (desertFirstRun == true) {
                    MusicPlayer.startSong(desertmain);
                    desertFirstRun = false;
                }
            }
            if(PlayerData.currentLevel % 25 > 20 && PlayerData.currentLevel%25 < 24){
                desertFirstRun = true;
                constraintLayout.setBackground(snow);
                MediaPlayer snowmain = MediaPlayer.create(this, R.raw.snowmain);
                if (snowFirstRun == true) {
                    MusicPlayer.startSong(snowmain);
                    snowFirstRun = false;
                }
            }
        } else if (!goingToNextLevelScreen) {
            game.setupLevel(PlayerData.currentLevel);
        }
        goingToNextLevelScreen = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!nextLevelScreen) {

            game.damageNear((int) event.getX(), (int) event.getY());

            if (game.isAllDead()) {
                nextLevelScreen = true;
            }
        }
        return super.onTouchEvent(event);
    }

    public void onPause() {
        super.onPause();
        gameActivityIsPaused = true;
    }
    public void onResume() {
        super.onResume();
        gameActivityIsPaused = false;
        game.moveAllBugsInLevel();
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
                    if (PlayerData.currentLevel > PlayerData.highestLevel) {
                        PlayerData.highestLevel = PlayerData.currentLevel;
                    }
                    gameOver();
                }
            }

        }).start();
    }

    public void gameOver() {
        PlayerData.totalEarnedGoldInLevel = 0;
        PlayerData.currentLevel = 1;
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onClick(View v) {
        goldButton.click();
    }

    public void resetCountDownTimer() {
        timeProgressbar.setProgress(100);
    }

    //Button game mechanics
    public void buttonTestClick(View v) {
        //sets +1 tap on the bug
        findBug(v).damageBug(game);
    }

    public void allBugsDead() {
        if (PlayerData.currentGold + game.getGoldEarnedInLevel() + goldButton.getGoldEarnedInLevel()
                <= 9_999_999) {
            PlayerData.currentGold += game.getGoldEarnedInLevel() + goldButton.getGoldEarnedInLevel();
        }
        PlayerData.highestLevel = PlayerData.currentLevel;

        game.resetBugsToInitialState();

        isOnNextLevelPage = true;

        findViewById(R.id.button2).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.textView23)).setText("" + game.getGoldEarnedInLevel() + goldButton.getGoldEarnedInLevelString());
        ((TextView) findViewById(R.id.textView25)).setText("" + PlayerData.currentGold);
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

        if(PlayerData.currentLevel%25 == 4) {
            MediaPlayer grassyend = MediaPlayer.create(this, R.raw.grassyend);
            MusicPlayer.playEndingMusic(grassyend);
        }
        if(PlayerData.currentLevel%25 == 8) {
            MediaPlayer sandyend = MediaPlayer.create(this, R.raw.sandyend);
            MusicPlayer.playEndingMusic(sandyend);
        }
        if(PlayerData.currentLevel%25 == 12) {
            MediaPlayer gravelend = MediaPlayer.create(this, R.raw.gravelend);
            MusicPlayer.playEndingMusic(gravelend);
        }
        if(PlayerData.currentLevel%25 == 16) {
            MediaPlayer leafend = MediaPlayer.create(this, R.raw.leafend);
            MusicPlayer.playEndingMusic(leafend);
        }
        if(PlayerData.currentLevel%25 == 20) {
            MediaPlayer desertend = MediaPlayer.create(this, R.raw.desertend);
            MusicPlayer.playEndingMusic(desertend);
        }
        if(PlayerData.currentLevel%25 == 24) {
            MediaPlayer snowend = MediaPlayer.create(this, R.raw.snowend);
            MusicPlayer.playEndingMusic(snowend);
        }
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
    }

    public void returnToMenuClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        PlayerData.shouldResumeGame = true;
        if (PlayerData.currentLevel > PlayerData.highestLevel) {
            PlayerData.highestLevel = PlayerData.currentLevel;
        }
        if (game.isAllDead()) {
            PlayerData.currentLevel++;
            PlayerData.shouldBeginGame = true;
        }
        goingToNextLevelScreen = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //code rand for nextLevelButtonClick
    public void nextLevelButtonClick(View v) {
        //checks to see if new bug on screen should be added

        game.nextLevel();
        goldButton.resetButton();

        startCountDownTimer();

        //Updates level marker
        TextView f = (TextView) findViewById(R.id.textView1);
        f.setText("Level: " + PlayerData.currentLevel);
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
            if (PlayerData.currentLevel % 25 == 0 && PlayerData.currentLevel % 25 < 5) {
                snowFirstRun = true;
                j.setBackground(o);
                MediaPlayer grassymain = MediaPlayer.create(this, R.raw.grassymain);
                if (grassyFirstRun == true) {
                    MusicPlayer.startSong(grassymain);
                    grassyFirstRun = false;
                }
            }
            if (PlayerData.currentLevel % 25 > 4 && PlayerData.currentLevel % 25 < 9) {
                grassyFirstRun = true;
                j.setBackground(k);
                MediaPlayer sandymain = MediaPlayer.create(this, R.raw.sandymain);
                if (sandyFirstRun == true) {
                    MusicPlayer.startSong(sandymain);
                    sandyFirstRun = false;
                }
            }
            if (PlayerData.currentLevel % 25 > 8 && PlayerData.currentLevel % 25 < 13) {
                sandyFirstRun = true;
                j.setBackground(l);
                MediaPlayer gravelmain = MediaPlayer.create(this, R.raw.gravelmain);
                if (gravelFirstRun == true) {
                    MusicPlayer.startSong(gravelmain);
                    gravelFirstRun = false;
                }
            }
            if (PlayerData.currentLevel % 25 > 12 && PlayerData.currentLevel % 25 < 17) {
                gravelFirstRun = true;
                j.setBackground(m);
                MediaPlayer leafmain = MediaPlayer.create(this, R.raw.leafmain);
                if (leafFirstRun == true) {
                    MusicPlayer.startSong(leafmain);
                    leafFirstRun = false;
                }
            }
            if (PlayerData.currentLevel % 25 > 16 && PlayerData.currentLevel % 25 < 21) {
                leafFirstRun = true;
                j.setBackground(n);
                MediaPlayer desertmain = MediaPlayer.create(this, R.raw.desertmain);
                if (desertFirstRun == true) {
                    MusicPlayer.startSong(desertmain);
                    desertFirstRun = false;
                }
            }
            if (PlayerData.currentLevel % 25 > 20 && PlayerData.currentLevel % 25 < 24) {
                desertFirstRun = true;
                j.setBackground(p);
                MediaPlayer snowmain = MediaPlayer.create(this, R.raw.snowmain);
                if (snowFirstRun == true) {
                    MusicPlayer.startSong(snowmain);
                    snowFirstRun = false;
                }
            }
            nextLevelScreen = false;
        }
    public void pesticideUse(View v) {
        if (game.isAllDead() == false) {
            game.pesticide();
            for (Bug bug : game.bugsInLevel) {
                if (game.isAllDead()) {
                    break;
                }
                bug.damageBug(game);
            }
            if (PlayerData.numberOfPesticide > 0) {
                PlayerData.numberOfPesticide = PlayerData.numberOfPesticide - 1;
            }
            if (PlayerData.numberOfPesticide == 0) {
                findViewById(R.id.pesticide).setVisibility(View.GONE);
            }
            TextView g = (TextView) findViewById(R.id.textView31);
            g.setText("Remaining: " + PlayerData.numberOfPesticide);
            if (game.isAllDead()) {
                allBugsDead();
                final View button = v;
                putOffScreen(button);
                v.postDelayed(new Runnable() {
                    public void run() {
                        moveBack(button);
                    }
                }, 510);
            }

        }
    }
    private static void moveBack(View v){
        v.setY(1337);
    }

    private static void putOffScreen(View v) {
        v.setY(10000);
    }

    private Bug findBug(View view) {
        Bug result = null;
        for (Bug bug : game.getBugs()) {
            if (bug.hasButton(view)) {
                result = bug;
            }
        }
        return result;
    }

    private void setDamageImage(String str) {
        upgradeImages[0].setVisibility(View.VISIBLE);
        upgradeImages[1].setVisibility(View.VISIBLE);
        upgradeImages[2].setVisibility(View.VISIBLE);
        ((TextView) upgradeImages[2]).setText(str);
    }

    private void setRadiusImage(String str) {
        upgradeImages[3].setVisibility(View.VISIBLE);
        upgradeImages[4].setVisibility(View.VISIBLE);
        upgradeImages[5].setVisibility(View.VISIBLE);
        upgradeImages[6].setVisibility(View.VISIBLE);
        ((TextView) upgradeImages[6]).setText(str);
    }

    private void setGoldImage(String str) {
        upgradeImages[7].setVisibility(View.VISIBLE);
        upgradeImages[8].setVisibility(View.VISIBLE);
        upgradeImages[9].setVisibility(View.VISIBLE);
        ((TextView) upgradeImages[9]).setText(str);
    }
}
