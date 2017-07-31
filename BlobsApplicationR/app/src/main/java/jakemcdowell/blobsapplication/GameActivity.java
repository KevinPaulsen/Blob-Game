package jakemcdowell.blobsapplication;

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
    AnimationDrawable buganimations[] = new AnimationDrawable[10];

    private Game game;
    private ArrayList<Bug> bugList = new ArrayList<>();
    private boolean isInitialized = false;

    //Sets up game screen (progress bar)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button buttons[] = new Button[10];
        ProgressBar progressBar[] = new ProgressBar[10];
        int buttonIds[] = new int[10];
        int progressBarIds[] = new int[10];
        int health = 1;
        int kOsPerDeath = 2;

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

        for (int count = 0; count < 10; count++) {
            //Instantiates all bug buttons, and  puts them offscreen
            buttons[count] = (Button) findViewById(buttonIds[count]);
            buttons[count].setBackgroundResource(R.drawable.animationxml);
            putOffScreen(buttons[count]);
            //Instantiates all progresssBars, and  puts them offscreen
            progressBar[count] = (ProgressBar) findViewById(progressBarIds[count]);
            putOffScreen(progressBar[count]);
            // Get all the bug animation objects
            buganimations[count] = (AnimationDrawable) buttons[count].getBackground();
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

        //Creates new Game
        ProgressBar levelProgress = (ProgressBar)this.findViewById(R.id.progressBar2);
        game = new Game(levelProgress, bugList);

        /*
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    //move the bug
                    try{
                        Thread.sleep(1000);
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
        */
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (!isInitialized) {
            bugList.get(0).move();
            for (AnimationDrawable bugAnimation : buganimations) {
                bugAnimation.start();
            }
            isInitialized = true;
        }
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
            findViewById(R.id.button).setVisibility(View.GONE);
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            findViewById(R.id.progressBar2).setVisibility(View.GONE);
        }
    }

    //code rand for nextLevelButtonClick
    public void nextLevelButtonClick(View v) {
        //checks to see if new bug on screen should be added
        game.nextLevel();

        //Updates level marker
        TextView f = (TextView) findViewById(R.id.textView1);
        f.setText("level: " + game.getLevel());

        //sets up next level and removes nextLevel page.
        View b = findViewById(R.id.button2);
        View g = findViewById(R.id.textView2);
        View c = findViewById(R.id.button);
        View d = findViewById(R.id.progressBar);
        View e = findViewById(R.id.progressBar2);
        b.setVisibility(View.GONE);
        g.setVisibility(View.GONE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
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
