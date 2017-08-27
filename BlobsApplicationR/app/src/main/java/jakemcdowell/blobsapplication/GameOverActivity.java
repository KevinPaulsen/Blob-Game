package jakemcdowell.blobsapplication;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener{

    Button playgameagain;
    Button returntomenu;
    public static Boolean fireBugDeath = false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        saveAndLoadData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        playgameagain = (Button)this.findViewById(R.id.button15);
        playgameagain.setOnClickListener(this);
        returntomenu = (Button)this.findViewById(R.id.button17);
        returntomenu.setOnClickListener(this);
        MediaPlayer gameovertheme = MediaPlayer.create(this, R.raw.gameovertheme);
        MusicPlayer.startSongOnce(gameovertheme);
        if (fireBugDeath) {
            fireBugDeath = false;
            findViewById(R.id.textView47).setVisibility(View.VISIBLE);
            findViewById(R.id.textView48).setVisibility(View.GONE);
        }
        else {
            fireBugDeath = false;
            findViewById(R.id.textView47).setVisibility(View.GONE);
            findViewById(R.id.textView48).setVisibility(View.VISIBLE);
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
