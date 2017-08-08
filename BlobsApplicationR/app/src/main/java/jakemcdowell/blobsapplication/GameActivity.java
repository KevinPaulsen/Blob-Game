package jakemcdowell.blobsapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.ProgressBar;
import android.graphics.drawable.AnimationDrawable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

import jakemcdowell.blobsapplication.bugs.Bug;
import jakemcdowell.blobsapplication.bugs.FireBug;
import jakemcdowell.blobsapplication.bugs.MovingBug;
import jakemcdowell.blobsapplication.bugs.SmallBug;
import jakemcdowell.blobsapplication.bugs.TeleportingBug;

import static jakemcdowell.blobsapplication.Constants.goldAddedPerLevel;
import static jakemcdowell.blobsapplication.Constants.radiusIncreaseLevel1;
import static jakemcdowell.blobsapplication.bugs.Bug.scheduler;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;


public class GameActivity extends AppCompatActivity implements View.OnClickListener{
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
    private int goldEarnedByRandom = 0;
    private boolean gameactivityispaused = false;
    Button goldButton;

    boolean sandyfirstrun = true;
    boolean gravelfirstrun = true;
    boolean leaffirstrun = true;
    boolean desertfirstrun = true;
    boolean snowfirstrun = true;
    boolean grassyfirstrun = true;

    //Sets up game screen (progress bar)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sandyfirstrun = true;
        gravelfirstrun = true;
        leaffirstrun = true;
        desertfirstrun = true;
        snowfirstrun = true;
        grassyfirstrun = true;

        Button buttons[] = new Button[10];
        ProgressBar progressBar[] = new ProgressBar[10];
        int buttonIds[] = new int[10];
        int progressBarIds[] = new int[10];
        int health = 1;
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
        timeprogressbar = (ProgressBar) this.findViewById(R.id.timeProgressBar);

