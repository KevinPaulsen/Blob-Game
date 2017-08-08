package jakemcdowell.blobsapplication;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExtrasActivity extends AppCompatActivity implements View.OnClickListener {

    Button returntomenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);
        returntomenu = (Button) this.findViewById(R.id.button22);
        returntomenu.setOnClickListener(this);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        MediaPlayer creditstheme = MediaPlayer.create(this,R.raw.creditstheme);
        MusicPlayer.startSong(creditstheme);
    }

    public void onClick(View v) {
        if (v.equals(returntomenu)) {
            returnbutton();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void returnbutton() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
