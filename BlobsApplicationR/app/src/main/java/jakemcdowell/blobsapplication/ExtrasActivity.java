package jakemcdowell.blobsapplication;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ExtrasActivity extends AppCompatActivity implements View.OnClickListener {
    Boolean creditsmusicstart = true;
    Button returntomenu;
    AnimationDrawable normalBugDancing;
    AnimationDrawable fireBugDancing;
    AnimationDrawable movingBugDancing;
    AnimationDrawable teleportingBugDancing;
    AnimationDrawable kazoo1;
    AnimationDrawable kazoo2;
    AnimationDrawable kazoo3;
    AnimationDrawable kazoo4;
    View normalbugimage;
    View firebugimage;
    View movingbugimage;
    View teleportingbugimage;
    View kazoo1image;
    View kazoo2image;
    View kazoo3image;
    View kazoo4image;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        saveAndLoadData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);
        returntomenu = (Button) this.findViewById(R.id.button22);
        returntomenu.setOnClickListener(this);
        normalbugimage = findViewById(R.id.imageView41);
        normalbugimage.setBackgroundResource(R.drawable.normalbugdancinganim);
        normalBugDancing = (AnimationDrawable) normalbugimage.getBackground();
        firebugimage = findViewById(R.id.imageView42);
        firebugimage.setBackgroundResource(R.drawable.firebugdancinganim);
        fireBugDancing = (AnimationDrawable) firebugimage.getBackground();
        movingbugimage = findViewById(R.id.imageView43);
        movingbugimage.setBackgroundResource(R.drawable.movingbugdancinganim);
        movingBugDancing = (AnimationDrawable) movingbugimage.getBackground();
        teleportingbugimage = findViewById(R.id.imageView44);
        teleportingbugimage.setBackgroundResource(R.drawable.teleportingbugdancinganim);
        teleportingBugDancing = (AnimationDrawable) teleportingbugimage.getBackground();
        kazoo1image = findViewById(R.id.imageView49);
        kazoo1image.setBackgroundResource(R.drawable.partykazooanim);
        kazoo1 = (AnimationDrawable) kazoo1image.getBackground();
        kazoo2image = findViewById(R.id.imageView50);
        kazoo2image.setBackgroundResource(R.drawable.partykazooanim);
        kazoo2 = (AnimationDrawable) kazoo2image.getBackground();
        kazoo3image = findViewById(R.id.imageView51);
        kazoo3image.setBackgroundResource(R.drawable.partykazooanim);
        kazoo3 = (AnimationDrawable) kazoo3image.getBackground();
        kazoo4image = findViewById(R.id.imageView52);
        kazoo4image.setBackgroundResource(R.drawable.partykazooanim);
        kazoo4 = (AnimationDrawable) kazoo4image.getBackground();
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
        if (creditsmusicstart) {
            playCreditsMusic();
            creditsmusicstart = false;
        }
        else {
        }

        normalBugDancing.start();
        fireBugDancing.start();
        movingBugDancing.start();
        teleportingBugDancing.start();
        kazoo1.start();
        kazoo2.start();
        kazoo3.start();
        kazoo4.start();

    }

    public void playCreditsMusic() {
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
