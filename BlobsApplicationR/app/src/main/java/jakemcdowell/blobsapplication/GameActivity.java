package jakemcdowell.blobsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import java.lang.Math;


public class GameActivity extends AppCompatActivity {

    private ProgressBar levelProgress;
    private ProgressBar hp;
    private double totalTaps;
    private double LevelM = 0;
    private double health = 2;
    private int totalLevels = 1;
    private Bug bug;
    private Game game;
    private boolean flag = true;


    //Sets up game screen (progress bar)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activity);
        levelProgress = (ProgressBar)this.findViewById(R.id.progressBar2);
        levelProgress.setProgress(0);
        hp = (ProgressBar)this.findViewById(R.id.progressBar);
        hp.setProgress(100);
        game = new Game(levelProgress, hp);
    }

    //Button game mechanics
    public void buttonTestClick(View v){
        View c = findViewById(R.id.button);
        if (flag) {
            bug = new Bug(v, health);
            flag = !flag;
        }

        //Moves button randomly around screen
        bug.move();

        bug.tapBug();
        game.setProgressBar(bug);
        //hp.setProgress((int)((health-totalTaps)/health*100));


        if(bug.getTapsOnBug() == bug.getHealth()){

            game.deadBug();
            game.pauseDeath(bug);
            bug.newBug();
        }

        if(game.getBugsKilled() == 5) {

            c.setY(10000);

            //Activity Change (Backdrop Change,etc)
            game.nextLevel();
            game.resetLevelProgress();
            bug.addHealth(2);
            health += 2;
            View b = findViewById(R.id.button2);
            View g = findViewById(R.id.textView2);
            View d = findViewById(R.id.progressBar);
            View e = findViewById(R.id.progressBar2);
            b.setVisibility(View.VISIBLE);
            g.setVisibility(View.VISIBLE);
            d.setVisibility(View.GONE);
            e.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
            totalLevels++;
        }
    }


    public void nextlevelbuttonclick(View v) {
        TextView f = (TextView) findViewById(R.id.textView1);
        f.setText("Level: "+totalLevels);
        View b = findViewById(R.id.button2);
        b.setVisibility(View.GONE);
        View g = findViewById(R.id.textView2);
        g.setVisibility(View.GONE);
        View c = findViewById(R.id.button);
        c.setVisibility(View.VISIBLE);
        View d = findViewById(R.id.progressBar);
        d.setVisibility(View.VISIBLE);
        View e = findViewById(R.id.progressBar2);
        e.setVisibility(View.VISIBLE);
        bug.move();
    }
}


