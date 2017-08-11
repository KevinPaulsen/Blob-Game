package jakemcdowell.blobsapplication;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener{

    Button playgameagain;
    Button returntomenu;
    public static Boolean fireBugDeath = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        playgameagain = (Button)this.findViewById(R.id.button15);
        playgameagain.setOnClickListener(this);
        returntomenu = (Button)this.findViewById(R.id.button17);
        returntomenu.setOnClickListener(this);
        MediaPlayer gameovertheme = MediaPlayer.create(this, R.raw.gameovertheme);
        MusicPlayer.startSongOnce(gameovertheme);
        if (fireBugDeath) {
            findViewById(R.id.textView47).setVisibility(View.VISIBLE);
        }
        else {
            findViewById(R.id.textView47).setVisibility(View.GONE);
        }
    }

    public void onClick(View v) {
        if (v.equals(playgameagain)) {
            playgameagainbutton();
        }
        else if (v.equals(returntomenu)) {
            returntomenubutton();
        }
    }

    public void playgameagainbutton() {
        PlayerData.currentLevel = 1;
        MediaPlayer gameovertheme = MediaPlayer.create(this, R.raw.gameovertheme);
        MusicPlayer.resetMusic(gameovertheme);
        Intent intent = new Intent(this, GameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        returntomenubutton();
    }


    public void returntomenubutton() {
        MediaPlayer gameovertheme = MediaPlayer.create(this, R.raw.gameovertheme);
        MusicPlayer.resetMusic(gameovertheme);
        Intent intent2 = new Intent(this, MainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent2);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
