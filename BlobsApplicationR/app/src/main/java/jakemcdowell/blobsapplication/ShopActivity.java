package jakemcdowell.blobsapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class ShopActivity extends AppCompatActivity implements Runnable {

    AnimationDrawable dooranimation;
    AnimationDrawable dooranimationfall;
    AnimationDrawable dooranimationslide;
    AnimationDrawable dooranimationsquash;
    private int dooranimselection;
    static boolean shopmusicstart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Button leaveshopbutton = (Button) findViewById(R.id.LeaveShopButton);
        leaveshopbutton.setBackgroundResource(R.drawable.door);
        dooranimselection = (int) (Math.random() * 4) + 1;
        if (dooranimselection == 1 || dooranimselection == 4) {
            leaveshopbutton.setBackgroundResource(R.drawable.dooranimation);
            dooranimation = (AnimationDrawable) leaveshopbutton.getBackground();
        }
        else if (dooranimselection == 2) {
            leaveshopbutton.setBackgroundResource(R.drawable.dooranimationfall);
            dooranimationfall = (AnimationDrawable) leaveshopbutton.getBackground();
        }
        else if (dooranimselection == 3) {
            leaveshopbutton.setBackgroundResource(R.drawable.dooranimationslide);
            dooranimationslide = (AnimationDrawable) leaveshopbutton.getBackground();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        TextView golddisplay = (TextView) findViewById(R.id.textView10);
        golddisplay.setText("" + PlayerData.currentGold);
        if (shopmusicstart) {
            playShopMusic();
            shopmusicstart = false;
        }
        else {
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void playShopMusic() {
        MediaPlayer shoptheme = MediaPlayer.create(this,R.raw.shoptheme);
        MusicPlayer.startSong(shoptheme);
    }

    public void leaveshopbutton(View v) {
        Thread thread = new Thread(this);
        thread.start();
        SystemClock.sleep(300);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void damageincreasebutton(View v) {
        Intent intent = new Intent(this,DamageIncreaseActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void radiusincreasebutton(View v) {
        Intent intent2 = new Intent(this,RadiusIncreaseActivity.class);
        startActivity(intent2);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void pesticidebutton(View v) {
        Intent intent3 = new Intent(this,PesticideActivity.class);
        startActivity(intent3);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void goldincreasebutton(View v) {
        Intent intent4 = new Intent(this,GoldIncreaseActivity.class);
        startActivity(intent4);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void run() {
        if (dooranimselection == 1 || dooranimselection == 4) {
            dooranimation.start();
        }
        else if (dooranimselection == 2) {
            dooranimationfall.start();
        }
        else if (dooranimselection == 3) {
            dooranimationslide.start();
        }
    }
}
