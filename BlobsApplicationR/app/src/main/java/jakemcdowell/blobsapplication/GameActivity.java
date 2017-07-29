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
    AnimationDrawable buganimation;
    AnimationDrawable buganimation5;
    AnimationDrawable buganimation6;
    AnimationDrawable buganimation7;
    AnimationDrawable buganimation8;
    AnimationDrawable buganimation9;
    AnimationDrawable buganimation10;
    AnimationDrawable buganimation11;
    AnimationDrawable buganimation12;
    AnimationDrawable buganimation13;

    private ProgressBar levelProgress;
    private int health = 1;
    private Bug bug1;
    private Bug bug2;
    private Bug bug3;
    private Bug bug4;
    private Bug bug5;
    private Bug bug6;
    private Bug bug7;
    private Bug bug8;
    private Bug bug9;
    private Bug bug10;
    private Game game;
    private ArrayList<Bug> bugList = new ArrayList<>();
    private int kOsPerDeath = 2;

    //Sets up game screen (progress bar)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);

        //Instantiates all progresssBars, and  puts them offscreen
        levelProgress = (ProgressBar)this.findViewById(R.id.progressBar2);

        putOffScreen((ProgressBar)this.findViewById(R.id.progressBar3));
        putOffScreen((ProgressBar)this.findViewById(R.id.progressBar4));
        putOffScreen((ProgressBar)this.findViewById(R.id.progressBar5));
        putOffScreen((ProgressBar)this.findViewById(R.id.progressBar6));
        putOffScreen((ProgressBar)this.findViewById(R.id.progressBar7));
        putOffScreen((ProgressBar)this.findViewById(R.id.progressBar8));
        putOffScreen((ProgressBar)this.findViewById(R.id.progressBar9));
        putOffScreen((ProgressBar)this.findViewById(R.id.progressBar10));
        putOffScreen((ProgressBar)this.findViewById(R.id.progressBar11));
        putOffScreen(this.findViewById(R.id.button5));
        putOffScreen(this.findViewById(R.id.button6));
        putOffScreen(this.findViewById(R.id.button7));
        putOffScreen(this.findViewById(R.id.button8));
        putOffScreen(this.findViewById(R.id.button9));
        putOffScreen(this.findViewById(R.id.button10));
        putOffScreen(this.findViewById(R.id.button11));
        putOffScreen(this.findViewById(R.id.button12));
        putOffScreen(this.findViewById(R.id.button13));

        //adds every bug that will appear to a list.
        bugList.add(bug1);
        bugList.add(bug2);
        bugList.add(bug3);
        bugList.add(bug4);
        bugList.add(bug5);
        bugList.add(bug6);
        bugList.add(bug7);
        bugList.add(bug8);
        bugList.add(bug9);
        bugList.add(bug10);

        //Creates new Game
        game = new Game(levelProgress, bugList);

        Button button = (Button)findViewById(R.id.button);
        Button button5 = (Button)findViewById(R.id.button5);
        Button button6 = (Button)findViewById(R.id.button6);
        Button button7 = (Button)findViewById(R.id.button7);
        Button button8 = (Button)findViewById(R.id.button8);
        Button button9 = (Button)findViewById(R.id.button9);
        Button button10 = (Button)findViewById(R.id.button10);
        Button button11 = (Button)findViewById(R.id.button11);
        Button button12 = (Button)findViewById(R.id.button12);
        Button button13 = (Button)findViewById(R.id.button13);

        button.setBackgroundResource(R.drawable.animationxml);
        button5.setBackgroundResource(R.drawable.animationxml);
        button6.setBackgroundResource(R.drawable.animationxml);
        button7.setBackgroundResource(R.drawable.animationxml);
        button8.setBackgroundResource(R.drawable.animationxml);
        button9.setBackgroundResource(R.drawable.animationxml);
        button10.setBackgroundResource(R.drawable.animationxml);
        button11.setBackgroundResource(R.drawable.animationxml);
        button12.setBackgroundResource(R.drawable.animationxml);
        button13.setBackgroundResource(R.drawable.animationxml);

        buganimation = (AnimationDrawable)button.getBackground();
        buganimation5 = (AnimationDrawable)button5.getBackground();
        buganimation6 = (AnimationDrawable)button6.getBackground();
        buganimation7 = (AnimationDrawable)button7.getBackground();
        buganimation8 = (AnimationDrawable)button8.getBackground();
        buganimation9 = (AnimationDrawable)button9.getBackground();
        buganimation10 = (AnimationDrawable)button10.getBackground();
        buganimation11 = (AnimationDrawable)button11.getBackground();
        buganimation12 = (AnimationDrawable)button12.getBackground();
        buganimation13 = (AnimationDrawable)button13.getBackground();

        //instantiantes every bug that will appear.
        bug1 = new Bug(button, health, (ProgressBar)this.findViewById(R.id.progressBar), kOsPerDeath);
        bug2 = new Bug(button5, health, (ProgressBar)this.findViewById(R.id.progressBar3), kOsPerDeath);
        bug3 = new SmallBug(button6, health, (ProgressBar)this.findViewById(R.id.progressBar4), kOsPerDeath);
        bug4 = new SmallBug(button7, health, (ProgressBar)this.findViewById(R.id.progressBar5), kOsPerDeath);
        bug5 = new MovingBug(button8, health, (ProgressBar)this.findViewById(R.id.progressBar6), kOsPerDeath);
        bug6 = new MovingBug(button9, health, (ProgressBar)this.findViewById(R.id.progressBar7), kOsPerDeath);
        bug7 = new TeleportingBug(button10, health, (ProgressBar)this.findViewById(R.id.progressBar8), kOsPerDeath);
        bug8 = new TeleportingBug(button11, health, (ProgressBar)this.findViewById(R.id.progressBar9), kOsPerDeath);
        bug9 = new FireBug(button12, health, (ProgressBar)this.findViewById(R.id.progressBar10), kOsPerDeath);
        bug10 = new FireBug(button13, health, (ProgressBar)this.findViewById(R.id.progressBar11), kOsPerDeath);
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
        bug1.move();
        buganimation.start();
        buganimation5.start();
        buganimation6.start();
        buganimation7.start();
        buganimation8.start();
        buganimation9.start();
        buganimation10.start();
        buganimation11.start();
        buganimation12.start();
        buganimation13.start();
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

        for (Bug bug : bugList) {
            if (bug.getButton() == view) {
                return bug;
            }
        }
        return bug1;
    }
}