        for (int count = 0; count < 10; count++) {
            //Instantiates all bug buttons, and  puts them offscreen
            buttons[count] = (Button) findViewById(buttonIds[count]);
            if (count < 2) {
                buttons[count].setBackgroundResource(R.drawable.normalbuganimation);
            } else if (count < 4) {
                buttons[count].setBackgroundResource(R.drawable.smallbuganimation);
            } else if (count < 6) {
                buttons[count].setBackgroundResource(R.drawable.movingbuganimation);
            } else if (count < 8) {
                buttons[count].setBackgroundResource(R.drawable.teleportingbuganimation);
            } else if (count < 10) {
                buttons[count].setBackgroundResource(R.drawable.armedfirebuganimation);
            }
            putOffScreen(buttons[count]);
            //Instantiates all progresssBars, and  puts them offscreen
            progressBar[count] = (ProgressBar) findViewById(progressBarIds[count]);
            putOffScreen(progressBar[count]);
            // Get all the bug animation objects
            if (count < 2) {
                normalbuganimations.add((AnimationDrawable) buttons[count].getBackground());
            } else if (count < 4) {
                smallbuganimations.add((AnimationDrawable) buttons[count].getBackground());
            } else if (count < 6) {
                movingbuganimations.add((AnimationDrawable) buttons[count].getBackground());
            } else if (count < 8) {
                teleportingbuganimations.add((AnimationDrawable) buttons[count].getBackground());
            } else if (count < 10) {
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
        ProgressBar levelProgress = (ProgressBar) this.findViewById(R.id.progressBar2);
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
        if (PlayerData.continueLevel == true) {
            PlayerData.continueLevel = false;
            PlayerData.continueLevel1 = false;
            //NEXTLEVELBUTTONCLICKCALLED
            //checks to see if new bug on screen should be added


            //Updates level marker
            TextView f = (TextView) findViewById(R.id.textView1);
            game.nextLevel();
            startCountDownTimer();
            f.setText("Level: " + game.getLevel());
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
                snowfirstrun = true;
                j.setBackground(o);
                MediaPlayer grassymain = MediaPlayer.create(this, R.raw.grassymain);
                if (grassyfirstrun == true) {
                    MusicPlayer.startSong(grassymain);
                    grassyfirstrun = false;
                }
            }
            if (game.getLevel() % 25 > 4 && game.getLevel() % 25 < 9) {
                grassyfirstrun = true;
                j.setBackground(k);
                MediaPlayer sandymain = MediaPlayer.create(this, R.raw.sandymain);
                if (sandyfirstrun == true) {
                    MusicPlayer.startSong(sandymain);
                    sandyfirstrun = false;
                }
            }
            if (game.getLevel() % 25 > 8 && game.getLevel() % 25 < 13) {
                sandyfirstrun = true;
                j.setBackground(l);
                MediaPlayer gravelmain = MediaPlayer.create(this, R.raw.gravelmain);
                if (gravelfirstrun == true) {
                    MusicPlayer.startSong(gravelmain);
                    gravelfirstrun = false;
                }
            }
            if (game.getLevel() % 25 > 12 && game.getLevel() % 25 < 17) {
                gravelfirstrun = true;
                j.setBackground(m);
                MediaPlayer leafmain = MediaPlayer.create(this, R.raw.leafmain);
                if (leaffirstrun == true) {
                    MusicPlayer.startSong(leafmain);
                    leaffirstrun = false;
                }
            }
            if (game.getLevel() % 25 > 16 && game.getLevel() % 25 < 21) {
                leaffirstrun = true;

                MediaPlayer desertmain = MediaPlayer.create(this, R.raw.desertmain);
                if (desertfirstrun == true) {
                    MusicPlayer.startSong(desertmain);
                    desertfirstrun = false;
                }
            }
            if (game.getLevel() % 25 > 20 && game.getLevel() % 25 < 24) {
                desertfirstrun = true;
                j.setBackground(p);
                MediaPlayer snowmain = MediaPlayer.create(this, R.raw.snowmain);
                if (snowfirstrun == true) {
                    MusicPlayer.startSong(snowmain);
                    snowfirstrun = false;
                }
            }
        }
        TextView f = (TextView) findViewById(R.id.textView1);
        f.setText("Level: " + game.getLevel());
        TextView g = (TextView) findViewById(R.id.textView31);
        g.setText("Remaining: " + PlayerData.numberOfPesticide);

    }

