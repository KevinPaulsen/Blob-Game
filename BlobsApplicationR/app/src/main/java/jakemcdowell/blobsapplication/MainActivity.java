package jakemcdowell.blobsapplication;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import jakemcdowell.blobsapplication.GameActivity;
import jakemcdowell.blobsapplication.HelpActivity;
import jakemcdowell.blobsapplication.R;
import jakemcdowell.blobsapplication.ShopActivity;

import static android.R.color.transparent;


public class MainActivity extends AppCompatActivity {
    public static boolean isMuted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView a = (TextView) findViewById(R.id.textView20);
        a.setText("Version: " + Constants.version);
        TextView b = (TextView) findViewById(R.id.textView32);
        b.setText("High Score: Level " + PlayerData.highestLevel);
        if(PlayerData.continueLevel == true) {
            ((Button) (findViewById(R.id.button))).setText("Continue Level " + (PlayerData.currentLevel+1));
        }
        else if(PlayerData.continueLevel1 == true) {
            ((Button) (findViewById(R.id.button))).setText("Continue Level " + (PlayerData.currentLevel));
        }
        else if(PlayerData.beginLevel == true){
            ((Button) (findViewById(R.id.button))).setText("Begin Level " + (PlayerData.currentLevel+1));
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        playMainTheme();
        if (isMuted && findViewById(R.id.button20).getVisibility() == View.VISIBLE) {
            findViewById(R.id.button20).setBackgroundResource(R.drawable.x);
        }
    }

    public void playMainTheme() {
        MediaPlayer maintheme = MediaPlayer.create(this, R.raw.maintheme);
        MusicPlayer.startSong(maintheme);
    }

    public void playgamebuttonclick(View v) {
        MediaPlayer maintheme = MediaPlayer.create(this, R.raw.maintheme);
        MusicPlayer.resetMusic(maintheme);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void mutebuttonclick(View v) {
        if (isMuted) {
            findViewById(R.id.button20).setBackgroundResource(transparent);
            isMuted = !isMuted;
            playMainTheme();
        } else {
            findViewById(R.id.button20).setBackgroundResource(R.drawable.x);
            MusicPlayer.stopCurrentMusic();
            isMuted = !isMuted;
        }

    }

    public void entershopbuttonclick(View v) {
        MediaPlayer maintheme = MediaPlayer.create(this, R.raw.maintheme);
        MusicPlayer.resetMusic(maintheme);
        ShopActivity.shopmusicstart = true;
        Intent intent2 = new Intent(this,ShopActivity.class);
        startActivity(intent2);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void howtoplaybuttonclick(View v) {
        Intent intent3 = new Intent(this,HelpActivity.class);
        startActivity(intent3);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void extrasbuttonclick(View v) {
        Intent intent4 = new Intent(this, ExtrasActivity.class);
        startActivity(intent4);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
