package jakemcdowell.blobsapplication;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    boolean howtoplaymusicstart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (howtoplaymusicstart) {
            playHowToPlayMusic();
            howtoplaymusicstart = false;
        }
        else {
        }
    }

    public void playHowToPlayMusic() {
        MediaPlayer howtoplaytheme = MediaPlayer.create(this,R.raw.howtoplaytheme);
        MusicPlayer.startSong(howtoplaytheme);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void leave(View v) {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
