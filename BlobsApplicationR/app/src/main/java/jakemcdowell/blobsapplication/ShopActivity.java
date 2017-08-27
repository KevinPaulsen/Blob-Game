package jakemcdowell.blobsapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
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
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        shopmusicstart = true;
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

    public void onDestroy() {
        super.onDestroy();
        saveAndLoadData();
    }

    public void SaveInt(String key, int value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void SaveBoolean(String key, boolean value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void saveAndLoadData() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (PlayerData.currentGold > 0 ) {
            SaveInt("currentgoldkey", PlayerData.currentGold);
            PlayerData.currentGold = sharedPreferences.getInt("currentgoldkey",0);
        } else {
            PlayerData.currentGold = sharedPreferences.getInt("currentgoldkey",0);
            SaveInt("currentgoldkey", PlayerData.currentGold);
        }
        if (PlayerData.currentLevel > 0 ) {
            SaveInt("currentlevelkey", PlayerData.currentLevel);
            PlayerData.currentLevel = sharedPreferences.getInt("currentlevelkey",1);
        } else {
            PlayerData.currentLevel = sharedPreferences.getInt("currentlevelkey",1);
            SaveInt("currentlevelkey", PlayerData.currentLevel);
        }
        if (PlayerData.highestLevel > 0 ) {
            SaveInt("highestlevelkey", PlayerData.highestLevel);
            PlayerData.highestLevel = sharedPreferences.getInt("highestlevelkey",0);
        } else {
            PlayerData.highestLevel = sharedPreferences.getInt("highestlevelkey",0);
            SaveInt("highestlevelkey", PlayerData.highestLevel);
        }
        if (PlayerData.totalEarnedGoldInLevel > 0 ) {
            SaveInt("totalearnedgoldinlevelkey", PlayerData.totalEarnedGoldInLevel);
            PlayerData.totalEarnedGoldInLevel = sharedPreferences.getInt("totalearnedgoldinlevelkey",0);
        } else {
            PlayerData.totalEarnedGoldInLevel = sharedPreferences.getInt("totalearnedgoldinlevelkey",0);
            SaveInt("totalearnedgoldinlevelkey", PlayerData.totalEarnedGoldInLevel);
        }
        if (PlayerData.damageIncreaseLevel > 0 ) {
            SaveInt("damageincreaselevelkey", PlayerData.damageIncreaseLevel);
            PlayerData.damageIncreaseLevel = sharedPreferences.getInt("damageincreaselevelkey",0);
        } else {
            PlayerData.damageIncreaseLevel = sharedPreferences.getInt("damageincreaselevelkey",0);
            SaveInt("damageincreaselevelkey", PlayerData.damageIncreaseLevel);
        }
        if (PlayerData.radiusIncreaseLevel > 0 ) {
            SaveInt("radiusincreaselevelkey", PlayerData.radiusIncreaseLevel);
            PlayerData.radiusIncreaseLevel = sharedPreferences.getInt("radiusincreaselevelkey",0);
        } else {
            PlayerData.radiusIncreaseLevel = sharedPreferences.getInt("radiusincreaselevelkey",0);
            SaveInt("radiusincreaselevelkey", PlayerData.radiusIncreaseLevel);
        }
        if (PlayerData.numberOfPesticide > 0 ) {
            SaveInt("numberofpesticidekey", PlayerData.numberOfPesticide);
            PlayerData.numberOfPesticide = sharedPreferences.getInt("numberofpesticidekey",0);
        } else {
            PlayerData.numberOfPesticide = sharedPreferences.getInt("numberofpesticidekey",0);
            SaveInt("numberofpesticidekey", PlayerData.numberOfPesticide);
        }
        if (PlayerData.goldIncreaseLevel > 0 ) {
            SaveInt("goldincreaselevelkey", PlayerData.goldIncreaseLevel);
            PlayerData.goldIncreaseLevel = sharedPreferences.getInt("goldincreaselevelkey",0);
        } else {
            PlayerData.goldIncreaseLevel = sharedPreferences.getInt("goldincreaselevelkey",0);
            SaveInt("goldincreaselevelkey", PlayerData.goldIncreaseLevel);
        }
        PlayerData.shouldResumeGame = sharedPreferences.getBoolean("shouldresumegamekey", false);
        PlayerData.shouldBeginGame = sharedPreferences.getBoolean("shouldbegingamekey", false);
        SaveBoolean("shouldresumegamekey", PlayerData.shouldResumeGame);
        SaveBoolean("shouldbegingamekey", PlayerData.shouldBeginGame);

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