    public void onWindowFocusChanged(boolean hasFocus) {
        sandyfirstrun = true;
        gravelfirstrun = true;
        leaffirstrun = true;
        desertfirstrun = true;
        snowfirstrun = true;
        grassyfirstrun = true;
        findViewById(R.id.button).setClickable(false);
        findViewById(R.id.button5).setClickable(false);
        findViewById(R.id.button6).setClickable(false);
        findViewById(R.id.button7).setClickable(false);
        findViewById(R.id.button8).setClickable(false);
        findViewById(R.id.button9).setClickable(false);
        findViewById(R.id.button10).setClickable(false);
        findViewById(R.id.button11).setClickable(false);
        findViewById(R.id.button12).setClickable(false);
        findViewById(R.id.button13).setClickable(false);
        randomGoldMove();
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
            ConstraintLayout j = (ConstraintLayout)findViewById(R.id.Constraint);
            Drawable k = getDrawable(R.drawable.sandy);
            Drawable l = getDrawable(R.drawable.gravel);
            Drawable m = getDrawable(R.drawable.leaf);
            Drawable n = getDrawable(R.drawable.desert);
            Drawable o = getDrawable(R.drawable.grassy);
            Drawable p = getDrawable(R.drawable.snow);
            if(game.getLevel()%25 == 1 || game.getLevel()%25 < 5) {
                snowfirstrun = true;
                j.setBackground(o);
                MediaPlayer grassymain = MediaPlayer.create(this, R.raw.grassymain);
                if (grassyfirstrun == true) {
                    MusicPlayer.startSong(grassymain);
                    grassyfirstrun = false;
                }
            }
            if(game.getLevel()%25 > 4 && game.getLevel()%25 < 9) {
                grassyfirstrun = true;
                j.setBackground(k);
                MediaPlayer sandymain = MediaPlayer.create(this, R.raw.sandymain);
                if (sandyfirstrun == true) {
                    MusicPlayer.startSong(sandymain);
                    sandyfirstrun = false;
                }
            }
            if(game.getLevel()%25 > 8 && game.getLevel()%25 < 13){
                sandyfirstrun = true;
                j.setBackground(l);
                MediaPlayer gravelmain = MediaPlayer.create(this, R.raw.gravelmain);
                if (gravelfirstrun == true) {
                    MusicPlayer.startSong(gravelmain);
                    gravelfirstrun = false;
                }
            }
            if(game.getLevel()%25 > 12 && game.getLevel()%25 < 17){
                gravelfirstrun = true;
                j.setBackground(m);
                MediaPlayer leafmain = MediaPlayer.create(this, R.raw.leafmain);
                if (leaffirstrun == true) {
                    MusicPlayer.startSong(leafmain);
                    leaffirstrun = false;
                }
            }
            if(game.getLevel()%25 > 16 && game.getLevel()%25 < 21){
                leaffirstrun = true;
                j.setBackground(n);
                MediaPlayer desertmain = MediaPlayer.create(this, R.raw.desertmain);
                if (desertfirstrun == true) {
                    MusicPlayer.startSong(desertmain);
                    desertfirstrun = false;
                }
            }
            if(game.getLevel()%25 > 20 && game.getLevel()%25 < 24){
                desertfirstrun = true;
                j.setBackground(p);
                MediaPlayer snowmain = MediaPlayer.create(this, R.raw.snowmain);
                if (snowfirstrun == true) {
                    MusicPlayer.startSong(snowmain);
                    snowfirstrun = false;
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // MotionEvent object holds X-Y values
        if (PlayerData.radiusIncreaseLevel == 0) {
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel0) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                findBug(bugbutton1).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel0) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                findBug(bugbutton2).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel0) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                findBug(bugbutton3).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel0) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                findBug(bugbutton4).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel0) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                findBug(bugbutton5).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel0) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                findBug(bugbutton6).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel0) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                findBug(bugbutton7).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel0) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                findBug(bugbutton8).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel0) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                findBug(bugbutton9).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel0) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                findBug(bugbutton10).damageBug(game);
            }
        }
        if (PlayerData.radiusIncreaseLevel == 1) {
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                findBug(bugbutton1).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                findBug(bugbutton2).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                findBug(bugbutton3).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                findBug(bugbutton4).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                findBug(bugbutton5).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                findBug(bugbutton6).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                findBug(bugbutton7).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                findBug(bugbutton8).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                findBug(bugbutton9).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                findBug(bugbutton10).damageBug(game);
            }
        }
        if (PlayerData.radiusIncreaseLevel == 2) {
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                findBug(bugbutton1).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                findBug(bugbutton2).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                findBug(bugbutton3).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                findBug(bugbutton4).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                findBug(bugbutton5).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                findBug(bugbutton6).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                findBug(bugbutton7).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                findBug(bugbutton8).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                findBug(bugbutton9).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                findBug(bugbutton10).damageBug(game);
            }
        }
        if (PlayerData.radiusIncreaseLevel == 3) {
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                findBug(bugbutton1).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                findBug(bugbutton2).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                findBug(bugbutton3).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                findBug(bugbutton4).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                findBug(bugbutton5).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                findBug(bugbutton6).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                findBug(bugbutton7).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                findBug(bugbutton8).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                findBug(bugbutton9).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                findBug(bugbutton10).damageBug(game);
            }
        }
        if (PlayerData.radiusIncreaseLevel == 4) {
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                findBug(bugbutton1).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                findBug(bugbutton2).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                findBug(bugbutton3).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                findBug(bugbutton4).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                findBug(bugbutton5).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                findBug(bugbutton6).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                findBug(bugbutton7).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                findBug(bugbutton8).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                findBug(bugbutton9).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                findBug(bugbutton10).damageBug(game);
            }
        }
        if (PlayerData.radiusIncreaseLevel == 5) {
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                findBug(bugbutton1).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                findBug(bugbutton2).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                findBug(bugbutton3).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                findBug(bugbutton4).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                findBug(bugbutton5).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                findBug(bugbutton6).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                findBug(bugbutton7).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                findBug(bugbutton8).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                findBug(bugbutton9).damageBug(game);
            }
            if (getDistance(event.getX(),event.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                findBug(bugbutton10).damageBug(game);
            }
        }
        if (game.isAllDead()) {
            allBugsDead();
        }
        return super.onTouchEvent(event);
    }

    public int getDistance(float centerpointx, float centerpointy, float buttonpointx, float buttonpointy) {
        float number1 = buttonpointx - centerpointx;
        float number2 = buttonpointy - centerpointy;
        number1 = number1*number1;
        number2 = number2*number2;
        return (int) Math.sqrt(number1+number2);
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
                    if (game.getLevel() > PlayerData.highestLevel) {
                        PlayerData.highestLevel = game.getLevel();
                    }
                    PlayerData.currentLevel = 1;
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
        }, 3000, SECONDS);
    }

    public void onClick(View v) {
        goldButtonClick();
    }

    public void goldButtonClick() {
        moveOffScreen(goldButton);
        goldEarnedByRandom = goldAddedPerLevel / game.getLevel();
        PlayerData.currentGold = PlayerData.currentGold + goldEarnedByRandom;
        goldearnedbylevel = goldearnedbylevel + goldEarnedByRandom;
    }

    //Button game mechanics
    public void buttonTestClick(View v) {
        Bug bug1 = findBug(v);
        //sets +1 tap on the bug
        game.radiusDamage(bug1, game);

        /*if (PlayerData.radiusIncreaseLevel == 1) {
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                if(findViewById(R.id.button) != v) {
                    findBug(bugbutton1).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                if(findViewById(R.id.button5) != v) {
                    findBug(bugbutton2).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                if(findViewById(R.id.button6) != v) {
                    findBug(bugbutton3).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                if(findViewById(R.id.button7) != v) {
                    findBug(bugbutton4).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                if(findViewById(R.id.button8) != v) {
                    findBug(bugbutton5).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                if(findViewById(R.id.button9) != v) {
                    findBug(bugbutton6).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                if(findViewById(R.id.button10) != v) {
                    findBug(bugbutton7).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                if(findViewById(R.id.button11) != v) {
                    findBug(bugbutton8).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                if(findViewById(R.id.button12) != v) {
                    findBug(bugbutton9).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel1) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                if(findViewById(R.id.button13) != v) {
                    findBug(bugbutton10).damageBug(game);
                }
            }
        }
        if (PlayerData.radiusIncreaseLevel == 2) {
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                if(findViewById(R.id.button) != v) {
                    findBug(bugbutton1).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                if(findViewById(R.id.button5) != v) {
                    findBug(bugbutton2).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                if(findViewById(R.id.button6) != v) {
                    findBug(bugbutton3).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                if(findViewById(R.id.button7) != v) {
                    findBug(bugbutton4).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                if(findViewById(R.id.button8) != v) {
                    findBug(bugbutton5).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                if(findViewById(R.id.button9) != v) {
                    findBug(bugbutton6).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                if(findViewById(R.id.button10) != v) {
                    findBug(bugbutton7).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                if(findViewById(R.id.button11) != v) {
                    findBug(bugbutton8).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                if(findViewById(R.id.button12) != v) {
                    findBug(bugbutton9).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel2) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                if(findViewById(R.id.button13) != v) {
                    findBug(bugbutton10).damageBug(game);
                }
            }
        }
        if (PlayerData.radiusIncreaseLevel == 3) {
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                if(findViewById(R.id.button) != v) {
                    findBug(bugbutton1).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                if(findViewById(R.id.button5) != v) {
                    findBug(bugbutton2).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                if(findViewById(R.id.button6) != v) {
                    findBug(bugbutton3).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                if(findViewById(R.id.button7) != v) {
                    findBug(bugbutton4).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                if(findViewById(R.id.button8) != v) {
                    findBug(bugbutton5).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                if(findViewById(R.id.button9) != v) {
                    findBug(bugbutton6).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                if(findViewById(R.id.button10) != v) {
                    findBug(bugbutton7).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                if(findViewById(R.id.button11) != v) {
                    findBug(bugbutton8).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                if(findViewById(R.id.button12) != v) {
                    findBug(bugbutton9).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel3) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                if(findViewById(R.id.button13) != v) {
                    findBug(bugbutton10).damageBug(game);
                }
            }
        }
        if (PlayerData.radiusIncreaseLevel == 4) {
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                if(findViewById(R.id.button) != v) {
                    findBug(bugbutton1).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                if(findViewById(R.id.button5) != v) {
                    findBug(bugbutton2).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                if(findViewById(R.id.button6) != v) {
                    findBug(bugbutton3).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                if(findViewById(R.id.button7) != v) {
                    findBug(bugbutton4).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                if(findViewById(R.id.button8) != v) {
                    findBug(bugbutton5).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                if(findViewById(R.id.button9) != v) {
                    findBug(bugbutton6).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                if(findViewById(R.id.button10) != v) {
                    findBug(bugbutton7).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                if(findViewById(R.id.button11) != v) {
                    findBug(bugbutton8).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                if(findViewById(R.id.button12) != v) {
                    findBug(bugbutton9).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel4) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                if(findViewById(R.id.button13) != v) {
                    findBug(bugbutton10).damageBug(game);
                }
            }
        }
        if (PlayerData.radiusIncreaseLevel == 5) {
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button).getX(),findViewById(R.id.button).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton1 = (Button) findViewById(R.id.button);
                if(findViewById(R.id.button) != v) {
                    findBug(bugbutton1).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button5).getX(),findViewById(R.id.button5).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton2 = (Button) findViewById(R.id.button5);
                if(findViewById(R.id.button5) != v) {
                    findBug(bugbutton2).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button6).getX(),findViewById(R.id.button6).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton3 = (Button) findViewById(R.id.button6);
                if(findViewById(R.id.button6) != v) {
                    findBug(bugbutton3).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button7).getX(),findViewById(R.id.button7).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton4 = (Button) findViewById(R.id.button7);
                if(findViewById(R.id.button7) != v) {
                    findBug(bugbutton4).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button8).getX(),findViewById(R.id.button8).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton5 = (Button) findViewById(R.id.button8);
                if(findViewById(R.id.button8) != v) {
                    findBug(bugbutton5).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button9).getX(),findViewById(R.id.button9).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton6 = (Button) findViewById(R.id.button9);
                if(findViewById(R.id.button9) != v) {
                    findBug(bugbutton6).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button10).getX(),findViewById(R.id.button10).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton7 = (Button) findViewById(R.id.button10);
                if(findViewById(R.id.button10) != v) {
                    findBug(bugbutton7).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button11).getX(),findViewById(R.id.button11).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton8 = (Button) findViewById(R.id.button11);
                if(findViewById(R.id.button11) != v) {
                    findBug(bugbutton8).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button12).getX(),findViewById(R.id.button12).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton9 = (Button) findViewById(R.id.button12);
                if(findViewById(R.id.button12) != v) {
                    findBug(bugbutton9).damageBug(game);
                }
            }
            if (getDistance(v.getX(),v.getY(),findViewById(R.id.button13).getX(),findViewById(R.id.button13).getY()) <= Constants.radiusIncreaseLevel5) {
                Button bugbutton10 = (Button) findViewById(R.id.button13);
                if(findViewById(R.id.button13) != v) {
                    findBug(bugbutton10).damageBug(game);
                }
            }
        }*/
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
        goldearnedbylevel = goldearnedbylevel + goldAddedPerLevel;
        PlayerData.currentGold = PlayerData.currentGold + goldearnedbylevel;
        TextView goldEarnedDisplay = (TextView) findViewById(R.id.textView23);
        TextView currentGoldDisplay = (TextView) findViewById(R.id.textView25);
        goldEarnedDisplay.setText("" + goldearnedbylevel);
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
            goldearnedbylevel = goldearnedbylevel + goldAddedPerLevel;
            PlayerData.currentGold = PlayerData.currentGold + goldearnedbylevel;
            goldEarnedDisplay.setText("" + goldearnedbylevel);
            currentGoldDisplay.setText("" + PlayerData.currentGold);
        }
    }
    public void returnToMenuClick(View v) {
            if (game.getLevel() > PlayerData.highestLevel) {
                PlayerData.highestLevel = game.getLevel();
            }
        if(game.getLevel() != 1) {

            PlayerData.currentLevel = game.getLevel() - 1;

            PlayerData.continueLevel = true;
            for (Bug bug : game.bugsInLevel) {
                bug.resetBugToInitialState();
            }
            game.resetKOBar();
        }
        else{
            PlayerData.continueLevel1 = true;
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
        PlayerData.currentLevel = game.getLevel();
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
        if (game.getLevel() % 25 == 0 || game.getLevel() % 25 < 5) {
            if (PlayerData.numberOfPesticide != 0) {
                findViewById(R.id.pesticide).setVisibility(View.VISIBLE);
            }

            if (game.getLevel() % 25 == 0 && game.getLevel() % 25 < 5) {
                snowfirstrun = true;
                j.setBackground(o);
                MediaPlayer grassymain = MediaPlayer.create(this, R.raw.grassymain);
                if (grassyfirstrun == true) {
                    MusicPlayer.startSong(grassymain);
                    grassyfirstrun = false;
                }
            }
            if (game.getLevel() % 25 > 4 && game.getLevel() % 25 < 9) {
                grassyfirstrun = true;
                j.setBackground(k);
                MediaPlayer sandymain = MediaPlayer.create(this, R.raw.sandymain);
                if (sandyfirstrun == true) {
                    MusicPlayer.startSong(sandymain);
                    sandyfirstrun = false;
                }
            }
            if (game.getLevel() % 25 > 8 && game.getLevel() % 25 < 13) {
                sandyfirstrun = true;
                j.setBackground(l);
                MediaPlayer gravelmain = MediaPlayer.create(this, R.raw.gravelmain);
                if (gravelfirstrun == true) {
                    MusicPlayer.startSong(gravelmain);
                    gravelfirstrun = false;
                }
            }
            if (game.getLevel() % 25 > 12 && game.getLevel() % 25 < 17) {
                gravelfirstrun = true;
                j.setBackground(m);
                MediaPlayer leafmain = MediaPlayer.create(this, R.raw.leafmain);
                if (leaffirstrun == true) {
                    MusicPlayer.startSong(leafmain);
                    leaffirstrun = false;
                }
            }
            if (game.getLevel() % 25 > 16 && game.getLevel() % 25 < 21) {
                leaffirstrun = true;

                MediaPlayer desertmain = MediaPlayer.create(this, R.raw.desertmain);
                if (desertfirstrun == true) {
                    MusicPlayer.startSong(desertmain);
                    desertfirstrun = false;
                }
            }
            if (game.getLevel() % 25 > 20 && game.getLevel() % 25 < 24) {
                desertfirstrun = true;
                j.setBackground(p);
                MediaPlayer snowmain = MediaPlayer.create(this, R.raw.snowmain);
                if (snowfirstrun == true) {
                    MusicPlayer.startSong(snowmain);
                    snowfirstrun = false;
                }
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
            goldearnedbylevel = goldearnedbylevel + goldAddedPerLevel;
            PlayerData.currentGold = PlayerData.currentGold + goldearnedbylevel;
            TextView goldEarnedDisplay = (TextView) findViewById(R.id.textView23);
            TextView currentGoldDisplay = (TextView) findViewById(R.id.textView25);
            goldEarnedDisplay.setText("" + goldearnedbylevel);
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
